package com.projeto.biblioteca.service;

import com.projeto.biblioteca.dto.LivroDTO;
// LivroResponseDTO não é mais necessário para esta abordagem
import com.projeto.biblioteca.model.Livro;
import com.projeto.biblioteca.model.Usuario;
import com.projeto.biblioteca.repository.LivroRepository;
import com.projeto.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
// import java.util.stream.Collectors; // Não mais necessário para converter para DTO

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Livro> listarTodos() { // Retorna List<Livro>
        return livroRepository.findAllWithProprietario(); // Usa o novo método com JOIN FETCH
    }

    // Método buscarPorId pode continuar retornando a entidade Livro.
    // Se precisar do proprietário carregado, crie um método específico com JOIN FETCH por ID ou use este e acesse o proprietário dentro da transação.
    @Transactional(readOnly = true) // Para garantir que o proprietário possa ser acessado se necessário
    public Livro buscarPorId(Long id) {
        // Para garantir que o proprietário seja carregado, você pode usar uma query específica ou
        // confiar que o acesso ao proprietário (se houver) será feito dentro de um contexto transacional.
        // Exemplo de query específica (opcional, se o findById padrão + acesso posterior não funcionar devido a transações):
        // return livroRepository.findByIdWithProprietario(id).orElseThrow(() -> new RuntimeException("Livro não encontrado"));
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com o ID: " + id));
    }


    @Transactional(readOnly = true)
    public List<Livro> listarLivrosDoUsuario(String emailUsuario) { // Retorna List<Livro>
        Usuario proprietario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com email " + emailUsuario + " não encontrado."));

        return livroRepository.findByProprietarioWithProprietario(proprietario); // Usa o novo método com JOIN FETCH
    }

    @Transactional
    public Livro adicionarLivro(LivroDTO dto, String emailUsuario) {
        Usuario proprietario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário proprietário com email " + emailUsuario + " não encontrado."));

        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());
        livro.setDisponivel(dto.getDisponivel() != null ? dto.getDisponivel() : true);
        livro.setProprietario(proprietario);

        return livroRepository.save(livro);
    }

    // Métodos atualizarLivro e deletarLivro como antes
    @Transactional
    public Livro atualizarLivro(Long id, LivroDTO dto, String emailUsuario) {
        Livro livro = buscarPorId(id); // Este buscarPorId pode precisar carregar o proprietário se a verificação for feita aqui
        Usuario requisitante = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com email " + emailUsuario + " não encontrado."));

        // Acessar livro.getProprietario() aqui deve funcionar se buscarPorId for transacional e
        // a sessão ainda estiver ativa, ou se buscarPorId já fizer o fetch.
        if (livro.getProprietario() == null || !livro.getProprietario().equals(requisitante)) {
            throw new SecurityException("Usuário não autorizado a atualizar este livro ou proprietário não carregado.");
        }

        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());
        livro.setDisponivel(dto.getDisponivel() != null ? dto.getDisponivel() : livro.isDisponivel());

        return livroRepository.save(livro);
    }

    @Transactional
    public void deletarLivro(Long id, String emailUsuario) {
        Livro livro = buscarPorId(id); // Similar ao de cima
        Usuario requisitante = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com email " + emailUsuario + " não encontrado."));

        if (livro.getProprietario() == null ||!livro.getProprietario().equals(requisitante)) {
            throw new SecurityException("Usuário não autorizado a deletar este livro ou proprietário não carregado.");
        }
        livroRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<Livro> listarLivrosDisponiveisDoUsuario(String emailUsuario) { // Retorna List<Livro>
        Usuario proprietario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com email " + emailUsuario + " não encontrado para listar seus livros disponíveis."));

        return livroRepository.findByProprietarioAndDisponivelTrueWithProprietario(proprietario);
    }

}