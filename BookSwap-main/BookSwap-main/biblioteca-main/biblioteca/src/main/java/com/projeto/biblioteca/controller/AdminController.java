package com.projeto.biblioteca.controller;

import com.projeto.biblioteca.dto.*;
import com.projeto.biblioteca.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

// @RestController é uma anotação do Spring que indica que esta classe é um controller REST.
// Isso significa que os métodos desta classe irão lidar com requisições web e retornar
// respostas no formato REST (geralmente JSON ou XML).
@RestController
// @RequestMapping("/admin") define o caminho base para todos os endpoints definidos
// nesta classe. Todas as URLs acessíveis através deste controller começarão com "/admin".
@RequestMapping("/admin")
public class AdminController {

    // Declara uma variável de instância privada e final chamada adminService do tipo AdminService.
    // 'private' significa que esta variável só pode ser acessada dentro desta classe.
    // 'final' significa que, uma vez inicializada no construtor, esta variável sempre
    // referenciará a mesma instância de AdminService.
    private final AdminService adminService;

    // Este é o construtor da classe AdminController. Ele recebe uma instância de AdminService
    // como argumento. O Spring irá automaticamente injetar essa dependência devido à anotação
    // @Autowired implícita em construtores com um único argumento.
    public AdminController(AdminService adminService) {
        // Inicializa a variável adminService com a instância injetada.
        this.adminService = adminService;
    }

    // @PostMapping("/livros") mapeia requisições HTTP do tipo POST para a URL "/admin/livros".
    // Este metodo será executado quando uma requisição POST for feita para essa URL.
    @PostMapping("/livros")
    // @RequestBody indica que o parâmetro LivroDTO será preenchido com os dados enviados
    // no corpo da requisição HTTP (geralmente em formato JSON).
    // @Valid garante que o objeto LivroDTO seja validado de acordo com as anotações
    // de validação definidas na classe LivroDTO (por exemplo, @NotBlank, @Size).
    public RespostaDTO cadastrarLivro(@Valid @RequestBody LivroDTO livroDTO) {
        // Bloco try-catch para tratamento de possíveis exceções que podem ocorrer
        // durante a chamada ao serviço.
        try {
            // Chama o metodo cadastrarLivro do adminService, passando o LivroDTO recebido.
            // O metodo deve retornar o resultado da operação de cadastro.
            return RespostaDTO.sucesso(
                    "Livro cadastrado!", // Mensagem de sucesso
                    adminService.cadastrarLivro(livroDTO) // Dados do livro cadastrado
            );
        } catch (Exception e) {
            // Se ocorrer alguma exceção durante o processo de cadastro, este bloco é executado.
            // Retorna um RespostaDTO indicando um erro, com a mensagem da exceção.
            return RespostaDTO.erro(e.getMessage());
        }
    }

    // @DeleteMapping("/livros/{id}") mapeia requisições HTTP do tipo DELETE para URLs
    // como "/admin/livros/123", onde "123" é o valor do parâmetro "id".
    @DeleteMapping("/livros/{id}")
    // @PathVariable Long id extrai o valor do parâmetro "id" da URL e o converte para um Long.
    public RespostaDTO excluirLivro(@PathVariable Long id) {
        // Bloco try-catch para tratamento de possíveis exceções durante a exclusão.
        try {
            // Chama o metodo excluirLivro do adminService, passando o ID do livro a ser excluído.
            adminService.excluirLivro(id);
            // Se a exclusão for bem-sucedida, retorna um RespostaDTO indicando sucesso,
            // com uma mensagem e sem dados adicionais (null).
            return RespostaDTO.sucesso("Livro excluído!", null);
        } catch (Exception e) {
            // Se ocorrer alguma exceção durante a exclusão, retorna um RespostaDTO indicando um erro,
            // com a mensagem da exceção.
            return RespostaDTO.erro(e.getMessage());
        }
    }
}