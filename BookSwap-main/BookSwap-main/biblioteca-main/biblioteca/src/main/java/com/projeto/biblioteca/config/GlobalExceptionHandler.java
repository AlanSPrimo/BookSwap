package com.projeto.biblioteca.config;

import com.projeto.biblioteca.dto.RespostaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

// @RestControllerAdvice é uma anotação do Spring que indica que esta classe
// lida com exceções lançadas pelos controllers (@RestController). Ela atua como um
// interceptador de exceções em nível global para todos os controllers.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler(Exception.class) define que este metodo irá lidar com exceções
    // do tipo Exception ou de qualquer de suas subclasses que forem lançadas nos controllers.
    @ExceptionHandler(Exception.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) define o código de status HTTP
    // que será retornado na resposta quando uma Exception genérica for capturada.
    // HttpStatus.INTERNAL_SERVER_ERROR (código 500) indica um erro interno no servidor.
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespostaDTO handleGenericException(Exception ex) {
        // Cria e retorna um objeto RespostaDTO com uma mensagem de erro genérica.
        // O metodo erro() de RespostaDTO provavelmente formata a resposta para indicar um erro.
        return RespostaDTO.erro("Ocorreu um erro interno no servidor.");
    }

    // @ExceptionHandler(IllegalArgumentException.class) define que este metodo irá lidar com
    // exceções do tipo IllegalArgumentException que forem lançadas nos controllers.
    @ExceptionHandler(IllegalArgumentException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST) define o código de status HTTP que será
    // retornado quando uma IllegalArgumentException for capturada.
    // HttpStatus.BAD_REQUEST (código 400) indica que a requisição do cliente estava inválida.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaDTO handleNotFoundException(IllegalArgumentException ex) {
        // Cria e retorna um objeto RespostaDTO com a mensagem de erro da IllegalArgumentException.
        return RespostaDTO.erro(ex.getMessage());
    }

    // @ExceptionHandler(MethodArgumentNotValidException.class) define que este metodo irá lidar com
    // exceções do tipo MethodArgumentNotValidException. Essa exceção é lançada automaticamente
    // pelo Spring quando a validação de um objeto anotado com @Valid falha.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // @ResponseStatus(HttpStatus.BAD_REQUEST) define o código de status HTTP que será
    // retornado quando uma MethodArgumentNotValidException for capturada.
    // HttpStatus.BAD_REQUEST (código 400) indica que a requisição do cliente estava inválida
    // devido a erros de validação.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Cria um mapa para armazenar os erros de validação. A chave será o nome do campo
        // com erro e o valor será a mensagem de erro.
        Map<String, String> errors = new HashMap<>();
        // Obtém o resultado da validação (BindingResult) da exceção e itera sobre todos os
        // erros de campo (FieldError) encontrados.
        ex.getBindingResult().getFieldErrors().forEach(error ->
                // Para cada erro de campo, adiciona ao mapa o nome do campo e a mensagem de erro padrão.
                errors.put(error.getField(), error.getDefaultMessage()));
        // Cria e retorna um objeto RespostaDTO indicando um erro de validação e incluindo
        // o mapa de erros detalhados no corpo da resposta.
        return new RespostaDTO("Erro de validação", errors);
    }
}