package com.projeto.biblioteca.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropostaTrocaRequestDTO {

    @NotNull(message = "O ID do livro ofertado não pode ser nulo.")
    private Long livroOfertadoId;

    @NotNull(message = "O ID do livro desejado não pode ser nulo.")
    private Long livroDesejadoId;
}