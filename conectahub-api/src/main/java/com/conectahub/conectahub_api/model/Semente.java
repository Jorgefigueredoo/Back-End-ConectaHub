package com.conectahub.conectahub_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "sementes") // Tabela agora é "sementes"
public class Semente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_semente", unique = true, nullable = false)
    private String tipoSemente; // Ex: Milho, Soja, Feijão

    @Column(name = "quantidade_kg", nullable = false)
    private BigDecimal quantidadeKg;

    // Para o status "Estoque Baixo" da tela
    @Column(name = "nivel_minimo_kg", nullable = false)
    private BigDecimal nivelMinimoKg; 

    @Column(name = "data_ultima_entrada")
    private LocalDate dataUltimaEntrada;

    /**
     * Este é um campo "calculado".
     * A anotação @Transient diz ao JPA para NÃO salvar isso no banco.
     * Nós usamos isso para mostrar o status na tela de "Controle de Estoque".
     */
    @Transient
    public String getStatus() {
        if (quantidadeKg.compareTo(BigDecimal.ZERO) <= 0) {
            return "Sem Estoque";
        }
        if (quantidadeKg.compareTo(nivelMinimoKg) <= 0) {
            return "Estoque Baixo";
        }
        return "Disponível";
    }
}