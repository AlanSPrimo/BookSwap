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

    @PostMapping("/login")
    public RespostaDTO login(@RequestBody LoginDTO loginDTO) {
        System.out.println("AuthController: Recebido login para email: " + loginDTO.getEmail()); // DEBUG
        try {
            String resultadoAutenticacao = authService.autenticar(loginDTO.getEmail(), loginDTO.getSenha());
            System.out.println("AuthController: Autenticação bem-sucedida, resultado: " + resultadoAutenticacao); // DEBUG
            // Use o método estático RespostaDTO.sucesso
            return RespostaDTO.sucesso("Login realizado com sucesso!", resultadoAutenticacao);
        } catch (RuntimeException e) {
            // Este catch é útil para debug, mas o GlobalExceptionHandler também pegaria.
            // Se você tem um GlobalExceptionHandler que já retorna RespostaDTO.erro,
            // você poderia simplesmente relançar a exceção: throw e;
            // Ou, para controle explícito aqui:
            System.err.println("AuthController: Erro na autenticação - " + e.getMessage()); // DEBUG
            return RespostaDTO.erro(e.getMessage());
        }
    }

    @PostMapping("/registro")
    public RespostaDTO registrar(@RequestBody UsuarioDTO usuarioDTO) {
        // Similarmente, para consistência, você pode usar try-catch e os métodos estáticos aqui também
        try {
            Object usuarioRegistrado = authService.registrar(usuarioDTO);
            return RespostaDTO.sucesso("Usuário registrado!", usuarioRegistrado);
        } catch (Exception e) { // Captura genérica, pode ser mais específica
            System.err.println("AuthController: Erro no registro - " + e.getMessage()); // DEBUG
            return RespostaDTO.erro(e.getMessage());
        }
    }
}