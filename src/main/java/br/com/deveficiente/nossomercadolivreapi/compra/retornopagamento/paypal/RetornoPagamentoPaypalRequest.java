package br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.paypal;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.Compra;
import br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.Pagamento;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RetornoPagamentoPaypalRequest {

    /**
     * Parametro já é recebido na rota, estou usando aqui para poder efetuar uma validação mais especifica.
     */
    @NotNull
    private Long compraId;

    @NotBlank
    private String idTransacao;

    /**
     * O Paypal retorna 1 para SUCESSO e 0 para ERRO.
     */
    @NotNull
    private Boolean status;

    public void setCompraId(Long compraId) {
        this.compraId = compraId;
    }

    public void setIdTransacao(String idTransacao) {
        this.idTransacao = idTransacao;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getCompraId() {
        return compraId;
    }

    public Pagamento criaPagamento(Compra compra) {
        return new Pagamento(idTransacao, status, compra);
    }
}
