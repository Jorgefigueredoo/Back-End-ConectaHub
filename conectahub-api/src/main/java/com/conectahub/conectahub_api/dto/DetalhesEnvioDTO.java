package com.conectahub.conectahub_api.dto;

import com.conectahub.conectahub_api.model.Envio;
import com.conectahub.conectahub_api.model.HistoricoEnvio;

import java.util.List;

// Este DTO representa o JSON que vamos enviar para o front-end
// quando ele pedir os "Detalhes do lote" (para a tela de timeline)
public class DetalhesEnvioDTO {

    private Envio envio;
    private List<HistoricoEnvio> historico;

    public DetalhesEnvioDTO(Envio envio, List<HistoricoEnvio> historico) {
        this.envio = envio;
        this.historico = historico;
    }

    // Getters e Setters
    public Envio getEnvio() {
        return envio;
    }
    public void setEnvio(Envio envio) {
        this.envio = envio;
    }
    public List<HistoricoEnvio> getHistorico() {
        return historico;
    }
    public void setHistorico(List<HistoricoEnvio> historico) {
        this.historico = historico;
    }
}