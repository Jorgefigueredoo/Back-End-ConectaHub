package com.conectahub.conectahub_api.model;

import com.conectahub.conectahub_api.model.StatusEnvio;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "envios")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_lote", nullable = false, unique = true)
    private String codigoLote;

    @Column(name = "quantidade_enviada_kg", nullable = false)
    private BigDecimal quantidadeEnviadaKg;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnvio status;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "agricultor_id", nullable = false)
    private Agricultor agricultor; 

    @ManyToOne
    @JoinColumn(name = "semente_id", nullable = false)
    private Semente semente; 

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }
}