package com.projeto.biblioteca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// @Data é uma anotação do Lombok que gera automaticamente os métodos boilerplate
// para uma classe Java, como getters para todos os campos, setters para campos não-finais,
// equals, hashCode e toString. Isso reduz a quantidade de código manual necessário.
@Data
public class LoginDTO {
    // @Email é uma anotação do Bean Validation que verifica se o valor do campo 'email'
    // segue um formato de endereço de email válido. A mensagem especificada será usada
    // se a validação falhar.
    @Email(message = "Formato de email inválido.")
    private String email;

    // @NotBlank é uma anotação do Bean Validation que garante que o campo 'senha'
    // não seja nulo nem contenha apenas espaços em branco. A mensagem especificada
    // será usada se a validação falhar.
    @NotBlank(message = "A senha não pode estar em branco.")
    private String senha;
}