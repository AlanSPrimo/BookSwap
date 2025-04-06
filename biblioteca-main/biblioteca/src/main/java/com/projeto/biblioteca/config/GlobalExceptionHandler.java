package com.projeto.biblioteca.config;

import com.projeto.biblioteca.dto.RespostaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespostaDTO handleGenericException(Exception ex) {
        return RespostaDTO.erro(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RespostaDTO handleNotFoundException(IllegalArgumentException ex) {
        return RespostaDTO.erro(ex.getMessage());
    }
}