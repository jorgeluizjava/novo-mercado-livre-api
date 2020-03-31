package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.categoria.Categoria;
import br.com.deveficiente.nossomercadolivreapi.categoria.CategoriaRepository;
import br.com.deveficiente.nossomercadolivreapi.shared.FindById;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

public class VerificaSeCategoriaExisteValidator implements Validator {

    private CategoriaRepository categoriaRepository;

    public VerificaSeCategoriaExisteValidator(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ProdutoRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProdutoRequest produtoRequest = (ProdutoRequest) o;
        if (produtoRequest.getCategoriaId() == null) {
            return;
        }

        Optional<Categoria> optionalCategoria = FindById.executa(produtoRequest.getCategoriaId(), categoriaRepository);
        if (!optionalCategoria.isPresent()) {
            errors.rejectValue("categoriaId", null, "Não foi encontrada categoria para o categoriaId: " + produtoRequest.getCategoriaId());
        }
    }
}
