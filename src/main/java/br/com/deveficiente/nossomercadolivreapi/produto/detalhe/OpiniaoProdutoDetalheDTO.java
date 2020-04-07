package br.com.deveficiente.nossomercadolivreapi.produto.detalhe;

import br.com.deveficiente.nossomercadolivreapi.produto.Produto;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class OpiniaoProdutoDetalheDTO {

    private BigDecimal media;

    private List<OpiniaoDTO> opinioes;


    public OpiniaoProdutoDetalheDTO(Produto produto) {

        media = produto.calculaMediaDasOpinioes();
        opinioes = extraiOpinioes(produto);
    }

    public BigDecimal getMedia() {
        return media;
    }

    public List<OpiniaoDTO> getOpinioes() {
        return opinioes;
    }

    private List<OpiniaoDTO> extraiOpinioes(Produto produto) {
        return produto.getOpinioes().stream().map(OpiniaoDTO::new).collect(toList());
    }

}
