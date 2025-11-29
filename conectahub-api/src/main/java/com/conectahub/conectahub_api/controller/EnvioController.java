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
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem (CORS)
public class EnvioController {

    @Autowired
    private EnvioService envioService;

    /**
     * 1. CRIAR ENVIO (Status: "Pedido Enviado")
     * Endpoint para criar um novo envio.
     * POST /api/envios
     */
    @PostMapping
    public ResponseEntity<Envio> criarEnvio(@RequestBody CriarEnvioRequestDTO request) {
        try {
            Envio novoEnvio = envioService.criarNovoEnvio(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEnvio);
        } catch (RuntimeException e) {
            // Retorna erro 400 se algo der errado (ex: estoque)
            return ResponseEntity.badRequest().body(null); 
        }
    }

    /**
     * 2. RASTREAR ENVIO
     * Busca detalhes e histórico pelo código.
     * GET /api/envios/buscar/{codigoLote}
     */
    @GetMapping("/buscar/{codigoLote}")
    public ResponseEntity<DetalhesEnvioDTO> buscarEnvioPorCodigoLote(@PathVariable String codigoLote) {
        try {
            DetalhesEnvioDTO detalhes = envioService.buscarDetalhesDoEnvio(codigoLote);
            return ResponseEntity.ok(detalhes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 3. ATUALIZAR PARA "EM ROTA DE ENTREGA"
     * Muda o status para EM_TRANSITO.
     * PUT /api/envios/{id}/em-rota
     */
    @PutMapping("/{id}/em-rota")
    public ResponseEntity<Envio> marcarComoEmRota(@PathVariable Long id) {
        try {
            Envio envioAtualizado = envioService.marcarComoEmTransito(id);
            return ResponseEntity.ok(envioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 4. CONFIRMAR "ENTREGUE"
     * Muda o status para ENTREGUE.
     * PUT /api/envios/{id}/entregue
     */
    @PutMapping("/{id}/entregue")
    public ResponseEntity<Envio> marcarComoEntregue(@PathVariable Long id) {
        try {
            Envio envioAtualizado = envioService.marcarComoEntregue(id);
            return ResponseEntity.ok(envioAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}