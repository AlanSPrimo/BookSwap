package com.projeto.biblioteca.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.Collections;

// @Data é uma anotação do Lombok que gera automaticamente os métodos boilerplate
// para uma classe Java, como getters para todos os campos, setters para campos não-finais,
// equals, hashCode e toString. Isso reduz a quantidade de código manual necessário.
@Data
// @Entity é uma anotação da JPA (Java Persistence API) que marca esta classe como
// uma entidade, o que significa que ela representa uma tabela no banco de dados.
@Entity
public class Usuario {
    // @Id é uma anotação da JPA que especifica a chave primária da entidade.
    @Id
    // @GeneratedValue é uma anotação da JPA que define a estratégia de geração
    // para o valor da chave primária. GenerationType.IDENTITY indica que a geração
    // será gerenciada pela coluna de identidade do banco de dados (auto incremento).
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Declara um campo privado chamado 'nome' do tipo String.
    // Este campo armazenará o nome do usuário.
    private String nome;

    // Declara um campo privado chamado 'email' do tipo String.
    // Este campo armazenará o endereço de email do usuário,
    // que geralmente é usado como identificador único.
    private String email;

    // Declara um campo privado chamado 'senha' do tipo String.
    // Este campo armazenará a senha do usuário. É importante lembrar
    // de que, em um sistema real, a senha deve ser armazenada de forma criptografada.
    private String senha;

    // @Enumerated(EnumType.STRING) é uma anotação da JPA que especifica como o
    // enum 'Role' deve ser persistido no banco de dados. EnumType.STRING indica
    // que o valor do enum será armazenado como uma String (por exemplo, "ADMIN", "CLIENTE").
    @Enumerated(EnumType.STRING)
    private Role role;

    // Este método implementa a interface GrantedAuthority do Spring Security.
    // GrantedAuthority representa uma permissão concedida a um principal (usuário).
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Cria uma lista contendo uma única SimpleGrantedAuthority.
        // SimpleGrantedAuthority é uma implementação simples de GrantedAuthority
        // que aceita uma String representando a autoridade (a role do usuário).
        // 'role.getAuthority()' retorna a representação String da role (por exemplo, "ROLE_ADMIN").
        // Collections.singletonList() cria uma lista imutável contendo apenas um elemento.
        return Collections.singletonList(new SimpleGrantedAuthority(role.getAuthority()));
    }
}