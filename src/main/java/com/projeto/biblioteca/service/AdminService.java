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
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Importar
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    // Cadastrar novo livro (ADMIN)
    @Transactional
    public Livro cadastrarLivro(LivroDTO livroDTO, String emailAdminLogado) {
        Usuario adminProprietario = usuarioRepository.findByEmail(emailAdminLogado)
                .orElseThrow(() -> new UsernameNotFoundException("Administrador com email " + emailAdminLogado + " não encontrado."));

        Livro livro = new Livro();
        livro.setTitulo(livroDTO.getTitulo());
        livro.setAutor(livroDTO.getAutor());
        livro.setIsbn(livroDTO.getIsbn());
        // livro.setCopiasDisponiveis(livroDTO.getCopiasDisponiveis()); // REMOVIDO - Esta linha causava o erro
        livro.setDisponivel(livroDTO.getDisponivel() != null ? livroDTO.getDisponivel() : true); // Utiliza o 'disponivel' do DTO
        livro.setProprietario(adminProprietario); // Define o admin logado como proprietário

        return livroRepository.save(livro);
    }

    // Excluir livro
    @Transactional
    public void excluirLivro(Long livroId) { // Adicionar verificação se o admin pode excluir ou se é o proprietário
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro com ID " + livroId + " não encontrado."));

        // Aqui você pode adicionar lógica de permissão, por exemplo:
        // Um admin pode excluir qualquer livro.
        // Um usuário só pode excluir seus próprios livros (isso estaria no LivroService ou ClienteService).

        // Se houver empréstimos ativos para este livro, pode ser necessário tratá-los antes de excluir.
        // List<Emprestimo> emprestimosAtivos = emprestimoRepository.findByLivroAndDevolvidoFalse(livro);
        // if (!emprestimosAtivos.isEmpty()) {
        //    throw new IllegalStateException("Não é possível excluir o livro. Existem empréstimos ativos.");
        // }

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
            // Se o livro agora é único, apenas o marcamos como disponível
            livro.setDisponivel(true);
            // livro.setCopiasDisponiveis(livro.getCopiasDisponiveis() + 1); // REMOVIDO
            livroRepository.save(livro);
        }
    }

    // Tornar usuário Admin
    @Transactional
    public Usuario promoverParaAdmin(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + usuarioId + " não encontrado."));
        usuario.setRole(Role.ADMIN);
        return usuarioRepository.save(usuario);
    }
}