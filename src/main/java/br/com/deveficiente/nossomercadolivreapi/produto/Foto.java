package br.com.deveficiente.nossomercadolivreapi.produto;

import org.hibernate.validator.constraints.URL;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "produto_foto")
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fotoId;

    @NotEmpty
    @URL
    private String url;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public Foto(@NotEmpty @URL String url, @NotNull Produto produto) {
        this.url = url;
        this.produto = produto;
    }

}
