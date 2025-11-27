package com.conectahub.conectahub_api.service;

import com.conectahub.conectahub_api.dto.LoginRequestDTO;
import com.conectahub.conectahub_api.dto.LoginResponseDTO;
import com.conectahub.conectahub_api.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService; // Vamos precisar criar este serviço

    public LoginResponseDTO efetuarLogin(LoginRequestDTO loginRequest) {
        // 1. Cria um token de autenticação com o email/senha do DTO
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                loginRequest.email(),
                loginRequest.senha()
        );

        // 2. O Spring Security tenta autenticar. Se falhar, ele lança uma exceção.
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        // 3. Se deu certo, pega o 'Usuario' logado
        Usuario usuarioLogado = (Usuario) auth.getPrincipal();

        // 4. Gera um Token JWT para este usuário
        String token = tokenService.gerarToken(usuarioLogado);

        // 5. Retorna o token para o frontend
        return new LoginResponseDTO(token);
    }
}