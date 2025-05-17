package com.projeto.biblioteca.repository;

import com.projeto.biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// public interface LivroRepository extends JpaRepository<Livro, Long> declara uma
// interface chamada LivroRepository que estende a interface JpaRepository do Spring
// Data JPA.
// JpaRepository<Livro, Long> fornece métodos para realizar operações CRUD (Create,
// Read, Update, Delete) na entidade Livro. 'Livro' é o tipo da entidade que este
// repositório gerencia, e 'Long' é o tipo da chave primária da entidade.
public interface LivroRepository extends JpaRepository<Livro, Long> {
    // List<Livro> findByTituloContainingIgnoreCase(String titulo); declara um método
    // que permite buscar todos os livros cujo título contenha a String fornecida
    // (ignorando a caixa alta ou baixa). O Spring Data JPA automaticamente gera a
    // implementação deste método com base no nome, seguindo a convenção 'findBy<NomeDoCampo>ContainingIgnoreCase'.
    // Ele buscará na coluna 'titulo' da tabela Livro. O método retorna uma List de
    // objetos Livro.
    List<Livro> findByTituloContainingIgnoreCase(String titulo);

    // List<Livro> findByDisponivelTrue(); declara um método que permite buscar todos
    // os livros onde o campo 'disponivel' é verdadeiro. O Spring Data JPA automaticamente
    // gera a implementação deste método com base no nome, seguindo a convenção
    // 'findBy<NomeDoCampo>True'. Ele buscará na coluna 'disponivel' da tabela Livro.
    // O método retorna uma List de objetos Livro.
    List<Livro> findByDisponivelTrue();
}