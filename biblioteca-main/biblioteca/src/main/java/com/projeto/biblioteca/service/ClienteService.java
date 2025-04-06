package com.projeto.biblioteca.service;

import com.projeto.biblioteca.model.Livro;
import com.projeto.biblioteca.model.Usuario;
import com.projeto.biblioteca.repository.LivroRepository;
import com.projeto.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Livro> listarLivrosDisponiveis() {
        return livroRepository.findByDisponivelTrue();
    }

    public String alugarLivro(Long livroId, Long usuarioId) {
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (!livro.isDisponivel()) {
            throw new IllegalStateException("Livro indisponível");
        }

        livro.setCopiasDisponiveis(livro.getCopiasDisponiveis() - 1);
        livroRepository.save(livro);
        return "Aluguel realizado!";
    }
}