package br.com.deveficiente.nossomercadolivreapi.produto;

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
    private Uploader uploader;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    public void cria(@Valid ProdutoRequest produtoRequest) {

        String email = "usuario@email.com.br";
        Usuario usuario = usuarioRepository.findByLogin(email).get();

        verificaSeUsuarioJaTemProdutoCadastrado(usuario);

        Produto produto = produtoRequest.criaProduto(usuario, usuarioRepository, uploader);
        produtoRepository.save(produto);

    }

    private void verificaSeUsuarioJaTemProdutoCadastrado(Usuario usuario) {
        long quantidade = produtoRepository.countByUsuario(usuario);
        if (quantidade > 0) {
            throw new IllegalArgumentException("Cliente já tem um produto cadastrado.");
        }
    }
}
