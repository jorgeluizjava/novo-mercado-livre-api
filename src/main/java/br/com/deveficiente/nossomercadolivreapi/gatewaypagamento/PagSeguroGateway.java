package br.com.deveficiente.nossomercadolivreapi.gatewaypagamento;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.Compra;
import org.springframework.util.Assert;

public class PagSeguroGateway implements GatewayPagamento {

    @Override
    public String paga(Compra compra) {
        Assert.notNull(compra, "Compra não pode ser nula.");
        return "pagseguro.com/" + compra.getId();
    }
}
