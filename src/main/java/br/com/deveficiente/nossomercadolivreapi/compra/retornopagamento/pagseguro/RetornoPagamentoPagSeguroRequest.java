package br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.pagseguro;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.Compra;
import br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.Pagamento;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RetornoPagamentoPagSeguroRequest {

    /**
     * Parametro já é recebido na rota, estou usando aqui para poder efetuar uma validação mais especifica.
     */
    @NotNull
    private Long compraId;

    @NotBlank
    private String idTransacao;

    @NotNull
    private PagSeguroStatus status;

    public void setCompraId(Long compraId) {
        this.compraId = compraId;
    }

    public void setIdTransacao(String idTransacao) {
        this.idTransacao = idTransacao;
    }

    public void setStatus(PagSeguroStatus status) {
        this.status = status;
    }

    public Long getCompraId() {
        return compraId;
    }

    public Pagamento criaPagamento(Compra compra) {
        Assert.notNull(compra, "Compra não pode ser nula.");
        return new Pagamento(idTransacao, status.equals(PagSeguroStatus.SUCESSO) , compra);
    }
}
