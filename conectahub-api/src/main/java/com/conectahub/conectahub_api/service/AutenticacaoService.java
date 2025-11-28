package com.conectahub.conectahub_api.service;

import com.conectahub.conectahub_api.dto.RegisterRequestDTO;
import com.conectahub.conectahub_api.model.Usuario;
import com.conectahub.conectahub_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    // ✅ MÉTODO 1: Ensina o Spring a buscar usuário (Sem loop!)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    // ✅ MÉTODO 2: Apenas cadastra (Não faz login aqui!)
   public void registrar(RegisterRequestDTO data) {
        if (this.repository.findByEmail(data.login()).isPresent()) {
            throw new RuntimeException("Este email já está em uso!");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        
        // --- CORREÇÃO AQUI ---
        // A ordem aqui TEM que bater com o construtor da classe Usuario.java
        // Geralmente é: (Nome, Email, Senha, Role)
        Usuario newUser = new Usuario(
            data.name(),        // 1. Nome (Verifique se seu DTO tem .name())
            data.login(),       // 2. Email
            encryptedPassword,  // 3. Senha Criptografada
            data.role()         // 4. Role (permissão)
        );

        this.repository.save(newUser);
    }
    
    // ❌ IMPORTANTE: O método 'efetuarLogin' e o 'AuthenticationManager' FORAM REMOVIDOS DAQUI.
    // A lógica de login agora vive exclusivamente no Controller.
}