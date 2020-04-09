package br.com.deveficiente.nossomercadolivreapi.produto.detalhe;

import br.com.deveficiente.nossomercadolivreapi.categoria.Categoria;
import br.com.deveficiente.nossomercadolivreapi.categoria.OrdenacaoCategoria;
import br.com.deveficiente.nossomercadolivreapi.produto.Produto;
import br.com.deveficiente.nossomercadolivreapi.produto.ProdutoPergunta;
import br.com.deveficiente.nossomercadolivreapi.produto.ProdutoPergutaRepository;
import br.com.deveficiente.nossomercadolivreapi.shared.Markdown;
import br.com.deveficiente.nossomercadolivreapi.shared.Ordenacao;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ProdutoDetalheDTO {

    private Long produtoId;
    private String nome;
    private BigDecimal valor;
    private String descricaoOriginal;
    private String descricaoHtml;
    private int quantidade;
    private List<CaracteristicaProdutoDetalheDTO> caracteristicas;
    private List<FotoProdutoDetalheDTO> fotos;
    private List<PerguntaProdutoDetalheDTO> perguntasProduto;
    private Long categoriaId;
    private List<CategoriaProdutoDetalheDTO> hierarquiaCategorias = new ArrayList<>();
    private String linkVendedor;
    private OpiniaoProdutoDetalheDTO opiniao;

    public ProdutoDetalheDTO(Produto produto, ProdutoPergutaRepository produtoPergutaRepository, UriComponentsBuilder uriComponentsBuilder) {
        produtoId = produto.getProdutoId();
        nome = produto.getNome();
        valor = produto.getValor();
        descricaoOriginal = produto.getDescricao();
        descricaoHtml = Markdown.toHtml(produto.getDescricao());
        quantidade = produto.getQuantidade();
        caracteristicas = extraiCaracteristicasDTOs(produto);
        fotos = extraiFotosDTOs(produto);
        perguntasProduto = extraiPerguntasDTOs(produto);
        categoriaId = produto.getCategoria().getCategoriaId();
        hierarquiaCategorias = extraiCategorias(produto, uriComponentsBuilder);
        linkVendedor = extraiLinkVendedor(produto, uriComponentsBuilder);
        opiniao = new OpiniaoProdutoDetalheDTO(produto);
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

    public String getDescricaoOriginal() {
        return descricaoOriginal;
    }

    public String getDescricaoHtml() {
        return descricaoHtml;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public List<CaracteristicaProdutoDetalheDTO> getCaracteristicas() {
        return Collections.unmodifiableList(caracteristicas);
    }

    public List<FotoProdutoDetalheDTO> getFotos() {
        return fotos;
    }

    public List<PerguntaProdutoDetalheDTO> getPerguntasProduto() {
        return perguntasProduto;
    }

    public List<CategoriaProdutoDetalheDTO> getHierarquiaCategorias() {
        return hierarquiaCategorias;
    }

    public String getLinkVendedor() {
        return linkVendedor;
    }

    public OpiniaoProdutoDetalheDTO getOpiniao() {
        return opiniao;
    }

    private List<CaracteristicaProdutoDetalheDTO> extraiCaracteristicasDTOs(Produto produto) {
        return produto
                .getCaracteristicas()
                .stream()
                .map(CaracteristicaProdutoDetalheDTO::new)
                .collect(toList());
    }

    private List<FotoProdutoDetalheDTO> extraiFotosDTOs(Produto produto) {
        return produto
                .getFotos()
                .stream()
                .map(FotoProdutoDetalheDTO::new)
                .collect(toList());
    }

    private List<PerguntaProdutoDetalheDTO> extraiPerguntasDTOs(Produto produto) {

        Comparator<ProdutoPergunta> ordenaPorDataCriacaoDecrescente = (pergunta1, pergunta2) -> pergunta2.getCreatedAt().compareTo(pergunta1.getCreatedAt());

        return produto
                .getPerguntas(ordenaPorDataCriacaoDecrescente)
                .stream()
                .map(PerguntaProdutoDetalheDTO::new)
                .collect(toList());
    }

    private List<CategoriaProdutoDetalheDTO> extraiCategorias(Produto produto, UriComponentsBuilder uriComponentsBuilder) {

        return produto
                .getCategorias(Ordenacao.MAE_PARA_FILHA)
                .stream()
                .map(categoria -> {
                    return new CategoriaProdutoDetalheDTO(categoria, uriComponentsBuilder);
                })
                .collect(toList());
    }

    private String extraiLinkVendedor(Produto produto, UriComponentsBuilder uriComponentsBuilder) {
        return uriComponentsBuilder.cloneBuilder().path("/api/usuarios/{usuarioId}").buildAndExpand(produto.getUsuario().getUsuarioId()).toUriString();
    }
}
