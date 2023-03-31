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

    public void criar(InputStream inputStream, String nomeArquvivo, double imDbRating) throws Exception{
 
        //InputStream inputStream = new FileInputStream(new File("C:\\workspace-estudos\\alura-imersao-java\\alura-stickers\\entrada\\filme.jpg"));
        //InputStream inputStream = new URL("https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_Ratio0.6716_AL_.jpg").openStream();
        
        //leitura da imagem
        BufferedImage imagemOriginal = ImageIO.read(inputStream);

        //redmensionar a imagem
        Image tmp = imagemOriginal.getScaledInstance(1089, 1500, Image.SCALE_SMOOTH);

        //criar nova imagem com transarência e com tamanho novo
        int largura = 1089;
        int altura = 1500;
        int novaAltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaAltura, BufferedImage.TRANSLUCENT);

        // copiar a imagem original para nova imagem (em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(tmp, 0, 0, null);

        //imagem de Raiting
        String texto;
        BufferedImage imagemRaiting;
        if(imDbRating >= 7.5)
        {
            imagemRaiting = ImageIO.read(new FileInputStream(new File("C:\\workspace-estudos\\alura-imersao-java\\alura-stickers\\entrada\\top.png")));
            texto = "TOPPER";
        }    
        else if(imDbRating >= 6 ){
            imagemRaiting = ImageIO.read(new FileInputStream(new File("C:\\workspace-estudos\\alura-imersao-java\\alura-stickers\\entrada\\mediano.png")));
            texto = "MEDIANO";
        }
            
        else
        { 
            imagemRaiting = ImageIO.read(new FileInputStream(new File("C:\\workspace-estudos\\alura-imersao-java\\alura-stickers\\entrada\\meia-boca.png")));
            texto = "MEIA BOCA";
        }     
        graphics.drawImage(imagemRaiting, 0, 1200, null);

        // configurar a fonte
        var font = new Font("Impact", Font.BOLD, 120);
        graphics.setFont(font);
        graphics.setColor(Color.MAGENTA);

        //escrever uma frase na nova imagem

        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retangulo = fontMetrics.getStringBounds(texto, graphics);
        int larguraTexto = (int) retangulo.getWidth();
        int posX = (largura-larguraTexto)-70;
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
