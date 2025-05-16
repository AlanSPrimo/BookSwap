package com.projeto.biblioteca.service;

import com.projeto.biblioteca.dto.LoginDTO;
import com.projeto.biblioteca.dto.UsuarioDTO;
import com.projeto.biblioteca.model.Usuario;
import com.projeto.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// @Service é uma anotação do Spring que marca esta classe como um serviço.
// Serviços contêm a lógica de negócios da aplicação.
@Service
public class AuthService {
    // @Autowired permite que o Spring injete a dependência de UsuarioRepository.
    // UsuarioRepository é usado para acessar os dados dos usuários no banco de dados.
    @Autowired
    private UsuarioRepository usuarioRepository;

    // @Autowired permite que o Spring injete a dependência de PasswordEncoder.
    // PasswordEncoder é usado para codificar e verificar as senhas dos usuários.
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registra um usuário a partir de UsuarioDTO
    public Usuario registrar(UsuarioDTO usuarioDTO) {
        // Cria uma nova instância da entidade Usuario.
        Usuario usuario = new Usuario();
        // Define o nome do usuário com o valor do nome presente no DTO.
        usuario.setNome(usuarioDTO.getNome());
        // Define o email do usuário com o valor do email presente no DTO.
        usuario.setEmail(usuarioDTO.getEmail());
        // Codifica a senha fornecida no DTO usando o PasswordEncoder antes de salvar.
        // É crucial armazenar senhas de forma segura (criptografada).
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        // Define a role do usuário com o valor da role presente no DTO.
        usuario.setRole(usuarioDTO.getRole());
        // Salva a nova entidade Usuario no banco de dados usando o UsuarioRepository
        // e retorna a entidade salva (que pode incluir um ID gerado).
        return usuarioRepository.save(usuario);
    }

    // Autentica um usuário a partir de LoginDTO
    public String autenticar(String email, String senha) {
        // Busca um usuário no banco de dados pelo email fornecido usando o UsuarioRepository.
        // .orElseThrow(...) lança uma exceção se nenhum usuário com o email fornecido for encontrado.
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
        // Verifica se a senha fornecida (em texto plano) corresponde à senha criptografada
        // armazenada no banco de dados usando o PasswordEncoder.
        if (passwordEncoder.matches(senha, usuario.getSenha())) {
            // Se as senhas corresponderem, indica que a autenticação foi bem-sucedida.
            // Em uma aplicação real, em vez de retornar uma String fixa, você geraria
            // e retornaria um Token JWT (JSON Web Token) para autenticação subsequente.
            return "Token-JWT-AQUI"; // Em produção, gere um JWT
        }
        // Se as senhas não corresponderem, lança uma exceção RuntimeException indicando
        // que as credenciais fornecidas são inválidas.
        throw new RuntimeException("Credenciais inválidas!");
    }
}