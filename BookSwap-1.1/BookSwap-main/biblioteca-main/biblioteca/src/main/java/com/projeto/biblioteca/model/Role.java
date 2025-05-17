package com.projeto.biblioteca.model;

// public enum Role declara um tipo enumerado chamado Role. Enums são conjuntos
// de constantes nomeadas. Neste caso, define os possíveis papéis dos usuários
// na aplicação.
public enum Role {
    // Define uma constante chamada ADMIN. Ao ser criada, associa a String "ROLE_ADMIN"
    // ao seu atributo 'authority'. O padrão "ROLE_" é uma convenção do Spring Security
    // para identificar as roles.
    ADMIN("ROLE_ADMIN"),
    // Define uma constante chamada CLIENTE. Similar ao ADMIN, associa a String
    // "ROLE_CLIENTE" ao seu atributo 'authority'.
    CLIENTE("ROLE_CLIENTE");

    // Declara um campo privado e final chamado 'authority' do tipo String.
    // 'private' significa que só pode ser acessado dentro do enum.
    // 'final' significa que seu valor é definido na criação da constante e não pode ser alterado.
    // Este campo armazenará a representação String da role, usada pelo Spring Security.
    private final String authority;

    // Este é o construtor do enum Role. Ele recebe uma String como argumento
    // e a atribui ao campo 'authority' da constante que está sendo criada.
    Role(String authority) {
        this.authority = authority;
    }

    // Declara um método público chamado 'getAuthority' que não recebe argumentos
    // e retorna uma String.
    public String getAuthority() {
        // Este método retorna o valor do campo 'authority' da constante do enum.
        // É usado para obter a representação String da role (por exemplo, "ROLE_ADMIN")
        // para integração com o sistema de segurança.
        return authority;
    }
}