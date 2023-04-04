package br.com.alura.linguagens.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LinguagemController {

    @Autowired
    private LinguagemRepository repository;

    @GetMapping("/linguagens")
    public List<Linguagem> obterLinguagens(){
        List<Linguagem> linguagens = repository.findByOrderByRaitingDesc();
        return linguagens;
    }

    @PostMapping("/linguagens")
    public ResponseEntity<Linguagem> cadastrarLinguagem(@RequestBody Linguagem linguagem){
        
        Linguagem linguagemSalva = repository.save(linguagem);
        return new ResponseEntity<>(linguagemSalva, HttpStatus.CREATED);

    }

    @GetMapping("/linguagens/{id}")
    public Linguagem obterLinguagemPorId(@PathVariable String id){
        return repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/linguagens/{id}")
    public Linguagem atualizarLinguagens(@PathVariable String id, @RequestBody Linguagem linguagem){
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        linguagem.setId(id);
        Linguagem linguagemSalva = repository.save(linguagem);
        
        return linguagemSalva;
    }

    @DeleteMapping("linguagens/{id}")
    public void excluirLinguagem(@PathVariable String id){
        repository.deleteById(id);

    }

    
    
}
