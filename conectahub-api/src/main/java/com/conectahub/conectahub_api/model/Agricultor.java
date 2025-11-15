package com.conectahub.conectahub_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "agricultores") // Tabela agora é "agricultores"
public class Agricultor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "cpf_cnpj", nullable = false, unique = true)
    private String cpfCnpj; // Ex: "José da Silva | CPF: 123.456..."

    private String municipio;

    private String uf; // Ex: "PE"
}