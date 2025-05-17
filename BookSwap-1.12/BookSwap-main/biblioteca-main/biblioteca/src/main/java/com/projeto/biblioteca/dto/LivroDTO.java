package com.projeto.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.ISBN;
import lombok.Data;

// @Data é uma anotação do Lombok que gera automaticamente os métodos boilerplate
// para uma classe Java, como getters para todos os campos, setters para campos não-finais,
// equals, hashCode e toString. Isso reduz a quantidade de código manual necessário.
@Data
public class LivroDTO {
    // @NotBlank é uma anotação do Bean Validation que garante que o campo 'titulo'
    // não seja nulo nem contenha apenas espaços em branco. A mensagem especificada
    // será usada se a validação falhar.
    @NotBlank(message = "O título não pode estar em branco.")
    // @Size é uma anotação do Bean Validation que define as restrições de tamanho
    // para o campo 'titulo'. 'min' especifica o tamanho mínimo (3 caracteres) e 'max'
    // especifica o tamanho máximo (255 caracteres). A mensagem é exibida em caso de violação.
    @Size(min = 3, max = 255, message = "O título deve ter entre 3 e 255 caracteres.")
    private String titulo;

    // Similar a 'titulo', @NotBlank garante que o campo 'autor' não esteja em branco,
    // e @Size define as restrições de tamanho para o nome do autor.
    @NotBlank(message = "O autor não pode estar em branco.")
    @Size(min = 3, max = 255, message = "O nome do autor deve ter entre 3 e 255 caracteres.")
    private String autor;

    // @NotBlank garante que o campo 'isbn' não esteja em branco.
    @NotBlank(message = "O ISBN não pode estar em branco.")
    // @ISBN é uma anotação do Hibernate Validator que verifica se o valor do campo
    // 'isbn' é um ISBN válido (International Standard Book Number). A mensagem é exibida
    // se o formato for inválido.
    @ISBN(message = "ISBN inválido.")
    private String isbn;

    // @PositiveOrZero é uma anotação do Bean Validation que garante que o valor do campo
    // 'copiasDisponiveis' seja um número maior ou igual a zero. A mensagem é exibida
    // se o valor for negativo.
    @PositiveOrZero(message = "As cópias disponíveis devem ser um número positivo ou zero.")
    private int copiasDisponiveis;
}