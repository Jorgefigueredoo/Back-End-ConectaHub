package com.conectahub.conectahub_api.controller;

import com.conectahub.conectahub_api.dto.CriarEnvioRequestDTO;
import com.conectahub.conectahub_api.dto.DetalhesEnvioDTO;
import com.conectahub.conectahub_api.model.Envio;
import com.conectahub.conectahub_api.service.EnvioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/envios") // Endereço base: http://localhost:8080/api/envios
@CrossOrigin(origins = "*")
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    @PostMapping
    public ResponseEntity<Envio> criarEnvio(@RequestBody CriarEnvioRequestDTO request) {
        try {
            Envio novoEnvio = envioService.criarNovoEnvio(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEnvio);
        } catch (RuntimeException e) {

            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/buscar/{codigoLote}")
    public ResponseEntity<DetalhesEnvioDTO> buscarEnvioPorCodigoLote(@PathVariable String codigoLote) {
        try {
            DetalhesEnvioDTO detalhes = envioService.buscarDetalhesDoEnvio(codigoLote);
            return ResponseEntity.ok(detalhes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}