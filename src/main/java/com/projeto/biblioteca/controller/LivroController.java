package com.projeto.biblioteca.controller;

import com.projeto.biblioteca.model.Livro;
import com.projeto.biblioteca.dto.LivroDTO;
// LivroResponseDTO não é mais necessário aqui se você optou pela abordagem JOIN FETCH e retorna List<Livro>
// import com.projeto.biblioteca.dto.LivroResponseDTO;
import com.projeto.biblioteca.dto.RespostaDTO; // <<< LINHA ADICIONADA/GARANTIDA
import com.projeto.biblioteca.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin(origins = "*")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        try {
            List<Livro> livros = livroService.listarTodos();
            return ResponseEntity.ok(livros);
        } catch (Exception e) {
            System.err.println("Erro ao listar todos os livros: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionarLivro(@Valid @RequestBody LivroDTO livroDTO, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(RespostaDTO.erro("Usuário não autenticado.")); // Uso de RespostaDTO
        }
        String emailUsuarioLogado = authentication.getName();

        try {
            Livro novoLivro = livroService.adicionarLivro(livroDTO, emailUsuarioLogado);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
        } catch (UsernameNotFoundException e) {
            System.err.println("DEBUG: UsernameNotFoundException no controller: " + e.getMessage());
            // Retornando RespostaDTO para erro 400 também para padronizar
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RespostaDTO.erro(e.getMessage()));
        } catch (RuntimeException e) {
            System.err.println("DEBUG: RuntimeException no controller ao adicionar livro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RespostaDTO.erro("Erro interno ao adicionar livro: " + e.getMessage()));
        }
    }

    @GetMapping("/meus")
    public ResponseEntity<?> listarMeusLivros(Authentication authentication) { // Alterado para ResponseEntity<?> para usar RespostaDTO no erro
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(RespostaDTO.erro("Usuário não autenticado."));
        }
        String emailUsuarioLogado = authentication.getName();
        try {
            List<Livro> meusLivros = livroService.listarLivrosDoUsuario(emailUsuarioLogado);
            return ResponseEntity.ok(meusLivros);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RespostaDTO.erro(e.getMessage()));
        } catch (Exception e) {
            System.err.println("Erro ao listar 'meus livros': " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RespostaDTO.erro("Erro interno ao buscar seus livros."));
        }
    }

    @GetMapping("/meus/disponiveis")
    public ResponseEntity<?> listarMeusLivrosDisponiveis(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(RespostaDTO.erro("Usuário não autenticado."));
        }
        String emailUsuarioLogado = authentication.getName();
        try {
            List<Livro> meusLivrosDisponiveis = livroService.listarLivrosDisponiveisDoUsuario(emailUsuarioLogado);
            return ResponseEntity.ok(meusLivrosDisponiveis);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RespostaDTO.erro(e.getMessage()));
        } catch (Exception e) {
            System.err.println("Erro ao listar 'meus livros disponíveis': " + e.getMessage());
            e.printStackTrace();
            // A linha 75 que você mencionou no erro provavelmente está aqui ou em um local similar
            // onde RespostaDTO.erro() foi chamado sem o import.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(RespostaDTO.erro("Erro interno ao buscar seus livros disponíveis."));
        }
    }
}