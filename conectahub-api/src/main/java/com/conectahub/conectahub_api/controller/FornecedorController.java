package com.conectahub.conectahub_api.controller;

import com.conectahub.conectahub_api.model.Fornecedor;
import com.conectahub.conectahub_api.service.FornecedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedores") // Endereço base: http://localhost:8080/api/fornecedores
@CrossOrigin(origins = "*")
public class FornecedorController {

    // O Controller injeta o Service
    // Se esta linha ficar vermelha, é porque falta o arquivo FornecedorService.java
    @Autowired
    private FornecedorService fornecedorService;

    /**
     * Endpoint para LISTAR todos os fornecedores (para a tabela)
     * Método: GET
     * URL: http://localhost:8080/api/fornecedores
     */
    @GetMapping
    public List<Fornecedor> listarTodosFornecedores() {
        return fornecedorService.listarTodos();
    }

    /**
     * Endpoint para CRIAR um novo fornecedor
     * Método: POST
     * URL: http://localhost:8080/api/fornecedores
     * Body (Corpo da requisição): {"razaoSocial": "Sementes S.A.", "cnpj":
     * "12.345.678/0001-99"}
     */
    @PostMapping
    public Fornecedor criarFornecedor(@RequestBody Fornecedor fornecedor) {
        return fornecedorService.salvar(fornecedor);
    }

    /**
     * Endpoint para ATUALIZAR um fornecedor
     * Método: PUT
     * URL: http://localhost:8080/api/fornecedores/1 (onde 1 é o ID)
     * Body (Corpo da requisição): {"razaoSocial": "Novo Nome S.A.", "cnpj":
     * "11.111..."}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> atualizarFornecedor(@PathVariable Long id,
            @RequestBody Fornecedor fornecedorDetails) {
        Fornecedor atualizado = fornecedorService.atualizar(id, fornecedorDetails);
        return ResponseEntity.ok(atualizado); // Retorna "200 OK" com o objeto atualizado
    }

    /**
     * Endpoint para DELETAR um fornecedor
     * Método: DELETE
     * URL: http://localhost:8080/api/fornecedores/1 (onde 1 é o ID)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFornecedor(@PathVariable Long id) {
        fornecedorService.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna "204 No Content" (sucesso sem corpo)
    }
}   