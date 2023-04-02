import java.util.*;

public class ExtratorDeConteudoDaNasa implements ExtratorDeConteudo {

    public List<Conteudo> extraiConteudos(String json){

        // extrair dados que interessam (tituo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeAtributos = parser.parse(json);

       return listaDeAtributos.stream()
            .map(atributos -> {
                double raiting = 10;
                return new Conteudo(atributos.get("title"), atributos.get("url"), raiting);
            }).toList();

    }
    
}
