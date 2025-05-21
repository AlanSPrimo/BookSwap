package com.projeto.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication é uma anotação de conveniência do Spring Boot que agrupa
// outras três anotações importantes:
// - @Configuration: Marca esta classe como uma fonte de definições de beans (objetos
//   gerenciados pelo Spring).
// - @EnableAutoConfiguration: Diz ao Spring Boot para tentar configurar automaticamente
//   sua aplicação com base nas dependências que você adicionou no seu arquivo pom.xml
//   ou build.gradle.
// - @ComponentScan: Diz ao Spring para procurar por outros componentes (como @Service,
//   @Controller, @Repository) no pacote desta classe e em seus subpacotes.
@SpringBootApplication
public class BibliotecaApplication {

	// public static void main(String[] args) é o método principal que é executado
	// quando você inicia a aplicação Java.
	public static void main(String[] args) {
		// SpringApplication.run(BibliotecaApplication.class, args); é o método
		// principal do Spring Boot para iniciar a aplicação.
		// - BibliotecaApplication.class: Passa a classe principal da aplicação para
		//   que o Spring Boot possa iniciar o contexto da aplicação a partir dela.
		// - args: Passa quaisquer argumentos de linha de comando que foram fornecidos
		//   ao iniciar a aplicação.
		SpringApplication.run(BibliotecaApplication.class, args);
	}

}