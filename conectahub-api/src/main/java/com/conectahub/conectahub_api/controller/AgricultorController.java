package com.conectahub.conectahub_api.controller;

import com.conectahub.conectahub_api.model.Agricultor;
import com.conectahub.conectahub_api.service.AgricultorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agricultores")
@CrossOrigin(origins = "*") // Permite o acesso do frontend
public class AgricultorController {

    @Autowired
    private AgricultorService agricultorService;

    /**
     * ENDPOINT: GET /api/agricultores/buscar
     * Exemplo de chamada: http://localhost:8080/api/agricultores/buscar?nome=Jose
     *
     * @param nome O parâmetro de busca vindo da URL
     * @return Uma lista de agricultores em formato JSON
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Agricultor>> buscarPorNome(@RequestParam String nome) {
        
        List<Agricultor> agricultores = agricultorService.buscarAgricultorPorNome(nome);
        return ResponseEntity.ok(agricultores); // Retorna 200 OK com a lista
    }
}