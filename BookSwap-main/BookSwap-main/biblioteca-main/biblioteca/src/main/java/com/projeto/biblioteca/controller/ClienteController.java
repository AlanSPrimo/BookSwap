package com.projeto.biblioteca.controller;

import com.projeto.biblioteca.dto.*;
import com.projeto.biblioteca.service.ClienteService;
import org.springframework.web.bind.annotation.*;

// @RestController é uma anotação do Spring que indica que esta classe é um controller REST.
// Isso significa que os métodos desta classe irão lidar com requisições web e retornar
// respostas no formato REST (geralmente JSON ou XML).
@RestController
// @RequestMapping("/cliente") define o caminho base para todos os endpoints definidos
// nesta classe. Todas as URLs acessíveis através deste controller começarão com "/cliente".
@RequestMapping("/cliente")
public class ClienteController {

    // Declara uma variável de instância privada e final chamada clienteService do tipo ClienteService.
    // 'private' significa que esta variável só pode ser acessada dentro desta classe.
    // 'final' significa que, uma vez inicializada no construtor, esta variável sempre
    // referenciará a mesma instância de ClienteService.
    private final ClienteService clienteService;

    // Este é o construtor da classe ClienteController. Ele recebe uma instância de ClienteService
    // como argumento. O Spring irá automaticamente injetar essa dependência devido à anotação
    // @Autowired implícita em construtores com um único argumento.
    public ClienteController(ClienteService clienteService) {
        // Inicializa a variável clienteService com a instância injetada.
        this.clienteService = clienteService;
    }

    // @GetMapping("/livros") mapeia requisições HTTP do tipo GET para a URL "/cliente/livros".
    // Este metodo será executado quando uma requisição GET for feita para essa URL.
    // Geralmente, requisições GET são usadas para recuperar informações.
    @GetMapping("/livros")
    public RespostaDTO listarLivrosDisponiveis() {
        // Chama o método listarLivrosDisponiveis do clienteService. Este método
        // provavelmente irá buscar a lista de livros disponíveis no sistema.
        return new RespostaDTO(
                "Catálogo de livros disponíveis", // Mensagem descritiva da resposta
                clienteService.listarLivrosDisponiveis() // Os dados da resposta, que provavelmente serão uma lista de LivroDTO
        );
    }

    // @PostMapping("/alugar") mapeia requisições HTTP do tipo POST para a URL "/cliente/alugar".
    // Requisições POST são geralmente usadas para criar novos recursos ou enviar dados para o servidor
    // para processamento. Neste caso, para alugar um livro.
    @PostMapping("/alugar")
    // @RequestBody indica que o parâmetro EmprestimoDTO será preenchido com os dados enviados
    // no corpo da requisição HTTP (geralmente em formato JSON). Espera-se que o corpo contenha
    // informações como o ID do livro e o ID do usuário para realizar o aluguel.
    public RespostaDTO alugarLivro(@RequestBody EmprestimoDTO emprestimoDTO) {
        // Chama o metodo alugarLivro do clienteService, passando o ID do livro e o ID do usuário
        // extraídos do EmprestimoDTO.
        return new RespostaDTO(
                "Livro alugado com sucesso!", // Mensagem de sucesso para a operação de aluguel
                clienteService.alugarLivro(emprestimoDTO.getLivroId(), emprestimoDTO.getUsuarioId()) // Resultado da operação de aluguel (pode ser informações do empréstimo, etc.)
        );
    }

    // @PostMapping("/devolver") mapeia requisições HTTP do tipo POST para a URL "/cliente/devolver".
    // Similar ao "/alugar", esta requisição POST é usada para enviar dados para o servidor
    // para processar a devolução de um livro.
    @PostMapping("/devolver")
    // @RequestBody indica que o parâmetro EmprestimoDTO será preenchido com os dados enviados
    // no corpo da requisição HTTP (geralmente em formato JSON). Espera-se que o corpo contenha
    // informações como o ID do livro e o ID do usuário para realizar a devolução.
    public RespostaDTO devolverLivro(@RequestBody EmprestimoDTO emprestimoDTO) {
        // Chama o metodo devolverLivro do clienteService, passando o ID do livro e o ID do usuário
        // extraídos do EmprestimoDTO.
        return new RespostaDTO(
                "Livro devolvido!", // Mensagem de sucesso para a operação de devolução
                clienteService.devolverLivro(emprestimoDTO.getLivroId(), emprestimoDTO.getUsuarioId()) // Resultado da operação de devolução (pode ser informações da devolução, etc.)
        );
    }
}