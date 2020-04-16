package br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

public class NovoPagamentoEvent extends ApplicationEvent {

    private Pagamento pagamento;
    private UriComponentsBuilder uriComponentsBuilder;


    public NovoPagamentoEvent(Object source, Pagamento pagamento, UriComponentsBuilder uriComponentsBuilder) {
        super(source);
        this.pagamento = pagamento;
        this.uriComponentsBuilder = uriComponentsBuilder;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public UriComponentsBuilder getUriComponentsBuilder() {
        return uriComponentsBuilder.cloneBuilder();
    }
}
