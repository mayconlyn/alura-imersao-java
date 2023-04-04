package br.com.alura.linguagens.api;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "principaisLinguagens")
public class Linguagem {

    @Id
    private String id;
    private String title;
    private String image;
    private String raiting;  

    public Linguagem(){

    }

    public Linguagem(String title, String image, String raiting) {
        this.title = title;
        this.image = image;
        this.raiting = raiting;
    }

    
    
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public String getImage() {
        return image;
    }
    public String getRaiting() {
        return raiting;
    }

    public String getId() {
        return id;
    }
    
}
