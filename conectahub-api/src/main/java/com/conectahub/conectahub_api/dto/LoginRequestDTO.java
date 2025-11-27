package com.conectahub.conectahub_api.dto;

// Usando 'record' para um DTO simples e imutável
public record LoginRequestDTO(String email, String senha) {
}