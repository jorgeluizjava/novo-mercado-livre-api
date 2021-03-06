package br.com.deveficiente.nossomercadolivreapi.produto;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "produto_caracteristica")
public class Caracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long caracteristicaId;

    @NotEmpty
    private String nome;

    @NotEmpty
    private String descricao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Deprecated
    private Caracteristica() {
    }

    public Caracteristica(@NotEmpty String nome, @NotEmpty String descricao, @NotNull Produto produto) {

        Assert.hasText(nome, "Nome da caracteristica não foi informado.");
        Assert.hasText(descricao, "Descrição da caracteristica não foi informado");
        Assert.notNull(produto, "Produto não foi informado.");

        this.nome = nome;
        this.descricao = descricao;
        this.produto = produto;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Caracteristica that = (Caracteristica) o;
        return nome.equals(that.nome) &&
                descricao.equals(that.descricao) &&
                produto.equals(that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, descricao, produto);
    }
}
