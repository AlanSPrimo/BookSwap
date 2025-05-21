package com.projeto.biblioteca.config;

import com.projeto.biblioteca.security.UsuarioDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Certifique-se que este import está presente
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioDetailsServiceImpl usuarioDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder; // Presumindo que você tem um PasswordEncoderConfig

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para simplificar em APIs RESTful ou se não estiver usando formulários Spring para todas as submissões protegidas
                .authorizeHttpRequests(auth -> auth
                        // ✅ Recursos Estáticos e Páginas Públicas
                        .requestMatchers("/style/**", "/Image/**").permitAll()
                        .requestMatchers("/", "/landing.html", "/login.html", "/cadastro.html").permitAll()
                        .requestMatchers("/auth/login", "/auth/registro").permitAll() // Endpoints de autenticação

                        // 📖 Endpoints de Livros
                        .requestMatchers(HttpMethod.GET, "/api/livros").permitAll() // Listar todos os livros é público
                        .requestMatchers(HttpMethod.POST, "/api/livros").authenticated() // Adicionar um novo livro requer autenticação
                        .requestMatchers(HttpMethod.GET, "/api/livros/meus").authenticated() // Listar "meus livros" requer autenticação
                        .requestMatchers(HttpMethod.GET, "/api/livros/meus/disponiveis").authenticated() // Listar "meus livros disponíveis" requer autenticação
                        // Outros endpoints de livros (PUT, DELETE /api/livros/{id}) precisariam de regras (ex: authenticated() ou hasRole('ADMIN'))

                        // 🔁 Endpoints de Trocas de Livros
                        .requestMatchers("/api/trocas/**").authenticated() // Todas as operações de troca requerem autenticação

                        // 👤 Endpoints Específicos de Roles (Páginas e APIs)
                        .requestMatchers("/cliente/**").hasRole("CLIENTE") // Conteúdo específico para clientes
                        .requestMatchers("/admin/**").hasRole("ADMIN")   // Conteúdo específico para administradores

                        // 🔐 Qualquer outra requisição precisa estar autenticada
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login.html")             // Página de login customizada
                        .loginProcessingUrl("/perform_login") // URL para onde o formulário de login envia os dados
                        .defaultSuccessUrl("/cliente/home.html", true) // Redireciona para home do cliente após login bem-sucedido
                        .failureUrl("/login.html?error=true") // Página para onde redirecionar em caso de falha no login
                        .permitAll()                          // Permite acesso à página de login e ao processamento
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL para acionar o logout
                        .logoutSuccessUrl("/login.html?logout=true") // Redireciona após logout bem-sucedido
                        .invalidateHttpSession(true)          // Invalida a sessão HTTP
                        .deleteCookies("JSESSIONID")          // Deleta o cookie de sessão
                        .permitAll()                          // Permite acesso à funcionalidade de logout
                );

        return http.build();
    }
}