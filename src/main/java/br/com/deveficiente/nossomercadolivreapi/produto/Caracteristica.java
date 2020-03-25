package br.com.deveficiente.nossomercadolivreapi.produto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @Deprecated
    public Caracteristica() {
    }

    public Caracteristica(@NotEmpty String nome, @NotEmpty String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
