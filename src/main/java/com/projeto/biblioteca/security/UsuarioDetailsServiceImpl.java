package com.projeto.biblioteca.security;

import com.projeto.biblioteca.model.Usuario;
import com.projeto.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

// @Service é uma anotação do Spring que marca esta classe como um serviço.
// Serviços contêm a lógica de negócios da aplicação.
@Service
// public class UsuarioDetailsServiceImpl implements UserDetailsService declara uma classe
// chamada UsuarioDetailsServiceImpl que implementa a interface UserDetailsService
// do Spring Security. Esta interface é usada para buscar os detalhes do usuário
// (como nome de usuário, senha e autoridades) durante o processo de autenticação.
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    // @Autowired permite que o Spring injete a dependência de UsuarioRepository.
    // UsuarioRepository é usado para acessar os dados dos usuários no banco de dados.
    @Autowired
    private UsuarioRepository usuarioRepository;

    // @Override indica que este método está sobrescrevendo um método da interface UserDetailsService.
    // loadUserByUsername é o método principal desta interface. Ele recebe o nome de usuário
    // (neste caso, o email) e tenta carregar os detalhes do usuário correspondente.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca um usuário no banco de dados pelo email fornecido usando o UsuarioRepository.
        // .findByEmail(email) retorna um Optional<Usuario>.
        // .orElseThrow(...) lança uma exceção UsernameNotFoundException se o Optional estiver vazio
        // (ou seja, nenhum usuário com o email fornecido foi encontrado).
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        // Cria e retorna um objeto User do Spring Security, que implementa a interface UserDetails.
        // Este objeto contém as informações necessárias para a autenticação:
        // - usuario.getEmail(): O email do usuário, usado como nome de usuário.
        // - usuario.getSenha(): A senha do usuário recuperada do banco de dados.
        //   **Importante:** Espera-se que esta senha esteja criptografada no banco de dados.
        // - List.of(new SimpleGrantedAuthority(usuario.getRole().getAuthority())): Uma lista
        //   das autoridades (roles/permissões) do usuário. 'usuario.getRole().getAuthority()'
        //   retorna a representação String da role do usuário (por exemplo, "ROLE_ADMIN").
        //   SimpleGrantedAuthority é uma implementação simples da interface GrantedAuthority.
        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getSenha(), // A SENHA DEVE ESTAR CRIPTOGRAFADA NO BANCO DE DADOS
                List.of(new SimpleGrantedAuthority(usuario.getRole().getAuthority()))
        );
    }
}