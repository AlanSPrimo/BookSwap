package com.projeto.biblioteca.dto;

import com.projeto.biblioteca.model.StatusProposta;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PropostaTrocaResponseDTO {
    private Long id;
    private LivroResumoDTO livroOfertado;
    private LivroResumoDTO livroDesejado;
    private UsuarioResumoDTO propositor;
    private UsuarioResumoDTO receptor;
    private StatusProposta status;
    private LocalDateTime dataProposta;
    private LocalDateTime dataAtualizacaoStatus;
}