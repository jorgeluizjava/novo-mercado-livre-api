package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.categoria.CategoriaRepository;
import br.com.deveficiente.nossomercadolivreapi.shared.FindById;
import br.com.deveficiente.nossomercadolivreapi.shared.infra.Uploader;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import br.com.deveficiente.nossomercadolivreapi.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
public class PerguntaProdutoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoPergutaRepository produtoPerguntaRepository;

    @Autowired
    private NotificaDonoDoProdutoService notificaDonoDoProdutoService;

    @PostMapping(path = "/api/produtos/{produtoId}/pergunta")
    public void efetuaPerguntaPara(@PathVariable("produtoId") Long produtoId,
                                   @RequestBody @Valid ProdutoPerguntaRequest produtoPerguntaRequest,
                                   UriComponentsBuilder uriComponentsBuilder) {

        Usuario usuarioLogado = getUsuarioLogado();

        Produto produto = FindById.executa(produtoId, produtoRepository);
        ProdutoPergunta produtoPergunta = produtoPerguntaRequest.criaProdutoPergunta(produto, usuarioLogado);
        produtoPerguntaRepository.save(produtoPergunta);

        notificaDonoDoProdutoService.executa(produtoPergunta, uriComponentsBuilder);
    }

    private Usuario getUsuarioLogado() {
        String email = "usuario@email.com.br";
        return usuarioRepository.findByLogin(email).get();
    }

}
