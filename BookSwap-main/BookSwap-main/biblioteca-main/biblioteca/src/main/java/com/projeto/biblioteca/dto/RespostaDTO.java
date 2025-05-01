package com.projeto.biblioteca.dto;

import com.projeto.biblioteca.model.Livro;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RespostaDTO {
    private String mensagem;
    private Object dados;
    private LocalDateTime timestamp;
    private String status;

    public RespostaDTO(String mensagem, Object dados) {
        this.mensagem = mensagem;
        this.dados = dados;
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }

    public RespostaDTO(String catálogoDeLivrosDisponíveis, List<Livro> dados) {

    }

    public static RespostaDTO sucesso(String mensagem, Object dados) {
        return new RespostaDTO(mensagem, dados);
    }

    public static RespostaDTO erro(String mensagem) {
        return new RespostaDTO(mensagem, null);
    }
}