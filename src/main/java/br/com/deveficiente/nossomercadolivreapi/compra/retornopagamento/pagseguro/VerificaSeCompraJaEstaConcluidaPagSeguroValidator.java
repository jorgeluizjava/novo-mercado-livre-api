package br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.pagseguro;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.Compra;
import br.com.deveficiente.nossomercadolivreapi.compra.nova.CompraRepository;
import br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.paypal.RetornoPagamentoPaypalRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class VerificaSeCompraJaEstaConcluidaPagSeguroValidator implements Validator {

    private CompraRepository compraRepository;

    public VerificaSeCompraJaEstaConcluidaPagSeguroValidator(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RetornoPagamentoPagSeguroRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        RetornoPagamentoPagSeguroRequest retornoPagamentoPagSeguroRequest = (RetornoPagamentoPagSeguroRequest) target;

        Compra compra = compraRepository.findById(retornoPagamentoPagSeguroRequest.getCompraId()).get();
        if (compra.isConcluida()) {
            errors.rejectValue("compraId", null, "Compra já está concluída, nenhum pagamento registrado.");
        }
    }
}
