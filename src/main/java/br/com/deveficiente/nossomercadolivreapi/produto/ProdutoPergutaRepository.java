package br.com.deveficiente.nossomercadolivreapi.produto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoPergutaRepository extends CrudRepository<ProdutoPergunta, Long> {

    List<ProdutoPergunta> findAllByProduto(Produto produto);
}
