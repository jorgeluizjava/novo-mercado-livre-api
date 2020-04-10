package br.com.deveficiente.nossomercadolivreapi.gatewaypagamento;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.Compra;
import org.springframework.util.Assert;

public class PaypalGateway implements GatewayPagamento {

    @Override
    public String paga(Compra compra) {
        Assert.notNull(compra, "Compra não pode ser nula.");
        return "paypal.com/" + compra.getId();
    }
}
