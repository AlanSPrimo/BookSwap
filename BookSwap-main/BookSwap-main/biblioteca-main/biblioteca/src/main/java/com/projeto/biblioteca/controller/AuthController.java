package com.projeto.biblioteca.controller;

import com.projeto.biblioteca.dto.*;
import com.projeto.biblioteca.service.AuthService;
import org.springframework.web.bind.annotation.*;

// @RestController é uma anotação do Spring que indica que esta classe é um controller REST.
// Isso significa que os métodos desta classe irão lidar com requisições web e retornar
// respostas no formato REST (geralmente JSON ou XML).
@RestController
// @RequestMapping("/auth") define o caminho base para todos os endpoints definidos
// nesta classe. Todas as URLs acessíveis através deste controller começarão com "/auth".
@RequestMapping("/auth")
public class AuthController {

    // Declara uma variável de instância privada e final chamada authService do tipo AuthService.
    // 'private' significa que esta variável só pode ser acessada dentro desta classe.
    // 'final' significa que, uma vez inicializada no construtor, esta variável sempre
    // referenciará a mesma instância de AuthService.
    private final AuthService authService;

    // Este é o construtor da classe AuthController. Ele recebe uma instância de AuthService
    // como argumento. O Spring irá automaticamente injetar essa dependência devido à anotação
    // @Autowired implícita em construtores com um único argumento.
    public AuthController(AuthService authService) {
        // Inicializa a variável authService com a instância injetada.
        this.authService = authService;
    }

    // @PostMapping("/login") mapeia requisições HTTP do tipo POST para a URL "/auth/login".
    // Este metodo será executado quando uma requisição POST for feita para essa URL.
    @PostMapping("/login")
    // @RequestBody indica que o parâmetro LoginDTO será preenchido com os dados enviados
    // no corpo da requisição HTTP (geralmente em formato JSON). Espera-se que o corpo contenha
    // informações como email e senha para a autenticação.
    public RespostaDTO login(@RequestBody LoginDTO loginDTO) {
        // Chama o metodo autenticar do authService, passando o email e a senha extraídos
        // do LoginDTO recebido. Este metodo provavelmente irá verificar as credenciais do usuário.
        return new RespostaDTO(
                "Login realizado!", // Mensagem de sucesso para o login
                authService.autenticar(loginDTO.getEmail(), loginDTO.getSenha()) // Resultado da autenticação (pode ser um token, informações do usuário, etc.)
        );
    }

    // @PostMapping("/registro") mapeia requisições HTTP do tipo POST para a URL "/auth/registro".
    // Este metodo será executado quando uma requisição POST for feita para essa URL.
    @PostMapping("/registro")
    // @RequestBody indica que o parâmetro UsuarioDTO será preenchido com os dados enviados
    // no corpo da requisição HTTP (geralmente em formato JSON). Espera-se que o corpo contenha
    // informações necessárias para registrar um novo usuário (nome, email, senha, etc.).
    public RespostaDTO registrar(@RequestBody UsuarioDTO usuarioDTO) {
        // Chama o metodo registrar do authService, passando o UsuarioDTO recebido.
        // Este metodo provavelmente irá criar um novo usuário no sistema.
        return new RespostaDTO(
                "Usuário registrado!", // Mensagem de sucesso para o registro
                authService.registrar(usuarioDTO) // Informações do usuário registrado ou resultado da operação
        );
    }
}