package com.projeto.biblioteca.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivroResumoDTO {
    private Long id;
    private String titulo;
    private String autor;
    // private String proprietarioNome; // Pode ser adicionado se necessário no resumo
}