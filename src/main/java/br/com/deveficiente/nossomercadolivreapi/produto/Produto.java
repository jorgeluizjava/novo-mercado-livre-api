package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.categoria.Categoria;
import br.com.deveficiente.nossomercadolivreapi.categoria.OrdenacaoCategoria;
import br.com.deveficiente.nossomercadolivreapi.produto.detalhe.CategoriaProdutoDetalheDTO;
import br.com.deveficiente.nossomercadolivreapi.produto.detalhe.PerguntaProdutoDetalheDTO;
import br.com.deveficiente.nossomercadolivreapi.shared.Ordenacao;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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

    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Foto> fotos = new HashSet<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Caracteristica> caracteristicas = new HashSet<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ProdutoPergunta> perguntas = new ArrayList<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProdutoOpiniao> opinioes = new ArrayList<>();

    @Deprecated
    Produto() {
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

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Set<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public Set<Foto> getFotos() {
        return fotos;
    }

    public List<ProdutoPergunta> getPerguntas(Comparator<ProdutoPergunta> comparator) {

        return perguntas
                .stream()
                .sorted(comparator)
                .collect(toList());
    }

    public List<ProdutoOpiniao> getOpinioes() {
        return Collections.unmodifiableList(opinioes);
    }

    public BigDecimal calculaMediaDasOpinioes() {
        return BigDecimal.valueOf(opinioes.stream().collect(Collectors.averagingDouble(ProdutoOpiniao::getNota)));
    }

    public List<Categoria> getCategorias(Ordenacao ordenacao) {

        List<Categoria> categorias = new ArrayList<>();

        while (categoria != null) {
            categorias.add(categoria);
            categoria = categoria.getCategoriaSuperior();
        }

        if (ordenacao.equals(Ordenacao.MAE_PARA_FILHA)) {
            Collections.reverse(categorias);
        }
        return categorias;
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
