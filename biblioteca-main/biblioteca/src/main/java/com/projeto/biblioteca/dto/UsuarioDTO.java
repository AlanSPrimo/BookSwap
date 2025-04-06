package com.projeto.biblioteca.dto;

import lombok.Data;
import com.projeto.biblioteca.model.Role;

@Data
public class UsuarioDTO {
    private String nome;
    private String email;
    private String senha;
    private Role role; // admin ou cliente
}