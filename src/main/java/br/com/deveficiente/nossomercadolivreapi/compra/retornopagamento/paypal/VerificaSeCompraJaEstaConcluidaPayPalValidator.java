package br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.paypal;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.Compra;
import br.com.deveficiente.nossomercadolivreapi.compra.nova.CompraRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class VerificaSeCompraJaEstaConcluidaPayPalValidator implements Validator {

    private CompraRepository compraRepository;

    public VerificaSeCompraJaEstaConcluidaPayPalValidator(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RetornoPagamentoPaypalRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        RetornoPagamentoPaypalRequest retornoPagamentoPaypalRequest = (RetornoPagamentoPaypalRequest) target;

        Compra compra = compraRepository.findById(retornoPagamentoPaypalRequest.getCompraId()).get();
        if (compra.isConcluida()) {
            errors.rejectValue("compraId", null, "Compra já está concluída, nenhum pagamento registrado.");
        }
    }
}
