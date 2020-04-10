package br.com.deveficiente.nossomercadolivreapi.compra.nova;

import br.com.deveficiente.nossomercadolivreapi.produto.Produto;
import br.com.deveficiente.nossomercadolivreapi.produto.ProdutoRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

public class VerificaQuantidadeDisponivelProduto implements Validator {

    private ProdutoRepository produtoRepository;

    public VerificaQuantidadeDisponivelProduto(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return EfetuaCompraRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object value, Errors errors) {

        EfetuaCompraRequest efetuaCompraRequest = (EfetuaCompraRequest) value;
        Optional<Produto> optionalProduto = produtoRepository.findById(efetuaCompraRequest.getProdutoId());
        if (!optionalProduto.isPresent()) {
            return;
        }

        Produto produto = optionalProduto.get();
        if (efetuaCompraRequest.getQuantidade() > produto.getQuantidade()) {
            errors.rejectValue("produtoId", null, "quantidade do produto insuficiente, qtd: " + produto.getQuantidade());
        }
    }
}
