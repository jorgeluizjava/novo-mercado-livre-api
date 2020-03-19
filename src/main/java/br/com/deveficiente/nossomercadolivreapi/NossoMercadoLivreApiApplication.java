package br.com.deveficiente.nossomercadolivreapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class NossoMercadoLivreApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NossoMercadoLivreApiApplication.class, args);
	}

	/**
	 * Alberto estou me familiarizando com este fluxo... sai fazendo o projeto e só depois que fui mandar pro github.
	 *
	 */
}
