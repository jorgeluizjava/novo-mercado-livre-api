package br.com.deveficiente.nossomercadolivreapi.gatewaypagamento;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.Compra;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public enum GatewayPagamentoType {

    PAYPAL(new PaypalGateway()),
    PAGSEGURO(new PagSeguroGateway());

    private GatewayPagamento gatewayPagamento;

    private GatewayPagamentoType(GatewayPagamento gatewayPagamento) {
        this.gatewayPagamento = gatewayPagamento;
    }

    public String efetuaPagamento(Compra compra, UriComponentsBuilder uriComponentsBuilder) {

        Assert.notNull(compra, "Compra não pode ser nula.");

        String url = gatewayPagamento.paga(compra);

        String urlCompraRetornoPagamento = uriComponentsBuilder
                .cloneBuilder()
                .path("/api/compras/{id}/retorno-pagamento")
                .buildAndExpand(compra.getId())
                .toUriString();

        return url += "?redirectUrl=" + urlCompraRetornoPagamento;
    }
}
