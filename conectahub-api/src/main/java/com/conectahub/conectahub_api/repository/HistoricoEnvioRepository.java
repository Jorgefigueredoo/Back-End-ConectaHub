package com.conectahub.conectahub_api.repository;

import com.conectahub.conectahub_api.model.HistoricoEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoricoEnvioRepository extends JpaRepository<HistoricoEnvio, Long> {

    // Método já existente
    List<HistoricoEnvio> findByEnvioIdOrderByDataHoraAsc(Long envioId);

    // --- CORREÇÃO AQUI ---
    // Mudamos de 'Data' para 'DataHora' para bater com o nome do campo na sua classe
    List<HistoricoEnvio> findTop5ByOrderByDataHoraDesc();
}