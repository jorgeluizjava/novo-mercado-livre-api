package br.com.deveficiente.nossomercadolivreapi.compra.nova;

import br.com.deveficiente.nossomercadolivreapi.produto.GerenciadorDeEstoque;
import br.com.deveficiente.nossomercadolivreapi.produto.NotificaDonoDoProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class EfetuaCompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private NotificaDonoDoProdutoService notificaDonoDoProdutoService;

    @Autowired
    private GerenciadorDeEstoque gerenciadorDeEstoque;

    public Compra executa(Compra compra, UriComponentsBuilder uriComponentsBuilder) {

        Assert.notNull(compra, "Compra não pode ser nula.");

        compraRepository.save(compra);

        gerenciadorDeEstoque.baixaEstoque(compra.getProduto(), compra.getQuantidade());
        notificaDonoDoProdutoService.executa(compra, uriComponentsBuilder);

        return compra;
    }
}
