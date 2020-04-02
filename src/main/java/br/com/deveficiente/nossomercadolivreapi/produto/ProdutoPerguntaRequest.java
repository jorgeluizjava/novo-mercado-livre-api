package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;

public class ProdutoPerguntaRequest {

    @NotBlank(message = "Titulo obrigatório")
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ProdutoPergunta criaProdutoPergunta(Produto produto, Usuario usuarioLogado) {

        Assert.notNull(produto, "Produto não pode ser nulo.");
        Assert.notNull(usuarioLogado, "Usuario não pode ser nulo");

        return new ProdutoPergunta(titulo, produto, usuarioLogado);
    }
}
