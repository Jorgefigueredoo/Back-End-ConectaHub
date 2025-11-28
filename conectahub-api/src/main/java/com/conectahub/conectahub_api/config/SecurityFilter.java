package com.conectahub.conectahub_api.config;

import com.conectahub.conectahub_api.model.Usuario;
import com.conectahub.conectahub_api.repository.UsuarioRepository;
import com.conectahub.conectahub_api.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // ✅ IGNORA ROTAS DE LOGIN E CADASTRO
        if (path.equals("/api/auth/login") || path.equals("/api/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = recoverToken(request);

        // ✅ NÃO bloqueia se não tiver token (deixa o Spring cuidar)
        if (token != null) {
            try {
                String login = tokenService.validarToken(token);

                if (login != null && !login.isEmpty()) {
                    Optional<Usuario> usuario = usuarioRepository.findByEmail(login);

                    if (usuario.isPresent()) {
                        Usuario userEntity = usuario.get();

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userEntity,
                                        null,
                                        userEntity.getAuthorities()
                                );

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                // ✅ Token inválido não quebra a aplicação
                System.out.println("Token inválido ou expirado: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        return authHeader.substring(7);
    }
}
