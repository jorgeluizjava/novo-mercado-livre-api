package br.com.deveficiente.nossomercadolivreapi.produto;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long> {

}
