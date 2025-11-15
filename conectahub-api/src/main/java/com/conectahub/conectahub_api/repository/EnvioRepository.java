package com.conectahub.conectahub_api.repository;

import com.conectahub.conectahub_api.model.Envio;
import com.conectahub.conectahub_api.model.StatusEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EnvioRepository extends JpaRepository<Envio, Long> {

    Optional<Envio> findByCodigoLote(String codigoLote);

    long countByStatus(StatusEnvio status);

    List<Envio> findTop10ByOrderByDataCriacaoDesc();
}