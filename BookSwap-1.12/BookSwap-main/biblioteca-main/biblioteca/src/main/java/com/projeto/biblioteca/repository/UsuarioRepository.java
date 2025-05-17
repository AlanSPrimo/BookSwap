package com.projeto.biblioteca.repository;

import com.projeto.biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// public interface UsuarioRepository extends JpaRepository<Usuario, Long> declara uma
// interface chamada UsuarioRepository que estende a interface JpaRepository do Spring
// Data JPA.
// JpaRepository<Usuario, Long> fornece métodos para realizar operações CRUD (Create,
// Read, Update, Delete) na entidade Usuario. 'Usuario' é o tipo da entidade que este
// repositório gerencia, e 'Long' é o tipo da chave primária da entidade.
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Optional<Usuario> findByEmail(String email); declara um método que permite buscar
    // um usuário pelo seu endereço de email. O Spring Data JPA automaticamente gera a
    // implementação deste método com base no nome, seguindo a convenção 'findBy<NomeDoCampo>'.
    // Ele buscará na coluna 'email' da tabela Usuario.
    // O método retorna um Optional<Usuario>. Optional é um container que pode ou não
    // conter um valor não-nulo. É usado aqui para lidar com o caso em que nenhum usuário
    // com o email fornecido é encontrado.
    Optional<Usuario> findByEmail(String email);
}