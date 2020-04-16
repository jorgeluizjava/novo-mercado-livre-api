package br.com.deveficiente.nossomercadolivreapi.gatewaypagamento;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.Compra;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

public enum GatewayPagamentoType {

    PAYPAL {
        @Override
        public String geraUrl(Compra compra, UriComponentsBuilder uriComponentsBuilder) {

            Assert.notNull(compra, "Compra não pode ser nula.");
            String urlCompraRetornoPagamento = getUrlCompraRetornoPagamento(compra, "paypal", uriComponentsBuilder);
            return "paypal.com/" + compra.getId() + "?redirectUrl=" + urlCompraRetornoPagamento;
        }
    },
    PAGSEGURO {
        @Override
        public String geraUrl(Compra compra, UriComponentsBuilder uriComponentsBuilder) {

            Assert.notNull(compra, "Compra não pode ser nula.");
            String urlCompraRetornoPagamento = getUrlCompraRetornoPagamento(compra, "pagseguro", uriComponentsBuilder);
            return "pagseguro.com/" + compra.getId() + "?redirectUrl=" + urlCompraRetornoPagamento;
        }
    };

    public abstract String geraUrl(Compra compra, UriComponentsBuilder uriComponentsBuilder);

    private static String getUrlCompraRetornoPagamento(Compra compra, String complementoUrl, UriComponentsBuilder uriComponentsBuilder) {
        return uriComponentsBuilder
                        .cloneBuilder()
                        .path("/api/retorno-pagamento/{id}/" + complementoUrl)
                        .buildAndExpand(compra.getId())
                        .toUriString();
    }

}
