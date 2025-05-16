package com.projeto.biblioteca.dto;

import com.projeto.biblioteca.model.Livro;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

// @Data é uma anotação do Lombok que gera automaticamente os métodos boilerplate
// para uma classe Java, como getters para todos os campos, setters para campos não-finais,
// equals, hashCode e toString. Isso reduz a quantidade de código manual necessário.
@Data
public class RespostaDTO {
    // Declara um campo privado chamado 'mensagem' do tipo String.
    // Este campo provavelmente conterá uma mensagem informativa sobre o resultado da operação.
    private String mensagem;

    // Declara um campo privado chamado 'dados' do tipo Object.
    // Este campo pode conter os dados reais da resposta (por exemplo, um objeto, uma lista).
    // O tipo Object permite que diferentes tipos de dados sejam armazenados aqui.
    private Object dados;

    // Declara um campo privado chamado 'timestamp' do tipo LocalDateTime.
    // Este campo armazenará a data e hora em que a resposta foi criada.
    private LocalDateTime timestamp;

    // Declara um campo privado chamado 'status' do tipo String.
    // Este campo provavelmente indicará o status da resposta (por exemplo, "sucesso", "erro").
    private String status;

    // Este é um construtor da classe RespostaDTO que recebe uma mensagem e os dados como argumentos.
    public RespostaDTO(String mensagem, Object dados) {
        // Inicializa o campo 'mensagem' com o valor fornecido.
        this.mensagem = mensagem;
        // Inicializa o campo 'dados' com o valor fornecido.
        this.dados = dados;
        // Inicializa o campo 'timestamp' com a data e hora atuais.
        this.timestamp = LocalDateTime.now();
        // Inicializa o campo 'status'. Note que o valor de 'status' não está sendo definido aqui,
        // o que pode levar a um valor nulo. É recomendável definir um valor padrão ou passá-lo
        // como argumento.
        this.status = status;
    }

    // Este é um segundo construtor da classe RespostaDTO que parece específico para
    // um catálogo de livros disponíveis. No entanto, ele recebe uma String como mensagem
    // e uma List<Livro> como dados, mas não inicializa os campos da classe com esses valores.
    // Isso parece ser um construtor incompleto ou com um propósito específico não claro.
    public RespostaDTO(String catálogoDeLivrosDisponíveis, List<Livro> dados) {
        // Os campos 'mensagem', 'dados', 'timestamp' e 'status' não estão sendo inicializados aqui.
    }

    // Este é um método estático chamado 'sucesso' que retorna uma instância de RespostaDTO
    // indicando sucesso. Ele recebe uma mensagem e os dados como argumentos.
    public static RespostaDTO sucesso(String mensagem, Object dados) {
        // Cria e retorna uma nova instância de RespostaDTO com a mensagem e os dados fornecidos.
        // Novamente, o campo 'status' não está sendo definido aqui.
        return new RespostaDTO(mensagem, dados);
    }

    // Este é um método estático chamado 'erro' que retorna uma instância de RespostaDTO
    // indicando um erro. Ele recebe apenas uma mensagem de erro como argumento.
    public static RespostaDTO erro(String mensagem) {
        // Cria e retorna uma nova instância de RespostaDTO com a mensagem de erro e 'null'
        // para os dados (já que é uma resposta de erro). O campo 'status' também não é definido.
        return new RespostaDTO(mensagem, null);
    }
}