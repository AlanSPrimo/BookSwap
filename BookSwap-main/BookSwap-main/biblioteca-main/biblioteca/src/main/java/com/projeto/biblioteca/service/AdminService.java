package com.projeto.biblioteca.service;

import com.projeto.biblioteca.dto.LivroDTO;
import com.projeto.biblioteca.model.Emprestimo;
import com.projeto.biblioteca.model.Livro;
import com.projeto.biblioteca.model.Usuario;
import com.projeto.biblioteca.model.Role;
import com.projeto.biblioteca.repository.EmprestimoRepository;
import com.projeto.biblioteca.repository.LivroRepository;
import com.projeto.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService { // Removi a palavra 'abstract'

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    // Cadastrar novo livro
    public Livro cadastrarLivro(LivroDTO livroDTO) {
        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAutor(livroDTO.getAutor());
        livro.setIsbn(livroDTO.getIsbn());
        livro.setCopiasDisponiveis(livroDTO.getCopiasDisponiveis());
        return livroRepository.save(livro);
    }

    // Excluir livro
    public void excluirLivro(Long livroId) {
        if (!livroRepository.existsById(livroId)) {
            throw new IllegalArgumentException("Livro com ID " + livroId + " não encontrado.");
        }
        livroRepository.deleteById(livroId);
    }

    // Forçar devolução (Admin)
    @Transactional
    public void forcarDevolucao(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo com ID " + emprestimoId + " não encontrado."));

        if (!emprestimo.isDevolvido()) {
            emprestimo.setDevolvido(true);
            emprestimo.setDataDevolucao(java.time.LocalDate.now());
            emprestimoRepository.save(emprestimo);

            Livro livro = emprestimo.getLivro();
            livro.setCopiasDisponiveis(livro.getCopiasDisponiveis() + 1);
            livroRepository.save(livro);
        }
    }

    // Tornar usuário Admin
    public Usuario promoverParaAdmin(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + usuarioId + " não encontrado."));
        usuario.setRole(Role.ADMIN);
        return usuarioRepository.save(usuario);
    }
}