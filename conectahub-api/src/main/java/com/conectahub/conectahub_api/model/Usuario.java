package com.conectahub.conectahub_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "senha_hash", nullable = false)
    private String senha;

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        // Retorna a senha (que o Spring Security vai comparar)
        return this.senha;
    }

    @Override
    public String getUsername() {
        // Retorna o 'username' (que no nosso caso é o email)
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // A conta nunca expira
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // A conta nunca é bloqueada
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // As credenciais nunca expiram
        return true;
    }

    @Override
    public boolean isEnabled() {
        // A conta está sempre habilitada
        return true;
    }
}