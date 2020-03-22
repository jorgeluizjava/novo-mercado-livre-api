package br.com.deveficiente.nossomercadolivreapi.categoria;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class CategoriaRequest {

    @NotEmpty(message = "Nome não informado.")
    private String nome;

    @Min(value = 0, message = "Valor deve ser maior ou igual a zero")
    private long categoriaSuperiorId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getCategoriaSuperiorId() {
        return categoriaSuperiorId;
    }

    public void setCategoriaSuperiorId(long categoriaSuperiorId) {
        this.categoriaSuperiorId = categoriaSuperiorId;
    }

    @Override
    public String toString() {
        return "CategoriaRequest{" +
                "nome='" + nome + '\'' +
                ", categoriaSuperiorId=" + categoriaSuperiorId +
                '}';
    }

    public Categoria criaCategoria(CategoriaRepository categoriaRepository) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(categoriaSuperiorId);
        return new Categoria(nome, optionalCategoria);
    }
}
