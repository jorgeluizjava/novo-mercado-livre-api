package br.com.deveficiente.nossomercadolivreapi.gatewaypagamento;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.Compra;

public interface GatewayPagamento {

    String paga(Compra compra);
}
