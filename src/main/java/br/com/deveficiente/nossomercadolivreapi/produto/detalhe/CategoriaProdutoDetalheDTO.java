package br.com.deveficiente.nossomercadolivreapi.produto.detalhe;

import br.com.deveficiente.nossomercadolivreapi.categoria.Categoria;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class CategoriaProdutoDetalheDTO {

    private Long categoriaId;
    private String nome;
    private String link;

    public CategoriaProdutoDetalheDTO(Categoria categoria, UriComponentsBuilder uriComponentsBuilder) {
        categoriaId = categoria.getCategoriaId();
        nome = categoria.getNome();

        UriComponents uriComponents = uriComponentsBuilder
                                        .cloneBuilder()
                                        .path("/api/categorias/{categoriaId}/produtos")
                                        .buildAndExpand(categoria.getCategoriaId());

        link = uriComponents.toUriString();
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public String getNome() {
        return nome;
    }

    public String getLink() {
        return link;
    }
}
