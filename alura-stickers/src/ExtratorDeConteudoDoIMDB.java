import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoDoIMDB implements ExtratorDeConteudo {

    public List<Conteudo> extraiConteudos(String json){    
        // extrair dados que interessam (tituo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeAtributos = parser.parse(json);

        return listaDeAtributos.stream()
        .map(atributos -> {
            return new Conteudo(atributos.get("title"), atributos.get("image").replaceAll("._V1_UX128_CR0,12,128,176_AL_.jpg", ".jpg"), Double.parseDouble(atributos.get("imDbRating")));
        }).toList();

    }

    
}
