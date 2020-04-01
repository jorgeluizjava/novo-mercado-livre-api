package br.com.deveficiente.nossomercadolivreapi.shared;

import br.com.deveficiente.nossomercadolivreapi.produto.Produto;
import br.com.deveficiente.nossomercadolivreapi.produto.ProdutoRepository;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.util.Assert;

import java.util.Optional;

public class FindById {

    public static <T> T executa(Long id, CrudRepository<T, Long> repository) {

        Assert.notNull(id, "id não pode ser nulo.");

        Optional<T> entityOptional = repository.findById(id);
        if (!entityOptional.isPresent()) {
            throw new IllegalArgumentException("id: " + id + " não encontrado.");
        }

        return entityOptional.get();
    }

}
