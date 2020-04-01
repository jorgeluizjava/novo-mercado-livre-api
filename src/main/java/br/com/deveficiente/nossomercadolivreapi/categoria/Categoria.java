package br.com.deveficiente.nossomercadolivreapi.categoria;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @Column(name = "categoria_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoriaId;

    @NotEmpty
    @Column(unique = true)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "categoria_superior_id")
    private Categoria categoriaSuperior;

    @Deprecated
    private Categoria() {
    }

    /**
     *
     * @param nome
     * @param categoriaSuperior
     */
    public Categoria(@NotEmpty String nome, Optional<Categoria> categoriaSuperior) {

        Assert.hasText(nome, "Nome não foi informado.");

        if (categoriaSuperior != null && categoriaSuperior.isPresent()) {
            this.categoriaSuperior = categoriaSuperior.get();
        }

        this.nome = nome;
    }
}
