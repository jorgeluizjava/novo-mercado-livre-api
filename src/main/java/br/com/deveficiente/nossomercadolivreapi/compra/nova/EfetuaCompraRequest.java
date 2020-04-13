package br.com.deveficiente.nossomercadolivreapi.compra.nova;

import br.com.deveficiente.nossomercadolivreapi.gatewaypagamento.GatewayPagamentoType;
import br.com.deveficiente.nossomercadolivreapi.produto.Produto;
import br.com.deveficiente.nossomercadolivreapi.produto.ProdutoRepository;
import br.com.deveficiente.nossomercadolivreapi.shared.customvalidators.ValidEnumValues;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import org.springframework.util.Assert;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EfetuaCompraRequest {

    @NotNull(message = "produtoId não informado.")
    private Long produtoId;

    @Min(value = 1, message = "A quantidade deve ser maior que zero.")
    private int quantidade;

    @ValidEnumValues(enumClass = GatewayPagamentoType.class)
    private String gatewayPagamento;

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getGatewayPagamento() {
        return gatewayPagamento;
    }

    public void setGatewayPagamento(String gatewayPagamento) {
        this.gatewayPagamento = gatewayPagamento;
    }

    @Override
    public String toString() {
        return "EfetuaCompraRequest{" +
                "produtoId=" + produtoId +
                ", quantidade=" + quantidade +
                ", gatewayPagamento=" + gatewayPagamento +
                '}';
    }

    public Compra criaCompra(Usuario usuarioLogado, ProdutoRepository produtoRepository) {

        Assert.notNull(usuarioLogado, "Usuario não pode ser nulo");

        Produto produto = produtoRepository.findById(produtoId).get();
        GatewayPagamentoType gatewayPagamentoType = GatewayPagamentoType.valueOf(this.gatewayPagamento);
        return new Compra(usuarioLogado, produto, quantidade, gatewayPagamentoType);
    }
}
