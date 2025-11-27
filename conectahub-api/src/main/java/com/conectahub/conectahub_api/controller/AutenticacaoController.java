package com.conectahub.conectahub_api.controller;

import com.conectahub.conectahub_api.dto.LoginRequestDTO;
import com.conectahub.conectahub_api.dto.LoginResponseDTO;
import com.conectahub.conectahub_api.service.AutenticacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // Endpoint base para autenticação
@CrossOrigin(origins = "*")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    /**
     * ENDPOINT: POST /api/auth/login
     * Recebe email e senha, retorna um token JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            LoginResponseDTO response = autenticacaoService.efetuarLogin(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Se o 'authenticationManager.authenticate' falhar (senha errada)
            return ResponseEntity.status(401).build(); // 401 Unauthorized
        }
    }
}