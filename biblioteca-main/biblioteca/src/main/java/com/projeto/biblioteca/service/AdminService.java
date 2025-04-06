package com.projeto.biblioteca.service;

import com.projeto.biblioteca.dto.LivroDTO;
import com.projeto.biblioteca.model.Livro;
import com.projeto.biblioteca.model.Usuario;
import com.projeto.biblioteca.repository.LivroRepository;
import com.projeto.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.projeto.biblioteca.model.Role;

@Service
public abstract class AdminService {
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Cadastrar novo livro
    public Livro cadastrarLivro(LivroDTO livro) {
        return livroRepository(livro);
    }

    // Excluir livro
    public void excluirLivro(Long livroId) {
        livroRepository.deleteById(livroId);
    }

    // Forçar devolução (Admin)
    public void forcarDevolucao(Long emprestimoId) {
        // Implementar lógica
    }

    // Tornar usuário Admin
    public Usuario promoverParaAdmin(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        usuario.setRole(Role.ADMIN);
        return usuarioRepository.save(usuario);
    }
}