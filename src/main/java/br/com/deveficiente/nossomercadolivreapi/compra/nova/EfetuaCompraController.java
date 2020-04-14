package br.com.deveficiente.nossomercadolivreapi.compra.nova;

import br.com.deveficiente.nossomercadolivreapi.produto.NotificaDonoDoProdutoService;
import br.com.deveficiente.nossomercadolivreapi.produto.Produto;
import br.com.deveficiente.nossomercadolivreapi.produto.ProdutoRepository;
import br.com.deveficiente.nossomercadolivreapi.shared.FindById;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import br.com.deveficiente.nossomercadolivreapi.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.OptimisticLockException;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class EfetuaCompraController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotificaDonoDoProdutoService notificaDonoDoProdutoService;

    @PostMapping(value = "/api/produtos/{produtoId}/compras")
    @Transactional
    public String efetuaCompra(@PathVariable("produtoId") Long produtoId, @RequestBody @Valid EfetuaCompraRequest efetuaCompraRequest, UriComponentsBuilder uriComponentsBuilder) {

        Usuario usuarioLogado = getUsuarioLogado();

        Produto produto = FindById.executa(produtoId, produtoRepository);
        Optional<ProdutoComQuantidade> possivelProdutoEstoque = produto.baixaQuantidadeEstoque(efetuaCompraRequest.getQuantidade());

        if (!possivelProdutoEstoque.isPresent()) {
            throw new IllegalArgumentException("quantidade do produto insuficiente, qtd: " + produto.getQuantidade());
        }

        salvaProduto(produto);

        ProdutoComQuantidade produtoComQuantidade = possivelProdutoEstoque.get();
        Compra compra = new Compra(usuarioLogado, produtoComQuantidade, efetuaCompraRequest.getGatewayPagamentoType());
        compraRepository.save(compra);

        notificaDonoDoProdutoService.executa(compra, uriComponentsBuilder);

        return compra.getUrlGatewayPagamento(uriComponentsBuilder);
    }

    private Usuario getUsuarioLogado() {
        String email = "usuario@email.com.br";
        return usuarioRepository.findByLogin(email).get();
    }

    private void salvaProduto(Produto produto) {

        try {
            produtoRepository.save(produto);
        } catch (OptimisticLockException ex) {
            throw new IllegalStateException("Não foi possível concluir a compra, o produto ficou indisponível.");
        }
    }
}
