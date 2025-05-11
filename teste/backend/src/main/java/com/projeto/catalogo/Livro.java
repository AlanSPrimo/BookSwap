
package com.projeto.catalogo;

import jakarta.persistence.*;

@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String autor;
    private Double preco;
    private String modalidade;
    private String categoria;

    // Construtores padrão e completo
    public Livro() {}

    public Livro(String titulo, String autor, Double preco, String modalidade, String categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.preco = preco;
        this.modalidade = modalidade;
        this.categoria = categoria;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public String getModalidade() { return modalidade; }
    public void setModalidade(String modalidade) { this.modalidade = modalidade; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
