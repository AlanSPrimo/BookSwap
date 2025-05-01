package com.projeto.biblioteca.controller;

import com.projeto.biblioteca.dto.*;
import com.projeto.biblioteca.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Login (usa LoginDTO)
    @PostMapping("/login")
    public RespostaDTO login(@RequestBody LoginDTO loginDTO) {
        return new RespostaDTO(
                "Login realizado!",
                authService.autenticar(loginDTO.getEmail(), loginDTO.getSenha())
        );
    }

    // Registro (usa UsuarioDTO)
    @PostMapping("/registro")
    public RespostaDTO registrar(@RequestBody UsuarioDTO usuarioDTO) {
        return new RespostaDTO(
                "Usuário registrado!",
                authService.registrar(usuarioDTO)
        );
    }
}