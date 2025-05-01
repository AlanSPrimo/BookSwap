package com.projeto.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.ISBN;
import lombok.Data;

@Data
public class LivroDTO {
    @NotBlank(message = "O título não pode estar em branco.")
    @Size(min = 3, max = 255, message = "O título deve ter entre 3 e 255 caracteres.")
    private String titulo;

    @NotBlank(message = "O autor não pode estar em branco.")
    @Size(min = 3, max = 255, message = "O nome do autor deve ter entre 3 e 255 caracteres.")
    private String autor;

    @NotBlank(message = "O ISBN não pode estar em branco.")
    @ISBN(message = "ISBN inválido.")
    private String isbn;

    @PositiveOrZero(message = "As cópias disponíveis devem ser um número positivo ou zero.")
    private int copiasDisponiveis;
}