package com.conectahub.conectahub_api.repository;

import com.conectahub.conectahub_api.model.Envio;
import com.conectahub.conectahub_api.model.StatusEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EnvioRepository extends JpaRepository<Envio, Long> {

    Optional<Envio> findByCodigoLote(String codigoLote);

    long countByStatus(StatusEnvio status);

    List<Envio> findTop10ByOrderByDataCriacaoDesc();

    // --- CORREÇÃO DA QUERY DE RELATÓRIO ---
    // Adicionamos cast para DATE ou ajustamos a lógica para comparar LocalDate com LocalDateTime
    @Query("SELECT e FROM Envio e " +
           "JOIN e.agricultor a " +
           "JOIN e.semente s " +
           "WHERE " +
           "(:agricultorId IS NULL OR e.agricultor.id = :agricultorId) AND " +
           "(:sementeId IS NULL OR e.semente.id = :sementeId) AND " +
           "(:municipio IS NULL OR a.municipio = :municipio) AND " +
           // AQUI ESTÁ A MÁGICA: cast(e.dataCriacao as date)
           "(:dataInicio IS NULL OR cast(e.dataCriacao as date) >= :dataInicio) AND " +
           "(:dataFim IS NULL OR cast(e.dataCriacao as date) <= :dataFim)")
    List<Envio> filtrarEnvios(
        @Param("agricultorId") Long agricultorId,
        @Param("sementeId") Long sementeId,
        @Param("municipio") String municipio,
        @Param("dataInicio") LocalDate dataInicio,
        @Param("dataFim") LocalDate dataFim
    );
}