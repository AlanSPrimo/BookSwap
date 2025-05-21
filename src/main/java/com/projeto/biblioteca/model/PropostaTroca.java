package com.projeto.biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp; // Para dataProposta
import org.hibernate.annotations.UpdateTimestamp; // Para dataAtualizacaoStatus

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "proposta_troca")
public class PropostaTroca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_ofertado_id", nullable = false)
    private Livro livroOfertado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_desejado_id", nullable = false)
    private Livro livroDesejado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propositor_id", nullable = false)
    private Usuario propositor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receptor_id", nullable = false)
    private Usuario receptor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProposta status;

    @CreationTimestamp // Define automaticamente no momento da criação
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataProposta;

    @UpdateTimestamp // Define automaticamente no momento da criação e atualização
    @Column(nullable = false)
    private LocalDateTime dataAtualizacaoStatus;

    public PropostaTroca(Livro livroOfertado, Livro livroDesejado, Usuario propositor, Usuario receptor) {
        this.livroOfertado = livroOfertado;
        this.livroDesejado = livroDesejado;
        this.propositor = propositor;
        this.receptor = receptor;
        this.status = StatusProposta.PENDENTE; // Status inicial
    }
}