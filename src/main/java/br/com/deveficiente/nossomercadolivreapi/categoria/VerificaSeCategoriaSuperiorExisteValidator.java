package br.com.deveficiente.nossomercadolivreapi.categoria;

import br.com.deveficiente.nossomercadolivreapi.shared.FindById;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

public class VerificaSeCategoriaSuperiorExisteValidator implements Validator {

    private CategoriaRepository categoriaRepository;

    public VerificaSeCategoriaSuperiorExisteValidator(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CategoriaRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CategoriaRequest categoriaRequest = (CategoriaRequest) o;
        if (categoriaRequest.getCategoriaSuperiorId() == null || categoriaRequest.getCategoriaSuperiorId() <= 0) {
            return;
        }

        Optional<Categoria> optionalCategoria = FindById.executa(categoriaRequest.getCategoriaSuperiorId(), categoriaRepository);
        if (!optionalCategoria.isPresent()) {
            errors.rejectValue("categoriaSuperiorId", null, "Categoria superior ID: " + categoriaRequest.getCategoriaSuperiorId() + " não encontrada.");
        }
    }
}
