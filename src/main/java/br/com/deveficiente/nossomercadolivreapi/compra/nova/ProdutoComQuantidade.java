package br.com.deveficiente.nossomercadolivreapi.compra.nova;

import br.com.deveficiente.nossomercadolivreapi.produto.Produto;

public class ProdutoComQuantidade {

    private Produto produto;
    private int quantidade;

    public ProdutoComQuantidade(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
