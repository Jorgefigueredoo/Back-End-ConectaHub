package com.conectahub.conectahub_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat; // Importante!
import java.time.LocalDate;

public class RelatorioFiltroDTO {

    private Long agricultorId;
    private Long sementeId;
    private String municipio;

    // A anotação diz: "Aceite datas no formato Ano-Mês-Dia"
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFim;

    // --- Getters e Setters ---

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

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }
}