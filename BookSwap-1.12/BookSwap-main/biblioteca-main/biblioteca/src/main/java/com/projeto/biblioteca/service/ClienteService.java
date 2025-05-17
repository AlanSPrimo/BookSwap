package com.projeto.biblioteca.service;

import com.projeto.biblioteca.model.Livro;
import com.projeto.biblioteca.model.Usuario;
import com.projeto.biblioteca.repository.LivroRepository;
import com.projeto.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// @Service é uma anotação do Spring que marca esta classe como um serviço.
// Serviços contêm a lógica de negócios da aplicação.
@Service
public class ClienteService {
    // @Autowired permite que o Spring injete a dependência de LivroRepository.
    // LivroRepository é usado para acessar os dados dos livros no banco de dados.
    @Autowired
    private LivroRepository livroRepository;

    // @Autowired permite que o Spring injete a dependência de UsuarioRepository.
    // UsuarioRepository é usado para acessar os dados dos usuários no banco de dados.
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Este método lista todos os livros que estão marcados como disponíveis no sistema.
    public List<Livro> listarLivrosDisponiveis() {
        // Chama o método findByDisponivelTrue do LivroRepository. Este método retorna
        // uma lista de todos os objetos Livro onde o atributo 'disponivel' é verdadeiro.
        return livroRepository.findByDisponivelTrue();
    }

    // Este método implementa a lógica para um cliente alugar um livro.
    public String alugarLivro(Long livroId, Long usuarioId) {
        // Busca o livro no banco de dados pelo ID fornecido usando o LivroRepository.
        // .orElseThrow(...) lança uma exceção IllegalArgumentException se nenhum livro
        // com o ID fornecido for encontrado.
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        // Busca o usuário no banco de dados pelo ID fornecido usando o UsuarioRepository.
        // .orElseThrow(...) lança uma exceção IllegalArgumentException se nenhum usuário
        // com o ID fornecido for encontrado.
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        // Verifica se o livro está atualmente disponível para aluguel.
        if (!livro.isDisponivel()) {
            // Se o livro não estiver disponível, lança uma exceção IllegalStateException
            // indicando que o livro não pode ser alugado.
            throw new IllegalStateException("Livro indisponível");
        }

        // Decrementa o número de cópias disponíveis do livro, pois uma cópia foi alugada.
        livro.setCopiasDisponiveis(livro.getCopiasDisponiveis() - 1);
        // Salva as alterações na entidade Livro no banco de dados para refletir a cópia alugada.
        livroRepository.save(livro);
        // Retorna uma mensagem de sucesso indicando que o aluguel foi realizado.
        return "Aluguel realizado!";
    }

    // Este método implementa a lógica para um cliente devolver um livro.
    public Object devolverLivro(Long livroId, Long usuarioId) {
        // Observação: A implementação deste método está incompleta (apenas retorna null).
        // A lógica para marcar o livro como devolvido, atualizar a contagem de cópias
        // disponíveis e possivelmente registrar a devolução precisaria ser adicionada aqui.
        return null;
    }
}