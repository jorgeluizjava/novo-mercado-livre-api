package br.com.deveficiente.nossomercadolivreapi.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.OptimisticLockException;

@Service
public class GerenciadorDeEstoque {

    @Autowired
    private ProdutoRepository produtoRepository;

    public void baixaEstoque(Produto produto, int quantidadeSolicitada) {

        Assert.notNull(produto, "Produto não pode ser nulo.");

        try {
            produto.baixaQuantidadeEstoque(quantidadeSolicitada);
            produtoRepository.save(produto);
        } catch (OptimisticLockException ex) {
            throw new IllegalStateException("Não foi possível concluir a compra, o produto ficou indisponível.");
        }

    }
}
