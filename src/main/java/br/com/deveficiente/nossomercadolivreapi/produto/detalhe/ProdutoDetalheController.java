package br.com.deveficiente.nossomercadolivreapi.produto.detalhe;

import br.com.deveficiente.nossomercadolivreapi.produto.Produto;
import br.com.deveficiente.nossomercadolivreapi.produto.ProdutoPergutaRepository;
import br.com.deveficiente.nossomercadolivreapi.produto.ProdutoRepository;
import br.com.deveficiente.nossomercadolivreapi.shared.FindById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class ProdutoDetalheController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoPergutaRepository produtoPergutaRepository;

    @GetMapping(value = {"/api/produtos/{produtoId}"})
    public ProdutoDetalheDTO getDetalhe(@PathVariable("produtoId") Long produtoId,
                                        UriComponentsBuilder uriComponentsBuilder) {
        Produto produto = FindById.executa(produtoId, produtoRepository);
        return new ProdutoDetalheDTO(produto, produtoPergutaRepository, uriComponentsBuilder);
    }
}
