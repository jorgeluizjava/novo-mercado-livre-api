package br.com.deveficiente.nossomercadolivreapi.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @InitBinder(value = {"categoriaRequest"})
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(new SemCategoriaComNomeDuplicadoValidator(categoriaRepository));
        dataBinder.addValidators(new VerificaSeCategoriaSuperiorExisteValidator(categoriaRepository));
    }

    @PostMapping
    public void cria(@RequestBody @Valid CategoriaRequest categoriaRequest) {

        Categoria novaCategoria = categoriaRequest.criaCategoria(categoriaRepository);
        categoriaRepository.save(novaCategoria);
    }
}
