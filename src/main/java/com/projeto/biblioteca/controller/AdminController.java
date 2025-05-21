package com.projeto.biblioteca.controller;

import com.projeto.biblioteca.dto.*;
import com.projeto.biblioteca.model.Livro; // Importar Livro
import com.projeto.biblioteca.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus; // Importar HttpStatus
import org.springframework.http.ResponseEntity; // Importar ResponseEntity
import org.springframework.security.core.Authentication; // Importar Authentication
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/livros")
    public ResponseEntity<RespostaDTO> cadastrarLivro(@Valid @RequestBody LivroDTO livroDTO, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(RespostaDTO.erro("Admin não autenticado."));
        }
        String emailAdminLogado = authentication.getName();

        try {
            Livro livroCadastrado = adminService.cadastrarLivro(livroDTO, emailAdminLogado);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(RespostaDTO.sucesso("Livro cadastrado pelo admin!", livroCadastrado));
        } catch (Exception e) {
            // Logar a exceção e retornar uma mensagem de erro mais genérica ou específica
            System.err.println("Erro ao admin cadastrar livro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RespostaDTO.erro(e.getMessage()));
        }
    }

    @DeleteMapping("/livros/{id}")
    public ResponseEntity<RespostaDTO> excluirLivro(@PathVariable Long id, Authentication authentication) {
        // Adicionar verificação se o admin está autenticado
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(RespostaDTO.erro("Admin não autenticado."));
        }
        // String emailAdminLogado = authentication.getName(); // Pode ser usado para logging ou regras de negócio

        try {
            adminService.excluirLivro(id); // A lógica de quem pode excluir está no AdminService
            return ResponseEntity.ok(RespostaDTO.sucesso("Livro excluído pelo admin!", null));
        } catch (Exception e) {
            System.err.println("Erro ao admin excluir livro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RespostaDTO.erro(e.getMessage()));
        }
    }
}