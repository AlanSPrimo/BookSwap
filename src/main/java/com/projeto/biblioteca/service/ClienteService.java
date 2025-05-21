package com.projeto.biblioteca.service;

import com.projeto.biblioteca.model.Livro;
import com.projeto.biblioteca.model.Usuario;
// Removido Emprestimo e EmprestimoRepository se não for mais usado diretamente aqui
// import com.projeto.biblioteca.model.Emprestimo;
// import com.projeto.biblioteca.repository.EmprestimoRepository;
import com.projeto.biblioteca.repository.LivroRepository;
import com.projeto.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate; // Se for registrar data de empréstimo/devolução
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Se a entidade Emprestimo ainda for usada para rastrear, injete o repository
    // @Autowired
    // private EmprestimoRepository emprestimoRepository;

    public List<Livro> listarLivrosDisponiveis() {
        // Lista livros que estão marcados como 'disponivel = true'
        // E que não pertencem ao próprio usuário (opcional, para não listar os próprios livros para troca)
        // String emailUsuarioLogado = ... // obter usuário logado
        // Usuario usuario = usuarioRepository.findByEmail(emailUsuarioLogado).orElse(null);
        // if (usuario != null) {
        //    return livroRepository.findByDisponivelTrueAndProprietarioNot(usuario); // precisaria criar este método no repo
        // }
        return livroRepository.findByDisponivelTrue();
    }

    @Transactional
    public String alugarLivro(Long livroId, Long usuarioIdLogado) { // usuarioId agora é quem está alugando
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        Usuario usuarioQueAluga = usuarioRepository.findById(usuarioIdLogado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário que está alugando não encontrado"));

        if (livro.getProprietario().equals(usuarioQueAluga)) {
            throw new IllegalStateException("Você não pode alugar seu próprio livro.");
        }

        if (!livro.isDisponivel()) {
            throw new IllegalStateException("Livro indisponível no momento.");
        }

        livro.setDisponivel(false); // Marca o livro como indisponível
        // livro.setCopiasDisponiveis(livro.getCopiasDisponiveis() - 1); // REMOVIDO
        livroRepository.save(livro);

        // Opcional: Registrar o empréstimo na tabela Emprestimo
        // Emprestimo emprestimo = new Emprestimo();
        // emprestimo.setLivro(livro);
        // emprestimo.setUsuario(usuarioQueAluga); // Quem pegou emprestado
        // emprestimo.setDataEmprestimo(LocalDate.now());
        // emprestimo.setDevolvido(false);
        // emprestimoRepository.save(emprestimo);

        return "Livro '" + livro.getTitulo() + "' alugado/solicitado para troca!";
    }

    @Transactional
    public String devolverLivro(Long livroId, Long usuarioIdLogado) { // usuarioId de quem está devolvendo
        // A lógica de devolução pode ser mais complexa se envolver a entidade Emprestimo.
        // Aqui, simplificaremos para apenas marcar o livro como disponível.

        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado para devolução."));

        // Usuario usuarioQueDevolve = usuarioRepository.findById(usuarioIdLogado)
        //         .orElseThrow(() -> new IllegalArgumentException("Usuário que está devolvendo não encontrado"));

        // Validações:
        // - Verificar se o livro estava realmente emprestado para este usuário (se usar a tabela Emprestimo)
        // - Verificar se o livro já não está disponível.
        if (livro.isDisponivel()) {
            // Poderia ser um erro ou apenas uma operação idempotente.
            // throw new IllegalStateException("Este livro já está marcado como disponível.");
            return "Livro '" + livro.getTitulo() + "' já constava como disponível.";
        }

        livro.setDisponivel(true); // Marca o livro como disponível novamente
        livroRepository.save(livro);

        // Opcional: Atualizar o registro na tabela Emprestimo
        // Emprestimo emprestimo = emprestimoRepository.findByLivroAndUsuarioAndDevolvidoFalse(livro, usuarioQueDevolve);
        // if (emprestimo != null) {
        //     emprestimo.setDevolvido(true);
        //     emprestimo.setDataDevolucao(LocalDate.now());
        //     emprestimoRepository.save(emprestimo);
        // } else {
        //     throw new IllegalStateException("Não foi encontrado um empréstimo ativo para este livro e usuário.");
        // }

        return "Livro '" + livro.getTitulo() + "' devolvido e marcado como disponível!";
    }
}