package br.com.deveficiente.nossomercadolivreapi.produto.detalhe;

import br.com.deveficiente.nossomercadolivreapi.produto.ProdutoOpiniao;
import org.springframework.util.Assert;

public class OpiniaoDTO {

    private String titulo;
    private String descricao;

    public OpiniaoDTO(ProdutoOpiniao produtoOpiniao) {
        titulo = produtoOpiniao.getTitulo();
        descricao = produtoOpiniao.getDescricao();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}
