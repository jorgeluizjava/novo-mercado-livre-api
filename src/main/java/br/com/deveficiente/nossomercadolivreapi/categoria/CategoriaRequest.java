package br.com.deveficiente.nossomercadolivreapi.categoria;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class CategoriaRequest {

    @NotEmpty(message = "Nome não informado.")
    private String nome;

    private Long categoriaSuperiorId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCategoriaSuperiorId() {
        return categoriaSuperiorId;
    }

    public void setCategoriaSuperiorId(Long categoriaSuperiorId) {
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

        if (categoriaSuperiorId == null || categoriaSuperiorId <= 0) {
            return new Categoria(nome, Optional.empty());
        }

        Optional<Categoria> optionalCategoria = categoriaRepository.findById(categoriaSuperiorId);

        return new Categoria(nome, optionalCategoria);
    }
}
