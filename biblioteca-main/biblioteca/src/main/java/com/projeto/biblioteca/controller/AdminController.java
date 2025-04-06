package com.projeto.biblioteca.controller;

import com.projeto.biblioteca.dto.*;
import com.projeto.biblioteca.service.AdminService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/livros")
    public RespostaDTO cadastrarLivro(@RequestBody LivroDTO livroDTO) {
        try {
            return RespostaDTO.sucesso(
                    "Livro cadastrado!",
                    adminService.cadastrarLivro(livroDTO)
            );
        } catch (Exception e) {
            return RespostaDTO.erro(e.getMessage());
        }
    }

    @DeleteMapping("/livros/{id}")
    public RespostaDTO excluirLivro(@PathVariable Long id) {
        try {
            adminService.excluirLivro(id);
            return RespostaDTO.sucesso("Livro excluído!", null);
        } catch (Exception e) {
            return RespostaDTO.erro(e.getMessage());
        }
    }
}