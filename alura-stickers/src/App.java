import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws Exception {

        Properties prop = getProp();
        String imdbKey = prop.getProperty("IMDB_API_KEY");

        // fazer uma conexão HTTP e buscar os top 250 filmes
        String url = "https://imdb-api.com/en/API/MostPopularMovies/"+imdbKey;
        URI endereco = URI.create(url);
        //HttpClient client = HttpClient.newHttpClient();
        var client = HttpClient.newHttpClient();
        //HttpRequest
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();


        //extrair dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        

        //exibir e manipular os dados
        var geradora = new GeradoraDeFigurinhas();
        for (Map<String,String> filme : listaDeFilmes) {

            String urlString = filme.get("image");
            String titulo = filme.get("title");
            double imDbRating = Double.parseDouble(filme.get("imDbRating"));
            
            InputStream inputStream = new URL(urlString).openStream();
            String nomeArquivo = titulo.replace(":", "-") +".png";

            System.out.println("\u001b[38;2;90;114;140mTitulo: \u001b[m"+"\u001b[38;2;255;255;255m"+titulo+"\u001b[m");
            System.out.println("\u001b[38;2;90;114;140mPoster: \u001b[m"+"\u001b[38;2;255;255;255m"+urlString+"\u001b[m");
            System.out.println("\u001b[102mClassificação: "+filme.get("imDbRating")+"\u001b[m");
            if(filme.get("imDbRating").equalsIgnoreCase(""))
               stars(0);
            else
               stars((int)imDbRating);

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

    private static Properties getProp() throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream(
				"./.properties");
		props.load(file);
		return props;
	}
}
