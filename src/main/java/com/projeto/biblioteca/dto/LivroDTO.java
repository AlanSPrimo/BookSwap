package com.projeto.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern; // Importar para validação de padrão
import jakarta.validation.constraints.Size;
// import org.hibernate.validator.constraints.ISBN; // REMOVER esta importação
import lombok.Data;

@Data
public class LivroDTO {

    @NotBlank(message = "O título não pode estar em branco.")
    @Size(min = 1, max = 255, message = "O título deve ter entre 1 e 255 caracteres.")
    private String titulo;

    @NotBlank(message = "O autor não pode estar em branco.")
    @Size(min = 1, max = 255, message = "O nome do autor deve ter entre 1 e 255 caracteres.")
    private String autor;

    @NotBlank(message = "O código do livro não pode estar em branco.") // Mensagem ajustada
    // @ISBN(message = "ISBN inválido.") // REMOVER esta linha
    // OPCIONAL: Se quiser permitir apenas números para o código (como "0001", "1234")
    @Pattern(regexp = "^[0-9]+$", message = "O código do livro deve conter apenas números.")
    @Size(min = 1, max = 20, message = "O código do livro deve ter entre 1 e 20 caracteres.") // Ajuste o tamanho conforme necessário
    private String isbn; // O nome do campo pode continuar "isbn" ou ser renomeado para algo como "codigoLivro" se preferir

    @NotNull(message = "O campo de disponibilidade deve ser informado.")
    private Boolean disponivel;
}