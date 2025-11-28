package com.conectahub.conectahub_api.controller;

import com.conectahub.conectahub_api.dto.LoginRequestDTO;
import com.conectahub.conectahub_api.dto.LoginResponseDTO;
import com.conectahub.conectahub_api.dto.RegisterRequestDTO;
import com.conectahub.conectahub_api.model.Usuario;
import com.conectahub.conectahub_api.service.AutenticacaoService;
import com.conectahub.conectahub_api.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager; // 1. Injetamos quem faz o login de verdade

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO data) { // Mudei o nome da variável pra 'data' pra ficar
                                                                     // mais curto
        System.out.println(">>> O PEDIDO CHEGOU! Email: " + data.email());

        // 2. Cria o objeto de autenticação usando EMAIL e SENHA (do seu DTO)
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());

        // 3. O Manager verifica a senha no banco. (Isso evita o loop infinito)
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // 4. Se a senha estiver certa, gera o token
        // Mude de .generateToken para .gerarToken
        var token = tokenService.gerarToken((Usuario) auth.getPrincipal()); // Verifique se o método chama generateToken
                                                                            // ou gerarToken

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        // Apenas chamamos o método. Se o usuário já existir, o Service vai lançar um
        // erro.
        this.autenticacaoService.registrar(body);

        return ResponseEntity.ok().build();
    }
}