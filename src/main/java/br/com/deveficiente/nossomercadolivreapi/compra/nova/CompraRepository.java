package br.com.deveficiente.nossomercadolivreapi.compra.nova;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends CrudRepository<Compra, Long> {
}
