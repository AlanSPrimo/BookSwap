package com.projeto.biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Livro livro;

    @ManyToOne
    private Usuario usuario;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean devolvido = false;
}