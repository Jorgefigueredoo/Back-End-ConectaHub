package com.conectahub.conectahub_api.dto;

public record RegisterRequestDTO(
    String name, 
    String login, 
    String password, 
    String role // <--- Mudou para String. O front manda "ADMIN" ou "USER"
) {
}