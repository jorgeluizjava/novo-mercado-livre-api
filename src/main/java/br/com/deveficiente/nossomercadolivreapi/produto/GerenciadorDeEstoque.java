package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.EfetuaCompraRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.OptimisticLockException;
import javax.validation.Valid;

@Service
public class GerenciadorDeEstoque {

    @Autowired
    private ProdutoRepository produtoRepository;

    public void baixaEstoque(@Valid EfetuaCompraRequest efetuaCompraRequest) {

        try {
            Produto produto = produtoRepository.findById(efetuaCompraRequest.getProdutoId()).get();
            produto.baixaQuantidadeEstoque(efetuaCompraRequest.getQuantidade());
            produtoRepository.save(produto);
        } catch (OptimisticLockException ex) {
            throw new IllegalStateException("Não foi possível concluir a compra, o produto ficou indisponível.");
        }
    }
}
