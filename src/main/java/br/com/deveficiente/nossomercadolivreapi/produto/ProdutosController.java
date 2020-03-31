package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.categoria.Categoria;
import br.com.deveficiente.nossomercadolivreapi.categoria.CategoriaRepository;
import br.com.deveficiente.nossomercadolivreapi.shared.FindById;
import br.com.deveficiente.nossomercadolivreapi.shared.infra.Uploader;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import br.com.deveficiente.nossomercadolivreapi.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

    @InitBinder(value = {"produtoRequest"})
    public void initBinderProduto(WebDataBinder dataBinder) {
        dataBinder.addValidators(new VerificaSeCategoriaExisteValidator(categoriaRepository));
    }

    @PostMapping
    public void cria(@Valid ProdutoRequest produtoRequest) {

        Usuario usuario = getUsuarioLogado();

        Produto produto = produtoRequest.criaProduto(usuario, categoriaRepository, uploader);

        produtoRepository.save(produto);

    }

    @PostMapping(path = "/{produtoId}/opiniao")
    public void registraOpiniao(@PathVariable("produtoId") Long produtoId, @RequestBody @Valid ProdutoOpiniaoRequest produtoOpiniaoRequest) {

        Usuario usuarioLogado = getUsuarioLogado();

        Optional<Produto> optionalProduto = FindById.executa(produtoId, produtoRepository);;
        verificaSeExisteProduto(produtoId, optionalProduto);

        Produto produto = optionalProduto.get();

        ProdutoOpiniao produtoOpiniao = produtoOpiniaoRequest.criaProdutoOpiniao(produto, usuarioLogado, usuarioRepository, produtoRepository);
        produtoOpiniaoRepository.save(produtoOpiniao);
    }

    private void verificaSeExisteProduto(@PathVariable("produtoId") Long produtoId, Optional<Produto> optionalProduto) {
        if (!optionalProduto.isPresent()) {
            throw new IllegalArgumentException("produtoId: " + produtoId + " não encontrado.");
        }
    }

    private Usuario getUsuarioLogado() {
        String email = "usuario@email.com.br";
        return usuarioRepository.findByLogin(email).get();
    }
}
