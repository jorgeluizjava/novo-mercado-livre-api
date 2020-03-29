package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.categoria.Categoria;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> fotos = new ArrayList<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Caracteristica> caracteristicas = new ArrayList<>();

    @Deprecated
    public Produto() {
    }

    public Produto(@NotNull Usuario usuario,
                   @NotNull Categoria categoria,
                   @NotEmpty String nome,
                   @Min(value = 1) BigDecimal valor,
                   @Min(value = 0) int quantidade,
                   @NotEmpty @Length(max = 1000) String descricao,
                   @NotEmpty @Min(value = 1) List<String> urlsFotos,
                   @NotEmpty @Min(value = 1)List<CaracteristicaRequest> caracteristicasProduto) {

        Assert.notNull(usuario, "Usuário não informado.");
        Assert.notNull(categoria, "Categoria não informada.");

        Assert.notEmpty(urlsFotos, "Nenhuma foto foi informada para o produto: " + nome);
        Assert.notEmpty(caracteristicasProduto, "Nenhuma caracteristica foi informada para o produto: " + nome);

        Assert.isTrue(caracteristicasProduto.size() >= 3, "Pelo menos três caracteristicas devem ser informadas.");

        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.usuario = usuario;
        this.categoria = categoria;

        criaFotos(urlsFotos);
        criaCaracteristicas(caracteristicasProduto);
    }

    public Long getProdutoId() {
        return produtoId;
    }

    private void criaFotos(List<String> urlsFotos) {
        for (String url : urlsFotos) {
            Foto foto = new Foto(url, this);
            fotos.add(foto);
        }
    }

    private void criaCaracteristicas(List<CaracteristicaRequest> caracteristicasProduto) {
        for (CaracteristicaRequest caracteristicaRequest : caracteristicasProduto) {
            Caracteristica caracteristica = caracteristicaRequest.criaCaracteristica(this);
            caracteristicas.add(caracteristica);
        }
    }
}
