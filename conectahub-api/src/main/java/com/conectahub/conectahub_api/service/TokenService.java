package com.conectahub.conectahub_api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.conectahub.conectahub_api.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Você precisa definir essa chave secreta no seu 'application.properties'
    // Ex: jwt.secret=minha-chave-secreta-super-longa
    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("conectahub-api") // Quem está emitindo o token
                    .withSubject(usuario.getEmail()) // O usuário (email)
                    .withExpiresAt(getExpirationDate()) // Data de expiração
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    // Método para validar o token (usado pelo Spring Security)
    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("conectahub-api")
                    .build()
                    .verify(token)
                    .getSubject(); // Retorna o email (subject) do usuário
        } catch (Exception exception) {
            return ""; // Retorna vazio se o token for inválido
        }
    }

    private Instant getExpirationDate() {
        // Define o token para expirar em 2 horas
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}