package br.com.deveficiente.nossomercadolivreapi;

import br.com.deveficiente.nossomercadolivreapi.categoria.Categoria;
import br.com.deveficiente.nossomercadolivreapi.categoria.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(enableDefaultTransactions = false)
public class NossoMercadoLivreApiApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public static void main(String[] args) {
		SpringApplication.run(NossoMercadoLivreApiApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		carregaCategorias();
	}


	private void carregaCategorias() {
		Categoria tecnologia = new Categoria("Tecnologia", Optional.empty());
		categoriaRepository.save(tecnologia);

		Categoria celulares = new Categoria("Celulares", Optional.of(tecnologia));
		categoriaRepository.save(celulares);

		Categoria smartphones = new Categoria("Smartphones", Optional.of(celulares));
		categoriaRepository.save(smartphones);

		Categoria android = new Categoria("Android", Optional.of(smartphones));
		categoriaRepository.save(android);
	}
}
