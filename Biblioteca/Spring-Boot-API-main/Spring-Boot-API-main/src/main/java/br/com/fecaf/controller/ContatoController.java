package br.com.fecaf.controller;


import br.com.fecaf.model.Contato;
import br.com.fecaf.services.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contatos")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    // Endpoint para listar Contatos

    @GetMapping("/listarContatos")
    public List<Contato> listarContatos() {
        return contatoService.listarContatos();
    }

    @PostMapping("/cadastrarContato")
    public ResponseEntity<Contato> salvarContato(@RequestBody Contato contato) {
        Contato newContato = contatoService.salvarContato(contato);
        return ResponseEntity.status(HttpStatus.CREATED).body(newContato);
    }

    @DeleteMapping("/deletarContato/{id}")
    public ResponseEntity<Void> deletarContato (@PathVariable int id) {
        contatoService.deletarContato(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
