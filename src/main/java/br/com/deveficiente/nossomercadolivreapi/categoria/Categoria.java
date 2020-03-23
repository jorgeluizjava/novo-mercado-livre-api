package br.com.deveficiente.nossomercadolivreapi.categoria;

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
    public Categoria() {
    }

    /**
     *
     * @param nome
     * @param categoriaSuperior
     */
    public Categoria(@NotEmpty String nome, Optional<Categoria> categoriaSuperior) {
        this.nome = nome;
        if (categoriaSuperior.isPresent()) {
            this.categoriaSuperior = categoriaSuperior.get();
        }
    }
}
