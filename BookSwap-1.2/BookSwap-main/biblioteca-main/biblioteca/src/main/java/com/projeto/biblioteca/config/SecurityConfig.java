package com.projeto.biblioteca.config;

import com.projeto.biblioteca.security.UsuarioDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// @Configuration é uma anotação do Spring que indica que esta classe contém
// definições de beans (objetos gerenciados pelo Spring).
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ... (seu AuthenticationManager, DaoAuthenticationProvider, etc.) ...
    @Autowired
    private UsuarioDetailsServiceImpl usuarioDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permitir acesso irrestrito a recursos estáticos na raiz de static
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        // Permitir acesso irrestrito aos endpoints de autenticação e páginas públicas (que ficam na raiz de static)
                        .requestMatchers("/", "/login.html", "/cadastro.html", "/auth/login", "/auth/registro").permitAll()
                        // Exigir autenticação para qualquer coisa dentro de /cliente/
                        .requestMatchers("/cliente/**").hasRole("CLIENTE") // Ou .authenticated() se qualquer usuário logado puder acessar
                        // Endpoints específicos de admin
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Qualquer outra requisição deve ser autenticada
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login.html") // Sua página de login
                                .loginProcessingUrl("/perform_login") // Endpoint do Spring Security para processar o login
                                .defaultSuccessUrl("/cliente/home.html", true) // Redirecionar para a home DENTRO de /cliente/
                                .failureUrl("/login.html?error=true")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login.html?logout=true")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                );
        // .httpBasic(httpBasic -> httpBasic.realmName("BibliotecaApp")); // Pode ser removido se formLogin for o principal

        return http.build();
    }
}