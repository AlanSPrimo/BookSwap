package com.projeto.biblioteca.dto;

import lombok.Data;
import com.projeto.biblioteca.model.Role;

// @Data é uma anotação do Lombok que gera automaticamente os métodos boilerplate
// para uma classe Java, como getters para todos os campos, setters para campos não-finais,
// equals, hashCode e toString. Isso reduz a quantidade de código manual necessário.
@Data
public class UsuarioDTO {
    // Declara um campo privado chamado 'nome' do tipo String.
    // Este campo provavelmente será usado para transportar o nome do usuário.
    private String nome;

    // Declara um campo privado chamado 'email' do tipo String.
    // Este campo provavelmente será usado para transportar o endereço de email do usuário,
    // que geralmente é usado como identificador único.
    private String email;

    // Declara um campo privado chamado 'senha' do tipo String.
    // Este campo será usado para transportar a senha do usuário. É importante lembrar
    // de não armazenar senhas em texto plano no banco de dados; elas devem ser criptografadas.
    private String senha;

    // Declara um campo privado chamado 'role' do tipo Role.
    // 'Role' é um enum que define os diferentes papéis dos usuários na aplicação
    // (por exemplo, ADMIN ou CLIENTE). Este campo indica o nível de acesso ou as
    // permissões do usuário.
    private Role role; // admin ou cliente
}