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

// @Configuration é uma anotação do Spring que indica que esta classe contém
// definições de beans (objetos gerenciados pelo Spring).
@Configuration
// @EnableWebSecurity habilita a segurança web no Spring Security, fornecendo
// filtros e configurações de segurança para as requisições HTTP.
@EnableWebSecurity
public class SecurityConfig {

    // @Autowired permite que o Spring injete a dependência de UsuarioDetailsServiceImpl.
    // UsuarioDetailsServiceImpl é responsável por buscar os detalhes do usuário (email, senha, roles)
    // do banco de dados para a autenticação.
    @Autowired
    private UsuarioDetailsServiceImpl usuarioDetailsService;

    // @Autowired permite que o Spring injete a dependência de PasswordEncoder.
    // PasswordEncoder é usado para codificar e verificar as senhas dos usuários.
    @Autowired
    private PasswordEncoder passwordEncoder;

    // @Bean marca este metodo como um provedor de um bean chamado "authenticationProvider".
    // Este bean é um DaoAuthenticationProvider, que é um AuthenticationProvider que usa
    // um UserDetailsService para buscar os detalhes do usuário e um PasswordEncoder para
    // verificar a senha.
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // Cria uma nova instância de DaoAuthenticationProvider.
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Define o UserDetailsService que o authProvider irá usar para buscar os usuários.
        authProvider.setUserDetailsService(usuarioDetailsService);
        // Define o PasswordEncoder que o authProvider irá usar para verificar as senhas.
        authProvider.setPasswordEncoder(passwordEncoder);
        // Retorna a instância configurada do DaoAuthenticationProvider.
        return authProvider;
    }

    // @Bean marca este metodo como um provedor de um bean chamado "securityFilterChain".
    // SecurityFilterChain define uma cadeia de filtros de segurança que serão aplicados
    // às requisições HTTP.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configura o HttpSecurity para definir as regras de segurança.
        http
                // .csrf(csrf -> csrf.disable()) desabilita a proteção contra ataques Cross-Site Request Forgery (CSRF).
                // Em APIs REST stateless, essa proteção geralmente é desnecessária, mas para aplicações
                // que renderizam HTML com formulários, é importante habilitá-la.
                .csrf(csrf -> csrf.disable())
                // .authorizeHttpRequests(auth -> ...) define as regras de autorização para as requisições.
                .authorizeHttpRequests(auth -> auth
                        // .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        // permite acesso irrestrito a recursos estáticos (CSS, JavaScript, imagens, etc.).
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        // .requestMatchers("/admin/**").hasRole("ADMIN") exige que o usuário autenticado
                        // tenha a role "ADMIN" para acessar qualquer URL que comece com "/admin/".
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // .requestMatchers("/cliente/**").hasRole("CLIENTE") exige que o usuário autenticado
                        // tenha a role "CLIENTE" para acessar qualquer URL que comece com "/cliente/".
                        .requestMatchers("/cliente/**").hasRole("CLIENTE")
                        // .anyRequest().permitAll() permite acesso irrestrito a todas as outras URLs
                        // que não foram explicitamente configuradas acima. Em um cenário de produção,
                        // você geralmente restringiria o acesso padrão e permitiria apenas o que é necessário.
                        .anyRequest().permitAll()
                )
                // .httpBasic(httpBasic -> httpBasic.realmName("BibliotecaApp")) configura a autenticação
                // HTTP Basic. Quando um usuário tenta acessar uma URL protegida, o navegador exibirá
                // uma caixa de diálogo solicitando nome de usuário e senha. O "realmName" é um título
                // para essa caixa de diálogo. Isso é usado aqui para fins de exemplo; em aplicações
                // reais, outros métodos de autenticação (como formulários de login, JWT) são mais comuns.
                .httpBasic(httpBasic -> httpBasic.realmName("BibliotecaApp"));
        // Constrói e retorna o SecurityFilterChain configurado.
        return http.build();
    }
}