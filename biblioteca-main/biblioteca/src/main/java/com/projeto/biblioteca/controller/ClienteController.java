package com.projeto.biblioteca.controller;

import com.projeto.biblioteca.dto.*;
import com.projeto.biblioteca.service.ClienteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Listar livros disponíveis (retorna List<LivroDTO>)
    @GetMapping("/livros")
    public RespostaDTO listarLivrosDisponiveis() {
        return new RespostaDTO(
                "Catálogo de livros disponíveis",
                clienteService.listarLivrosDisponiveis()
        );
    }

    // Alugar livro (usa EmprestimoDTO)
    @PostMapping("/alugar")
    public RespostaDTO alugarLivro(@RequestBody EmprestimoDTO emprestimoDTO) {
        return new RespostaDTO(
                "Livro alugado com sucesso!",
                clienteService.alugarLivro(emprestimoDTO.getLivroId(), emprestimoDTO.getUsuarioId())
        );
    }

    // Devolver livro (usa EmprestimoDTO)
    @PostMapping("/devolver")
    public RespostaDTO devolverLivro(@RequestBody EmprestimoDTO emprestimoDTO) {
        return new RespostaDTO(
                "Livro devolvido!",
                clienteService.devolverLivro(emprestimoDTO.getLivroId(), emprestimoDTO.getUsuarioId())
        );
    }
}