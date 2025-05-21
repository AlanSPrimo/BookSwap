package com.projeto.biblioteca.repository;

import com.projeto.biblioteca.model.Livro;
import com.projeto.biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Importar @Query
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByDisponivelTrue();

    // Novo método para buscar todos os livros com seus proprietários
    @Query("SELECT l FROM Livro l JOIN FETCH l.proprietario")
    List<Livro> findAllWithProprietario();

    // Novo método para buscar livros de um proprietário específico, com o proprietário já carregado
    @Query("SELECT l FROM Livro l JOIN FETCH l.proprietario WHERE l.proprietario = :proprietario")
    List<Livro> findByProprietarioWithProprietario(Usuario proprietario);

    // Alternativa se você já tem o proprietarioId e não o objeto Usuario completo:
    // @Query("SELECT l FROM Livro l JOIN FETCH l.proprietario p WHERE p.id = :proprietarioId")
    // List<Livro> findByProprietarioIdWithProprietario(Long proprietarioId);

    @Query("SELECT l FROM Livro l JOIN FETCH l.proprietario WHERE l.proprietario = :proprietario AND l.disponivel = true")
    List<Livro> findByProprietarioAndDisponivelTrueWithProprietario(Usuario proprietario);
}