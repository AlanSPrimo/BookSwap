package com.projeto.biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;

// @Data é uma anotação do Lombok que gera automaticamente os métodos boilerplate
// para uma classe Java, como getters para todos os campos, setters para campos não-finais,
// equals, hashCode e toString. Isso reduz a quantidade de código manual necessário.
@Data
// @Entity é uma anotação da JPA (Java Persistence API) que marca esta classe como
// uma entidade, o que significa que ela representa uma tabela no banco de dados.
@Entity
public class Livro {
    // @Id é uma anotação da JPA que especifica a chave primária da entidade.
    @Id
    // @GeneratedValue é uma anotação da JPA que define a estratégia de geração
    // para o valor da chave primária. GenerationType.IDENTITY indica que a geração
    // será gerenciada pela coluna de identidade do banco de dados (auto incremento).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Declara um campo privado chamado 'titulo' do tipo String.
    // Este campo armazenará o título do livro.
    private String titulo;

    // Declara um campo privado chamado 'autor' do tipo String.
    // Este campo armazenará o nome do autor do livro.
    private String autor;

    // Declara um campo privado chamado 'isbn' do tipo String.
    // Este campo armazenará o ISBN (International Standard Book Number) do livro,
    // que é um identificador único para livros.
    private String isbn;

    // O campo 'disponivel' indicará se este exemplar único está disponível para troca/empréstimo
    private boolean disponivel = true;
    // private int copiasDisponiveis; // REMOVIDO

    @ManyToOne(fetch = FetchType.LAZY) // EAGER pode ser usado se você sempre precisar do proprietário com o livro
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Usuario proprietario;
}