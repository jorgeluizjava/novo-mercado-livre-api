package br.com.deveficiente.nossomercadolivreapi.produto.detalhe;

import br.com.deveficiente.nossomercadolivreapi.produto.Foto;

public class FotoProdutoDetalheDTO {

    private String url;

    public FotoProdutoDetalheDTO(Foto foto) {
        this.url = foto.getUrl();
    }

    public String getUrl() {
        return url;
    }
}
