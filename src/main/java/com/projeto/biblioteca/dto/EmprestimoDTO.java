package com.projeto.biblioteca.dto;

import lombok.Data;

// @Data é uma anotação do Lombok que gera automaticamente os métodos boilerplate
// para uma classe Java, como getters para todos os campos, setters para campos não-finais,
// equals, hashCode e toString. Isso reduz a quantidade de código manual necessário.
@Data
public class EmprestimoDTO {
    // Declara um campo privado chamado livroId do tipo Long.
    // Este campo provavelmente será usado para transportar o ID do livro em operações
    // relacionadas a empréstimos (como alugar ou devolver).
    private Long livroId;

    // Declara um campo privado chamado usuarioId do tipo Long.
    // Este campo provavelmente será usado para transportar o ID do usuário em operações
    // relacionadas a empréstimos (como alugar ou devolver), indicando quem está envolvido
    // no empréstimo.
    private Long usuarioId;
}