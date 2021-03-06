package br.com.deveficiente.nossomercadolivreapi.compra.nova;

import br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.Pagamento;
import br.com.deveficiente.nossomercadolivreapi.email.Email;
import br.com.deveficiente.nossomercadolivreapi.gatewaypagamento.GatewayPagamentoType;
import br.com.deveficiente.nossomercadolivreapi.produto.Produto;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GatewayPagamentoType gatewayPagamentoType;

    @Enumerated(EnumType.STRING)
    private StatusCompra statusCompra = StatusCompra.INICIADA;

    @Min(value = 1)
    private int quantidade;

    @NotNull
    @Min(value = 1)
    private BigDecimal valor;

    @Deprecated
    Compra() {
    }

    public Compra(@NotNull Usuario usuario,
                  @NotNull ProdutoComQuantidade produtoComQuantidade,
                  @NotNull GatewayPagamentoType gatewayPagamentoType) {

        Assert.notNull(usuario, "Usuário não informado");
        Assert.notNull(produtoComQuantidade, "ProdutoQuantidade não informado.");
        Assert.notNull(gatewayPagamentoType, "GatewayPagamento não informado.");

        this.usuario = usuario;
        this.produto = produtoComQuantidade.getProduto();
        this.quantidade = produtoComQuantidade.getQuantidade();
        this.gatewayPagamentoType = gatewayPagamentoType;
        this.valor = calculaValorTotalCompra();
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public GatewayPagamentoType getGatewayPagamentoType() {
        return gatewayPagamentoType;
    }

    private BigDecimal calculaValorTotalCompra() {
        return produto.getValor().multiply(new BigDecimal(quantidade));
    }

    public Email constroiNotificaoParaDonoProduto(UriComponentsBuilder uriComponentsBuilder) {

        UriComponents uriComponents = uriComponentsBuilder
                                                .cloneBuilder()
                                                .path("/api/produtos/{produtoId}/detalhes")
                                                .buildAndExpand(produto.getProdutoId());

        Usuario de = usuario;
        Usuario para = produto.getUsuario();
        String assunto = para.getLogin() + " você tem uma nova solicitação de compra!";

        String link = uriComponents.toUriString();
        String corpo = String.format("O usuário: %s, efetuou uma compra do produto: %s, quantidade: %d no valor total de: %s, \n Detalhe Produto: %s",
                                usuario.getLogin(),
                                produto.getNome(),
                                quantidade,
                                valor,
                                link);


        return new Email(de, para, assunto, corpo);
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getUrlGatewayPagamento(UriComponentsBuilder uriComponentsBuilder) {
        return gatewayPagamentoType.geraUrl(this, uriComponentsBuilder);
    }

    public void registra(Pagamento pagamento) {
        Assert.notNull(pagamento, "Pagamento não pode ser nulo");
        if (isConcluida()) {
            throw new IllegalArgumentException("Operação inválida, Compra já foi concluída em outro momento.");
        }
        if (pagamento.comSucesso()) {
            this.statusCompra = StatusCompra.CONCLUIDA;
        }
    }

    public boolean isConcluida() {
        return this.statusCompra.equals(StatusCompra.CONCLUIDA);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Usuario getVendedor() {
        return produto.getUsuario();
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", produto=" + produto +
                ", gatewayPagamento=" + gatewayPagamentoType +
                ", statusCompra=" + statusCompra +
                '}';
    }
}
