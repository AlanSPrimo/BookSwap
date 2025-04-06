// cadastra e edita os livros

package com.projeto.biblioteca.dto;

import lombok.Data;

@Data
public class LivroDTO {
    private String titulo;
    private String autor;
    private String isbn;
    private int copiasDisponiveis;
}