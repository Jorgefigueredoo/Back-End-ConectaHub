package com.conectahub.conectahub_api.repository;

import com.conectahub.conectahub_api.model.HistoricoEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoricoEnvioRepository extends JpaRepository<HistoricoEnvio, Long> {

    List<HistoricoEnvio> findByEnvioIdOrderByDataHoraAsc(Long envioId);
}