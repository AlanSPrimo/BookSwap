
package com.projeto.catalogo;

import org.springframework.web.bind.annotation.*;
import java.util.List;

// Esse cara expõe os dados pro frontend acessar
@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroRepository repository;

    public LivroController(LivroRepository repository) {
        this.repository = repository;
    }

    // Endpoint pra pegar todos os livros cadastrados (pra catálogo geral)
    @GetMapping("/publicos")
    public List<Livro> listarLivros() {
        return repository.findAll();
    }

    // Se quiser filtrar por categoria (tipo fantasia, romance, etc)
    @GetMapping("/categoria/{categoria}")
    public List<Livro> porCategoria(@PathVariable String categoria) {
        return repository.findByCategoria(categoria);
    }
}
