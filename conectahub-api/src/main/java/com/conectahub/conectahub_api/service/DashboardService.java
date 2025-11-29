package com.conectahub.conectahub_api.service;

import com.conectahub.conectahub_api.dto.AtividadeRecenteDTO;
import com.conectahub.conectahub_api.dto.DashboardDTO;
import com.conectahub.conectahub_api.model.StatusEnvio;
import com.conectahub.conectahub_api.repository.EnvioRepository;
import com.conectahub.conectahub_api.repository.HistoricoEnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private EnvioRepository envioRepository;

    @Autowired
    private HistoricoEnvioRepository historicoEnvioRepository;

    public DashboardDTO buscarDadosDashboard() {
        // 1. Buscar estatísticas
        long emTransito = envioRepository.countByStatus(StatusEnvio.EM_TRANSITO);
        
        // Simplificação: Total de entregues (para "Entregues Hoje" seria necessário filtrar por data)
        long entregues = envioRepository.countByStatus(StatusEnvio.ENTREGUE); 

        // Cálculo fictício de taxa (pode ser aprimorado com lógica real depois)
        String taxa = "98%"; 

        // 2. Buscar atividades recentes (últimos 5 históricos)
        var historicos = historicoEnvioRepository.findTop5ByOrderByDataHoraDesc();

        // 3. Converter para DTO
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        
        List<AtividadeRecenteDTO> atividadesDTO = historicos.stream()
            .map(h -> new AtividadeRecenteDTO(
                // Ex: "Lote 1234: Lote saiu pra entrega"
                "Lote " + h.getEnvio().getCodigoLote() + ": " + h.getDescricao(), 
                h.getDataHora().format(formatter)
            ))
            .collect(Collectors.toList());

        return new DashboardDTO(emTransito, entregues, taxa, atividadesDTO);
    }
}