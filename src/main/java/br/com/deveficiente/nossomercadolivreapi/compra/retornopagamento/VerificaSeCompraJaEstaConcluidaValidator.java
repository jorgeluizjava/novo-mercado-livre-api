package br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.Compra;
import br.com.deveficiente.nossomercadolivreapi.compra.nova.CompraRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class VerificaSeCompraJaEstaConcluidaValidator implements Validator {

    private CompraRepository compraRepository;

    public VerificaSeCompraJaEstaConcluidaValidator(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RetornoPagamentoCompraRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        RetornoPagamentoCompraRequest retornoPagamentoRequest = (RetornoPagamentoCompraRequest) target;

        Compra compra = compraRepository.findById(retornoPagamentoRequest.getCompraId()).get();
        if (compra.isConcluida()) {
            errors.rejectValue("compraId", null, "Compra já está concluída, nenhum pagamento registrado.");
        }
    }
}
