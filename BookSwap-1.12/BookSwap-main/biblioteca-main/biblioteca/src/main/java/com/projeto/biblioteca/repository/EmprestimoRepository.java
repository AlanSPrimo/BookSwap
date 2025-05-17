package com.projeto.biblioteca.repository;

import com.projeto.biblioteca.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> declara
// uma interface chamada EmprestimoRepository que estende a interface JpaRepository
// do Spring Data JPA.
// JpaRepository<Emprestimo, Long> fornece métodos para realizar operações CRUD
// (Create, Read, Update, Delete) na entidade Emprestimo. 'Emprestimo' é o tipo da
// entidade que este repositório gerencia, e 'Long' é o tipo da chave primária da entidade.
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    // List<Emprestimo> findByUsuarioId(Long usuarioId); declara um método que permite
    // buscar todos os empréstimos associados a um determinado ID de usuário. O Spring Data JPA
    // automaticamente gera a implementação deste método com base no nome, seguindo a convenção
    // 'findBy<NomeDoCampo>'. Neste caso, ele buscará na coluna 'usuarioId' da tabela Emprestimo.
    // O método retorna uma List de objetos Emprestimo.
    List<Emprestimo> findByUsuarioId(Long usuarioId);

    // List<Emprestimo> findByDevolvidoFalse(); declara um método que permite buscar todos
    // os empréstimos onde o campo 'devolvido' é falso (ou seja, os livros ainda não foram
    // devolvidos). Similar ao método anterior, o Spring Data JPA gera a implementação
    // automaticamente com base no nome. O método retorna uma List de objetos Emprestimo.
    List<Emprestimo> findByDevolvidoFalse();
}