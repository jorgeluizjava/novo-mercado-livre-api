package br.com.deveficiente.nossomercadolivreapi.produto;

import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "produto_foto")
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fotoId;

    @NotEmpty
    @URL
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public Foto(@NotEmpty @URL String url) {
        this.url = url;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
