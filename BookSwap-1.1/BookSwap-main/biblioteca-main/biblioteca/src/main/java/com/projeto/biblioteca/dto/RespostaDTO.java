package com.projeto.biblioteca.dto;

import com.projeto.biblioteca.model.Livro; // Mantido se você ainda usa esse construtor específico
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List; // Mantido se você ainda usa esse construtor específico

@Data
public class RespostaDTO {
    private String mensagem;
    private Object dados;
    private LocalDateTime timestamp;
    private String status; // "sucesso" ou "erro"

    // Construtor privado para ser usado pelos métodos estáticos de fábrica
    private RespostaDTO(String status, String mensagem, Object dados) {
        this.status = status;
        this.mensagem = mensagem;
        this.dados = dados;
        this.timestamp = LocalDateTime.now();
    }

    // Construtor público original (mantido para compatibilidade se usado em outro lugar,
    // mas observe que ele NÃO define o status, o que é o problema que estamos tentando corrigir)
    // Se possível, refatore o código para usar os métodos estáticos sucesso() e erro().
    public RespostaDTO(String mensagem, Object dados) {
        this.mensagem = mensagem;
        this.dados = dados;
        this.timestamp = LocalDateTime.now();
        // this.status continua nulo aqui, o que é problemático para o frontend
        // Considere adicionar: this.status = "indefinido"; ou lançar uma exceção.
        // Melhor ainda: tornar este construtor privado ou deprecá-lo.
    }

    // Este construtor também não inicializa os campos da classe corretamente.
    // Revise se ele é realmente necessário ou corrija-o.
    public RespostaDTO(String catálogoDeLivrosDisponíveis, List<Livro> dados) {
        // Os campos 'mensagem', 'dados', 'timestamp' e 'status' não estão sendo inicializados aqui.
        // Isso parece um erro. Se for necessário, deveria ser:
        // this("sucesso", catálogoDeLivrosDisponíveis, dados); // Ou status apropriado
    }

    // Método estático de fábrica para respostas de SUCESSO
    public static RespostaDTO sucesso(String mensagem, Object dados) {
        return new RespostaDTO("sucesso", mensagem, dados);
    }

    // Método estático de fábrica para respostas de ERRO
    public static RespostaDTO erro(String mensagem) {
        return new RespostaDTO("erro", mensagem, null);
    }

    // Se você precisa de um erro com dados (raro, mas possível)
    public static RespostaDTO erro(String mensagem, Object dadosErro) {
        return new RespostaDTO("erro", mensagem, dadosErro);
    }
}