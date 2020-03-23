package br.com.deveficiente.nossomercadolivreapi.categoria;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

public class SemCategoriaComNomeDuplicadoValidator implements Validator {

    private CategoriaRepository categoriaRepository;

    public SemCategoriaComNomeDuplicadoValidator(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CategoriaRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CategoriaRequest categoriaRequest = (CategoriaRequest) o;
        Optional<Categoria> categoriaOptional = categoriaRepository.findByNome(categoriaRequest.getNome());
        if (categoriaOptional.isPresent()) {
            errors.rejectValue("nome", null, "Já existe uma categoria com o nome: " + categoriaRequest.getNome());
        }
    }
}
