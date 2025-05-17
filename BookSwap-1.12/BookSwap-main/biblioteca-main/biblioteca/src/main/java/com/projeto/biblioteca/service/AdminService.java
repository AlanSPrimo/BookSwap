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

// @Service é uma anotação do Spring que marca esta classe como um serviço.
// Serviços contêm a lógica de negócios da aplicação.
@Service
public class AdminService { // Removi a palavra 'abstract' // Observação: Conforme solicitado, apenas comentei a linha.

    // @Autowired permite que o Spring injete a dependência de LivroRepository.
    // LivroRepository é usado para acessar os dados dos livros no banco de dados.
    @Autowired
    private LivroRepository livroRepository;

    // @Autowired permite que o Spring injete a dependência de UsuarioRepository.
    // UsuarioRepository é usado para acessar os dados dos usuários no banco de dados.
    @Autowired
    private UsuarioRepository usuarioRepository;

    // @Autowired permite que o Spring injete a dependência de EmprestimoRepository.
    // EmprestimoRepository é usado para acessar os dados dos empréstimos no banco de dados.
    @Autowired
    private EmprestimoRepository emprestimoRepository;

    // Cadastrar novo livro
    public Livro cadastrarLivro(LivroDTO livroDTO) {
        // Cria uma nova instância da entidade Livro.
        Livro livro = new Livro();
        // Define o título do livro com o valor do título presente no DTO.
        livro.setTitulo(livroDTO.getTitulo());
        // Define o autor do livro com o valor do autor presente no DTO.
        livro.setAutor(livroDTO.getAutor());
        // Define o ISBN do livro com o valor do ISBN presente no DTO.
        livro.setIsbn(livroDTO.getIsbn());
        // Define o número de cópias disponíveis do livro com o valor presente no DTO.
        livro.setCopiasDisponiveis(livroDTO.getCopiasDisponiveis());
        // Salva a nova entidade Livro no banco de dados usando o LivroRepository
        // e retorna a entidade salva (que pode incluir um ID gerado).
        return livroRepository.save(livro);
    }

    // Excluir livro
    public void excluirLivro(Long livroId) {
        // Verifica se um livro com o ID fornecido existe no banco de dados.
        if (!livroRepository.existsById(livroId)) {
            // Se o livro não existir, lança uma exceção IllegalArgumentException com uma mensagem informativa.
            throw new IllegalArgumentException("Livro com ID " + livroId + " não encontrado.");
        }
        // Exclui o livro do banco de dados usando o ID fornecido através do LivroRepository.
        livroRepository.deleteById(livroId);
    }

    // Forçar devolução (Admin)
    @Transactional
    public void forcarDevolucao(Long emprestimoId) {
        // Busca um empréstimo no banco de dados pelo ID fornecido usando o EmprestimoRepository.
        // .orElseThrow(...) lança uma exceção IllegalArgumentException se nenhum empréstimo
        // com o ID fornecido for encontrado.
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo com ID " + emprestimoId + " não encontrado."));

        // Verifica se o empréstimo ainda não foi marcado como devolvido.
        if (!emprestimo.isDevolvido()) {
            // Define o status do empréstimo como devolvido (true).
            emprestimo.setDevolvido(true);
            // Define a data de devolução do empréstimo para a data atual.
            emprestimo.setDataDevolucao(java.time.LocalDate.now());
            // Salva as alterações na entidade Emprestimo no banco de dados.
            emprestimoRepository.save(emprestimo);

            // Obtém o livro associado ao empréstimo.
            Livro livro = emprestimo.getLivro();
            // Incrementa o número de cópias disponíveis do livro em 1, pois o livro foi devolvido.
            livro.setCopiasDisponiveis(livro.getCopiasDisponiveis() + 1);
            // Salva as alterações na entidade Livro no banco de dados.
            livroRepository.save(livro);
        }
    }

    // Tornar usuário Admin
    public Usuario promoverParaAdmin(Long usuarioId) {
        // Busca um usuário no banco de dados pelo ID fornecido usando o UsuarioRepository.
        // .orElseThrow(...) lança uma exceção IllegalArgumentException se nenhum usuário
        // com o ID fornecido for encontrado.
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + usuarioId + " não encontrado."));
        // Define a role do usuário para ADMIN (usando o enum Role).
        usuario.setRole(Role.ADMIN);
        // Salva as alterações na entidade Usuario no banco de dados.
        return usuarioRepository.save(usuario);
    }
}