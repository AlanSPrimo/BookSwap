package com.projeto.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// @Configuration é uma anotação do Spring que indica que esta classe contém
// definições de beans (objetos gerenciados pelo Spring).
@Configuration
public class PasswordEncoderConfig {

    // @Bean é uma anotação do Spring que marca um mtodo como um provedor de um bean.
    // O nome do bean será o mesmo do nome do metodo (neste caso, "passwordEncoder").
    // O Spring irá executar este metodo e gerenciar o objeto PasswordEncoder retornado.
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Cria e retorna uma instância de BCryptPasswordEncoder.
        // BCryptPasswordEncoder é uma implementação do PasswordEncoder que utiliza o
        // algoritmo de hash BCrypt, um algoritmo forte para codificar senhas de forma segura.
        // O Spring Security usa este bean para codificar senhas antes de armazená-las
        // no banco de dados e para verificar senhas fornecidas durante a autenticação.
        return new BCryptPasswordEncoder();
    }
}