package com.conectahub.conectahub_api.dto;

import java.util.List;

public record DashboardDTO(
    long emTransito,
    long entreguesHoje,
    String taxaConfirmacao, // Ex: "92%"
    List<AtividadeRecenteDTO> atividades
) {}

// DTO interno para a lista de atividades
record AtividadeRecenteDTO(
    String descricao,
    String dataHora
) {}