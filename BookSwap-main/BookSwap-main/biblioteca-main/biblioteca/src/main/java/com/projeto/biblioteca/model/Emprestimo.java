package com.projeto.biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

// @Data é uma anotação do Lombok que gera automaticamente os métodos boilerplate
// para uma classe Java, como getters para todos os campos, setters para campos não-finais,
// equals, hashCode e toString. Isso reduz a quantidade de código manual necessário.
@Data
// @Entity é uma anotação da JPA (Java Persistence API) que marca esta classe como
// uma entidade, o que significa que ela representa uma tabela no banco de dados.
@Entity
public class Emprestimo {
    // @Id é uma anotação da JPA que especifica a chave primária da entidade.
    @Id
    // @GeneratedValue é uma anotação da JPA que define a estratégia de geração
    // para o valor da chave primária. GenerationType.IDENTITY indica que a geração
    // será gerenciada pela coluna de identidade do banco de dados (auto incremento).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne é uma anotação da JPA que define um relacionamento de muitos-para-um
    // entre esta entidade (Emprestimo) e a entidade Livro. Muitos empréstimos podem
    // estar associados a um único livro.
    @ManyToOne
    // @JoinColumn é uma anotação da JPA que especifica a coluna na tabela Emprestimo
    // que será usada como chave estrangeira para referenciar a tabela Livro.
    // 'name = "livro_id"' indica que a coluna se chamará 'livro_id'.
    @JoinColumn(name = "livro_id")
    private Livro livro;

    // @ManyToOne Similar ao relacionamento com Livro, define um relacionamento de
    // muitos-para-um entre Emprestimo e Usuario. Muitos empréstimos podem estar
    // associados a um único usuário.
    @ManyToOne
    // @JoinColumn Especifica a coluna na tabela Emprestimo que será a chave estrangeira
    // para referenciar a tabela Usuario. 'name = "usuario_id"' indica que a coluna
    // se chamará 'usuario_id'.
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Declara um campo privado chamado 'dataEmprestimo' do tipo LocalDate.
    // Este campo armazenará a data em que o empréstimo foi realizado.
    private LocalDate dataEmprestimo;

    // Declara um campo privado chamado 'dataDevolucao' do tipo LocalDate.
    // Este campo armazenará a data prevista ou real da devolução do livro.
    private LocalDate dataDevolucao;

    // Declara um campo privado chamado 'devolvido' do tipo boolean.
    // Este campo indicará se o livro já foi devolvido (true) ou não (false).
    // O valor padrão é false.
    private boolean devolvido = false;
}