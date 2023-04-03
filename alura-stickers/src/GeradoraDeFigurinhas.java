import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class GeradoraDeFigurinhas {


    public void criar(InputStream inputStream, String nomeArquvivo, double raiting) throws Exception{
 
        //leitura da imagem
        BufferedImage imagemOriginal = ImageIO.read(inputStream);
        int largura;
        int altura;
        Image tmp;

        //redmensionar a imagem
        if(imagemOriginal.getHeight() > imagemOriginal.getWidth()){
            largura = SizedImagem.IMAGE_VERTCAL.getLargura();
            altura = SizedImagem.IMAGE_VERTCAL.getAltura();

        }else if(imagemOriginal.getHeight() == imagemOriginal.getWidth()){
            largura = SizedImagem.IMAGE_LADOS_IGUAIS.getLargura();
            altura = SizedImagem.IMAGE_LADOS_IGUAIS.getAltura();
        }
        else{
            largura = SizedImagem.IMAGE_HORIZONTAL.getLargura();
            altura = SizedImagem.IMAGE_HORIZONTAL.getAltura();
        }
        tmp = imagemOriginal.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

        //criar nova imagem com transarência e com tamanho novo 
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // copiar a imagem original para nova imagem (em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(tmp, 0, 0, null);

        //desenhar a imagem de Raiting
        String texto;
        BufferedImage imagemRaiting;
        int alturaRaiting = altura - 308;
        if(raiting >= 7.5)
        {
            imagemRaiting = ImageIO.read(new FileInputStream(new File("C:\\workspace-estudos\\alura-imersao-java\\alura-stickers\\entrada\\top.png")));
            if(raiting == 10)
                texto = nomeArquvivo.replaceAll(".png", "");
            else
                texto = "TOPPER";
        }    
        else if(raiting >= 6 ){
            imagemRaiting = ImageIO.read(new FileInputStream(new File("C:\\workspace-estudos\\alura-imersao-java\\alura-stickers\\entrada\\mediano.png")));
            texto = "MEDIANO";
        }
            
        else
        { 
            imagemRaiting = ImageIO.read(new FileInputStream(new File("C:\\workspace-estudos\\alura-imersao-java\\alura-stickers\\entrada\\meia-boca.png")));
            texto = "MEIA BOCA";
        }    
        graphics.drawImage(imagemRaiting, 0, alturaRaiting, null);

        // configurar a fonte
        var font = new Font("Impact", Font.BOLD, 60);
        graphics.setFont(font);
        graphics.setColor(Color.MAGENTA);

        //escrever uma frase na nova imagem
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retangulo = fontMetrics.getStringBounds(texto, graphics);
        int larguraTexto = (int) retangulo.getWidth();
        int posX = (largura-larguraTexto)/2;
        int posY = novaAltura-50;
        graphics.drawString(texto, posX, posY);

        FontRenderContext fontRenderContext = graphics.getFontRenderContext();
        var textLayout = new TextLayout(texto, font, fontRenderContext);

        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(posX, posY);
        graphics.setTransform(transform);

        var outLineStroke = new BasicStroke(largura * 0.004f);
        graphics.setStroke(outLineStroke);

        graphics.setColor(Color.BLACK);
        graphics.draw(outline);
        graphics.setClip(outline);

        // escrever a nova imagem em um arquivo
        ImageIO.write(novaImagem, "png", new File("C:\\workspace-estudos\\alura-imersao-java\\alura-stickers\\saida\\"+nomeArquvivo));
    }

    
}
