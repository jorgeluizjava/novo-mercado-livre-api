package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.categoria.CategoriaRepository;
import br.com.deveficiente.nossomercadolivreapi.shared.infra.Uploader;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import br.com.deveficiente.nossomercadolivreapi.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ProdutoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private Uploader uploader;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private NotificaDonoDoProdutoService notificaDonoDoProdutoService;

    @InitBinder(value = {"produtoRequest"})
    public void initBinderProduto(WebDataBinder dataBinder) {
        dataBinder.addValidators(new VerificaSeCategoriaExisteValidator(categoriaRepository));
    }

    @PostMapping(value = "/api/produtos")
    @Transactional
    public void cria(@Valid ProdutoRequest produtoRequest) {

        Usuario usuario = getUsuarioVendedorLogado();
        Produto produto = produtoRequest.criaProduto(usuario, categoriaRepository, uploader);
        produtoRepository.save(produto);

    }

    private Usuario getUsuarioVendedorLogado() {
        String email = "usuariovendedor@email.com.br";
        return usuarioRepository.findByLogin(email).get();
    }

}
