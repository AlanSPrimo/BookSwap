package com.projeto.biblioteca.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RespostaDTO {
    private String mensagem;
    private Object dados;
    private LocalDateTime timestamp;
    private String status;

    public RespostaDTO(String mensagem, Object dados) {
        this.mensagem = mensagem;
        this.dados = dados;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public static RespostaDTO sucesso(String mensagem, Object dados) {
        return new RespostaDTO(mensagem, dados);
    }

    public static RespostaDTO erro(String mensagem) {
        return new RespostaDTO(mensagem, null);
    }
}