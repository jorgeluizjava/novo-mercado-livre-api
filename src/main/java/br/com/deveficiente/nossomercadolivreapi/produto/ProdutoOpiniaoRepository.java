package br.com.deveficiente.nossomercadolivreapi.produto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoOpiniaoRepository extends CrudRepository<ProdutoOpiniao, Long> {
}
