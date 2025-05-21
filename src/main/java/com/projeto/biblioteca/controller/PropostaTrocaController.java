package com.projeto.biblioteca.controller;

import com.projeto.biblioteca.dto.PropostaTrocaRequestDTO;
import com.projeto.biblioteca.dto.PropostaTrocaResponseDTO;
import com.projeto.biblioteca.dto.RespostaDTO; // Usar RespostaDTO para padronizar respostas
import com.projeto.biblioteca.service.PropostaTrocaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trocas")
@CrossOrigin(origins = "*") // Adicionar CrossOrigin se necessário ou usar config global
public class PropostaTrocaController {

    @Autowired
    private PropostaTrocaService propostaTrocaService;

    private String getEmailUsuarioLogado(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Usuário não autenticado.");
        }
        return authentication.getName();
    }

    @PostMapping
    public ResponseEntity<RespostaDTO> criarProposta(@Valid @RequestBody PropostaTrocaRequestDTO requestDTO, Authentication authentication) {
        try {
            String emailPropositor = getEmailUsuarioLogado(authentication);
            PropostaTrocaResponseDTO proposta = propostaTrocaService.criarProposta(requestDTO, emailPropositor);
            return ResponseEntity.status(HttpStatus.CREATED).body(RespostaDTO.sucesso("Proposta de troca criada com sucesso!", proposta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(RespostaDTO.erro(e.getMessage()));
        }
    }

    @PostMapping("/{id}/aceitar")
    public ResponseEntity<RespostaDTO> aceitarProposta(@PathVariable Long id, Authentication authentication) {
        try {
            String emailReceptor = getEmailUsuarioLogado(authentication);
            PropostaTrocaResponseDTO proposta = propostaTrocaService.aceitarProposta(id, emailReceptor);
            return ResponseEntity.ok(RespostaDTO.sucesso("Proposta aceita!", proposta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(RespostaDTO.erro(e.getMessage()));
        }
    }

    @PostMapping("/{id}/rejeitar")
    public ResponseEntity<RespostaDTO> rejeitarProposta(@PathVariable Long id, Authentication authentication) {
        try {
            String emailReceptor = getEmailUsuarioLogado(authentication);
            PropostaTrocaResponseDTO proposta = propostaTrocaService.rejeitarProposta(id, emailReceptor);
            return ResponseEntity.ok(RespostaDTO.sucesso("Proposta rejeitada.", proposta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(RespostaDTO.erro(e.getMessage()));
        }
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<RespostaDTO> cancelarProposta(@PathVariable Long id, Authentication authentication) {
        try {
            String emailPropositor = getEmailUsuarioLogado(authentication);
            PropostaTrocaResponseDTO proposta = propostaTrocaService.cancelarProposta(id, emailPropositor);
            return ResponseEntity.ok(RespostaDTO.sucesso("Proposta cancelada.", proposta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(RespostaDTO.erro(e.getMessage()));
        }
    }

    @PostMapping("/{id}/concluir")
    public ResponseEntity<RespostaDTO> concluirTroca(@PathVariable Long id, Authentication authentication) {
        try {
            String emailUsuarioAcao = getEmailUsuarioLogado(authentication);
            PropostaTrocaResponseDTO proposta = propostaTrocaService.concluirTroca(id, emailUsuarioAcao);
            return ResponseEntity.ok(RespostaDTO.sucesso("Troca concluída com sucesso!", proposta));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(RespostaDTO.erro(e.getMessage()));
        }
    }

    @GetMapping("/recebidas")
    public ResponseEntity<?> getPropostasRecebidas(Authentication authentication) {
        try {
            String emailReceptor = getEmailUsuarioLogado(authentication);
            List<PropostaTrocaResponseDTO> propostas = propostaTrocaService.getPropostasRecebidas(emailReceptor);
            return ResponseEntity.ok(propostas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(RespostaDTO.erro(e.getMessage()));
        }
    }

    @GetMapping("/enviadas")
    public ResponseEntity<?> getPropostasEnviadas(Authentication authentication) {
        try {
            String emailPropositor = getEmailUsuarioLogado(authentication);
            List<PropostaTrocaResponseDTO> propostas = propostaTrocaService.getPropostasEnviadas(emailPropositor);
            return ResponseEntity.ok(propostas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(RespostaDTO.erro(e.getMessage()));
        }
    }

    @GetMapping("/historico")
    public ResponseEntity<?> getHistoricoTrocas(Authentication authentication) {
        try {
            String emailUsuario = getEmailUsuarioLogado(authentication);
            List<PropostaTrocaResponseDTO> historico = propostaTrocaService.getHistoricoTrocas(emailUsuario);
            return ResponseEntity.ok(historico);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(RespostaDTO.erro(e.getMessage()));
        }
    }
}