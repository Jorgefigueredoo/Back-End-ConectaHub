package com.conectahub.conectahub_api.service;

import com.conectahub.conectahub_api.model.Agricultor;
import com.conectahub.conectahub_api.repository.AgricultorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgricultorService {

    @Autowired
    private AgricultorRepository agricultorRepository;

    /**
     * Busca agricultores pelo nome.
     * Usado na tela "Preparar Novo Envio" na barra de busca.
     *
     * @param nome O termo de busca (pode ser parcial)
     * @return Lista de agricultores encontrados
     */
    public List<Agricultor> buscarAgricultorPorNome(String nome) {
        // Se a busca for vazia ou nula, retorna uma lista vazia
        // para não trazer todos os agricultores do banco.
        if (nome == null || nome.trim().isEmpty()) {
            return List.of(); // Retorna uma lista imutável vazia
        }
        
        // Usa o método que você criou no repositório
        return agricultorRepository.findByNomeContainingIgnoreCase(nome);
    }
}