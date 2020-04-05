package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.email.Email;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Entity
@Table(name = "produto_pergunta")
public class ProdutoPergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long perguntaId;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @PastOrPresent
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Deprecated
    private ProdutoPergunta() {
    }

    public ProdutoPergunta(@NotBlank String titulo, Produto produto, Usuario usuario) {

        Assert.hasText(titulo, "Titulo não informado.");
        Assert.notNull(produto, "Produto não informado.");
        Assert.notNull(usuario, "Usuario não informado.");

        this.titulo = titulo;
        this.produto = produto;
        this.usuario = usuario;
    }

    public Email constroiNotificaoParaDonoProduto(UriComponentsBuilder uriComponentsBuilder) {

        UriComponents uriComponents = uriComponentsBuilder
                                            .path("/api/produtos/{produtoId}/detalhes")
                                            .buildAndExpand(produto.getProdutoId());

        Usuario de = usuario;
        Usuario para = getEmailVendedor();
        String assunto = para.getLogin() + " você tem uma nova pergunta!";

        String link = uriComponents.toUriString();
        String corpo = titulo + " \n" + "Detalhe produto: " + link;

        return new Email(de, para, assunto, corpo);
    }

    private Usuario getEmailVendedor() {
        return produto.getUsuario();
    }

}
