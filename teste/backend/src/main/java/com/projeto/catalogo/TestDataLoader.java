
package com.projeto.catalogo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Esse aqui só roda uma vez na inicialização e coloca os livros no banco
@Component
public class TestDataLoader implements CommandLineRunner {

    private final LivroRepository repo;

    public TestDataLoader(LivroRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {
            repo.save(new Livro("Livro 1", "Autor A", 0.00, "Somente Troca", "Fantasia"));
            repo.save(new Livro("Livro 2", "Autor B", 5.00, "Troca e Venda", "Fantasia"));
            repo.save(new Livro("Livro 3", "Autor C", 10.90, "Somente Venda", "Fantasia"));
            repo.save(new Livro("Livro 4", "Autor D", 0.00, "Somente Troca", "Fantasia"));
            repo.save(new Livro("Livro 5", "Autor E", 7.50, "Troca e Venda", "Fantasia"));
            System.out.println("📚 Livros de exemplo inseridos no banco!");
        }
    }
}
