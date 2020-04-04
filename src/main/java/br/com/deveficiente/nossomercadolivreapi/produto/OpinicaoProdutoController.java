package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.shared.FindById;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import br.com.deveficiente.nossomercadolivreapi.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController

public class OpinicaoProdutoController {

    @Autowired
    private ProdutoOpiniaoRepository produtoOpiniaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping(path = "/api/produtos/{produtoId}/opiniao")
    public void registraOpiniao(@PathVariable("produtoId") Long produtoId, @RequestBody @Valid ProdutoOpiniaoRequest produtoOpiniaoRequest) {

        Usuario usuarioLogado = getUsuarioLogado();

        Produto produto = FindById.executa(produtoId, produtoRepository);

        ProdutoOpiniao produtoOpiniao = produtoOpiniaoRequest.criaProdutoOpiniao(produto, usuarioLogado, usuarioRepository, produtoRepository);
        produtoOpiniaoRepository.save(produtoOpiniao);
    }

    private Usuario getUsuarioLogado() {
        String email = "usuario@email.com.br";
        return usuarioRepository.findByLogin(email).get();
    }
}
