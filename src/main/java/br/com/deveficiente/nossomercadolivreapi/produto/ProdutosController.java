package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.categoria.CategoriaRepository;
import br.com.deveficiente.nossomercadolivreapi.shared.infra.Uploader;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import br.com.deveficiente.nossomercadolivreapi.usuario.UsuarioRepository;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    @InitBinder(value = {"produtoRequest"})
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(new VerificaSeCategoriaExisteValidator(categoriaRepository));
    }

    @PostMapping
    public void cria(@Valid ProdutoRequest produtoRequest) {

        String email = "usuario@email.com.br";
        Usuario usuario = usuarioRepository.findByLogin(email).get();

        Produto produto = produtoRequest.criaProduto(usuario, categoriaRepository, uploader);
        produtoRepository.save(produto);
    }

}
