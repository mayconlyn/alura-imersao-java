import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BuscaChave {

    private String chave;    

    BuscaChave(String nomeChave) {
        try{

            Properties props = new Properties();
            FileInputStream file = new FileInputStream("./.properties");
            props.load(file);
            this.chave = props.getProperty(nomeChave);
            
        }catch(IOException e){
            throw new FileException("Erro ao abrir o arquivo!");
        }	
        
	}

    public String getChave() {
        return chave;
    }
    
}
