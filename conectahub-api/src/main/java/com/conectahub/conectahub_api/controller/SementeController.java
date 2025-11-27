package com.conectahub.conectahub_api.controller;

import com.conectahub.conectahub_api.model.Semente;
import com.conectahub.conectahub_api.service.SementeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sementes") // Define o URL base para este controller (ex:
                                 // http://localhost:8080/api/sementes)
public class SementeController {

    // Injeta o serviço que acabamos de criar
    @Autowired
    private SementeService sementeService;

    /**
     * Endpoint para listar todo o estoque de sementes.
     * A tela "Controle de Estoque" deve chamar este endpoint.
     * * @return Uma lista (JSON) de todas as sementes.
     */
    @GetMapping
    public ResponseEntity<List<Semente>> listarEstoque() {
        List<Semente> sementes = sementeService.listarTodasSementes();
        return ResponseEntity.ok(sementes); // Retorna 200 OK com a lista no corpo da resposta
    }

    // @GetMapping("/buscar")
    // public ResponseEntity<List<Semente>> buscarPorNome(@RequestParam String nome)
    // {
    // // (Você precisaria criar o método 'buscarPorNome' no service e repository)
    // List<Semente> sementes = sementeService.buscarPorNome(nome);
    // return ResponseEntity.ok(sementes);
    // }
    // ---
}