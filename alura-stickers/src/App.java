import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {

        API api = API.LINGUAGENS;
        String url = api.getUrl();
        
        ExtratorDeConteudo extrator = api.getExtrator();

        var http = new ClienteHttp();
        String json = http.buscaDados(url);
        
        //exibir e manipular os dados
        List<Conteudo> conteudos = extrator.extraiConteudos(json);

        var geradora = new GeradoraDeFigurinhas();
        for (int i = 0; i < 7 ; i ++) {
            Conteudo conteudo = conteudos.get(i);
            String urlString = conteudo.urlImagem();
            
            InputStream inputStream = new URL(urlString).openStream();
            String nomeArquivo = conteudo.titulo().replace(":", "-") +".png";
            double imDbRating = conteudo.raiting();  

            System.out.println("\u001b[38;2;90;114;140mTitulo: \u001b[m"+"\u001b[38;2;255;255;255m"+conteudo.titulo()+"\u001b[m");
            System.out.println("\u001b[38;2;90;114;140mPoster: \u001b[m"+"\u001b[38;2;255;255;255m"+urlString+"\u001b[m");

            if(conteudo.raiting()!=0){
                System.out.println("\u001b[102mClassificação: "+conteudo.raiting()+"\u001b[m");
                stars((int)imDbRating);
            }

            geradora.criar(inputStream, nomeArquivo, imDbRating);
            System.out.println();
        }
    }

    private static void stars(int stars) {
        for(int i = 0; i < stars; i++){
            System.out.print("\u2B50");
        }
        System.out.println();
    }
}
