package com.conectahub.conectahub_api.dto;

import java.math.BigDecimal;

// Este DTO representa o JSON que o front-end vai enviar
// para criar um novo envio (visto na tela "Preparar Novo Envio")
public class CriarEnvioRequestDTO {
    
    private Long agricultorId;
    private Long sementeId;
    private BigDecimal quantidadeKg;
    private String codigoLote; // Opcional, ou podemos gerar

    // Getters e Setters (O Lombok @Data também funcionaria aqui)
    public Long getAgricultorId() {
        return agricultorId;
    }
    public void setAgricultorId(Long agricultorId) {
        this.agricultorId = agricultorId;
    }
    public Long getSementeId() {
        return sementeId;
    }
    public void setSementeId(Long sementeId) {
        this.sementeId = sementeId;
    }
    public BigDecimal getQuantidadeKg() {
        return quantidadeKg;
    }
    public void setQuantidadeKg(BigDecimal quantidadeKg) {
        this.quantidadeKg = quantidadeKg;
    }
    public String getCodigoLote() {
        return codigoLote;
    }
    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }
}