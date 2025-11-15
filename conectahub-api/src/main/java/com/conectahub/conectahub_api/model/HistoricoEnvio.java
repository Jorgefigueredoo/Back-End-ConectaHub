package com.conectahub.conectahub_api.model;

import com.conectahub.conectahub_api.model.StatusEnvio;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "historico_envios")
public class HistoricoEnvio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEnvio status;

    private String descricao;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "envio_id", nullable = false)
    private Envio envio;

    @PrePersist
    public void prePersist() {
        this.dataHora = LocalDateTime.now();
    }

    public HistoricoEnvio(Envio envio, StatusEnvio status, String descricao) { 
        this.envio = envio;
        this.status = status;
        this.descricao = descricao;
    }
}