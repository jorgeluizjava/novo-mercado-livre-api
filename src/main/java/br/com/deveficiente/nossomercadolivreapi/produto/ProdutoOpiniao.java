package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "produto_opiniao")
public class ProdutoOpiniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produtoOpiniaoId;

    @Min(value = 1)
    @Max(value = 5)
    private int nota;

    @NotEmpty
    private String titulo;

    @NotEmpty
    @Column(length = 500)
    private String descricao;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Deprecated
    private ProdutoOpiniao() {
    }

    public ProdutoOpiniao(@Min(value = 1) @Max(value = 5) int nota, @NotEmpty String titulo, @NotEmpty String descricao, @NotNull Produto produto, @NotNull Usuario usuarioLogado) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.produto = produto;
        this.usuario = usuarioLogado;

        Assert.isTrue(nota >= 1 && nota <= 5, "A nota deve estar entre 1 e 5.");
        Assert.hasText(titulo, "Título não pode ser vazio");
        Assert.notNull(produto, "Produto não informado.");
        Assert.notNull(usuarioLogado, "Usuário não informado.");
    }
}
