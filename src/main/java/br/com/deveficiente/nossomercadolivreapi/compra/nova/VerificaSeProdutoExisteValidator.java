package br.com.deveficiente.nossomercadolivreapi.compra.nova;

import br.com.deveficiente.nossomercadolivreapi.produto.ProdutoRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class VerificaSeProdutoExisteValidator implements Validator {

    private ProdutoRepository produtoRepository;

    public VerificaSeProdutoExisteValidator(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return EfetuaCompraRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object value, Errors errors) {

        EfetuaCompraRequest efetuaCompraRequest = (EfetuaCompraRequest) value;
        if (!produtoRepository.findById(efetuaCompraRequest.getProdutoId()).isPresent()) {
            errors.rejectValue("produtoId", null, "produtoId não encontrado.");
        }

    }
}
