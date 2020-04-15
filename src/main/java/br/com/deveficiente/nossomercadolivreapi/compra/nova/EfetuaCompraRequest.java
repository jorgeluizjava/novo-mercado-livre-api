package br.com.deveficiente.nossomercadolivreapi.compra.nova;

import br.com.deveficiente.nossomercadolivreapi.gatewaypagamento.GatewayPagamentoType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EfetuaCompraRequest {

    @Min(value = 1, message = "A quantidade deve ser maior que zero.")
    private int quantidade;

    @NotNull
    private GatewayPagamentoType gatewayPagamentoType;

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public GatewayPagamentoType getGatewayPagamentoType() {
        return gatewayPagamentoType;
    }

    public void setGatewayPagamentoType(GatewayPagamentoType gatewayPagamentoType) {
        this.gatewayPagamentoType = gatewayPagamentoType;
    }

    @Override
    public String toString() {
        return "EfetuaCompraRequest{" +
                "quantidade=" + quantidade +
                ", gatewayPagamentoType=" + gatewayPagamentoType +
                '}';
    }
}
