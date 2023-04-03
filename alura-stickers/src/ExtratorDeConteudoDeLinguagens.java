import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoDeLinguagens implements ExtratorDeConteudo {

    @Override
    public List<Conteudo> extraiConteudos(String json) {
        // extrair dados que interessam (tituo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeAtributos = parser.parse(json);

        return listaDeAtributos.stream()
        .map(atributos -> {
            return new Conteudo(atributos.get("title"), atributos.get("image"), Double.parseDouble(atributos.get("raiting")));
        }).toList();

    }
}
    

