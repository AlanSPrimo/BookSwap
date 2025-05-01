package com.projeto.biblioteca.service;

import com.projeto.biblioteca.dto.LoginDTO;
import com.projeto.biblioteca.dto.UsuarioDTO;
import com.projeto.biblioteca.model.Usuario;
import com.projeto.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registra um usuário a partir de UsuarioDTO
    public Usuario registrar(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuario.setRole(usuarioDTO.getRole());
        return usuarioRepository.save(usuario);
    }

    // Autentica um usuário a partir de LoginDTO
    public String autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
        if (passwordEncoder.matches(senha, usuario.getSenha())) {
            return "Token-JWT-AQUI"; // Em produção, gere um JWT
        }
        throw new RuntimeException("Credenciais inválidas!");
    }
}