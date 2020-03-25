package br.com.deveficiente.nossomercadolivreapi.produto;

import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produtoId;

    @NotEmpty
    private String nome;

    @Min(value = 1)
    private BigDecimal valor;

    @Min(value = 0)
    private int quantidade;

    @NotEmpty
    @Length(max = 1000)
    @Column(length = 1000)
    private String descricao;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> fotos = new ArrayList<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Caracteristica> caracteristicas = new ArrayList<>();

    @Deprecated
    public Produto() {
    }

    public Produto(@NotEmpty String nome,
                   @Min(value = 1) BigDecimal valor,
                   @Min(value = 0) int quantidade,
                   @NotEmpty @Length(max = 1000) String descricao,
                   @NotEmpty @Min(value = 1) List<Foto> fotosDoProduto,
                   @NotEmpty @Min(value = 1) List<Caracteristica> caracteristicasProduto) {

        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;

        Assert.notEmpty(fotosDoProduto, "Nenhuma foto foi informada para o produto: " + nome);
        Assert.notEmpty(caracteristicasProduto, "Nenhuma caracteristica foi informada para o produto: " + nome);

        Assert.isTrue(caracteristicasProduto.size() >= 3, "Pelo menos três caracteristicas devem ser informadas.");

        for (Foto foto : fotosDoProduto) {
            foto.setProduto(this);
            fotos.add(foto);
        }

        for (Caracteristica caracteristica: caracteristicasProduto) {
            caracteristica.setProduto(this);
            caracteristicas.add(caracteristica);
        }
    }
}
