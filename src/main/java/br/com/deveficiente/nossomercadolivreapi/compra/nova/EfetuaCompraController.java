package br.com.deveficiente.nossomercadolivreapi.compra.nova;

import br.com.deveficiente.nossomercadolivreapi.gatewaypagamento.GatewayPagamentoType;
import br.com.deveficiente.nossomercadolivreapi.produto.NotificaDonoDoProdutoService;
import br.com.deveficiente.nossomercadolivreapi.produto.Produto;
import br.com.deveficiente.nossomercadolivreapi.produto.ProdutoRepository;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import br.com.deveficiente.nossomercadolivreapi.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
public class EfetuaCompraController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private NotificaDonoDoProdutoService notificaDonoDoProdutoService;

    @InitBinder(value = {"efetuaCompraRequest"})
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(new VerificaSeProdutoExisteValidator(produtoRepository));
        dataBinder.addValidators(new VerificaQuantidadeDisponivelProduto(produtoRepository));
    }

    @PostMapping(value = "/api/compras")
    @Transactional
    public String efetuaCompra(@RequestBody @Valid EfetuaCompraRequest efetuaCompraRequest, UriComponentsBuilder uriComponentsBuilder) {

        Usuario usuarioLogado = getUsuarioLogado();

        Compra compra = efetuaCompraRequest.criaCompra(usuarioLogado, produtoRepository);
        compraRepository.save(compra);

        Produto produto = compra.getProduto();
        produto.baixaQuantidadeEstoque(compra.getQuantidade());
        produtoRepository.save(produto);

        notificaDonoDoProdutoService.executa(compra, uriComponentsBuilder);

        GatewayPagamentoType gatewayPagamento = compra.getGatewayPagamentoType();
        String urlRetorno = gatewayPagamento.efetuaPagamento(compra, uriComponentsBuilder);

        return urlRetorno;
    }

    private Usuario getUsuarioLogado() {
        String email = "usuario@email.com.br";
        return usuarioRepository.findByLogin(email).get();
    }
}
