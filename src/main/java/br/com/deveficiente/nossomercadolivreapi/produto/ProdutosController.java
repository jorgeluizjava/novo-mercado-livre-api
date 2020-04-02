package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.categoria.Categoria;
import br.com.deveficiente.nossomercadolivreapi.categoria.CategoriaRepository;
import br.com.deveficiente.nossomercadolivreapi.shared.FindById;
import br.com.deveficiente.nossomercadolivreapi.shared.infra.Uploader;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import br.com.deveficiente.nossomercadolivreapi.usuario.UsuarioRepository;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private Uploader uploader;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoOpiniaoRepository produtoOpiniaoRepository;

    @Autowired
    private ProdutoPergutaRepository produtoPerguntaRepository;

    @Autowired
    private ProdutoPerguntaService produtoPerguntaService;

    @InitBinder(value = {"produtoRequest"})
    public void initBinderProduto(WebDataBinder dataBinder) {
        dataBinder.addValidators(new VerificaSeCategoriaExisteValidator(categoriaRepository));
    }

    @PostMapping
    public void cria(@Valid ProdutoRequest produtoRequest) {

        Usuario usuario = getUsuarioVendedorLogado();

        Produto produto = produtoRequest.criaProduto(usuario, categoriaRepository, uploader);

        produtoRepository.save(produto);

    }

    @PostMapping(path = "/{produtoId}/opiniao")
    public void registraOpiniao(@PathVariable("produtoId") Long produtoId, @RequestBody @Valid ProdutoOpiniaoRequest produtoOpiniaoRequest) {

        Usuario usuarioLogado = getUsuarioLogado();

        Produto produto = FindById.executa(produtoId, produtoRepository);

        ProdutoOpiniao produtoOpiniao = produtoOpiniaoRequest.criaProdutoOpiniao(produto, usuarioLogado, usuarioRepository, produtoRepository);
        produtoOpiniaoRepository.save(produtoOpiniao);
    }

    @PostMapping(path = "/{produtoId}/pergunta")
    public void efetuaPerguntaPara(@PathVariable("produtoId") Long produtoId,
                                   @RequestBody @Valid ProdutoPerguntaRequest produtoPerguntaRequest,
                                   UriComponentsBuilder uriComponentsBuilder) {

        Usuario usuarioLogado = getUsuarioLogado();

        Produto produto = FindById.executa(produtoId, produtoRepository);
        ProdutoPergunta produtoPergunta = produtoPerguntaRequest.criaProdutoPergunta(produto, usuarioLogado);
        produtoPerguntaRepository.save(produtoPergunta);

        produtoPerguntaService.notificaVendedor(produtoPergunta, uriComponentsBuilder);
    }

    private Usuario getUsuarioLogado() {
        String email = "usuario@email.com.br";
        return usuarioRepository.findByLogin(email).get();
    }

    private Usuario getUsuarioVendedorLogado() {
        String email = "usuariovendedor@email.com.br";
        return usuarioRepository.findByLogin(email).get();
    }

}
