package br.com.deveficiente.nossomercadolivreapi.produto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    public Caracteristica() {
    }

    public Caracteristica(@NotEmpty String nome, @NotEmpty String descricao, @NotNull Produto produto) {
        this.nome = nome;
        this.descricao = descricao;
        this.produto = produto;
    }

}
