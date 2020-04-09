package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.shared.URLValidator;
import org.hibernate.validator.constraints.URL;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    @Deprecated
    private Foto() {
    }

    public Foto(@NotEmpty @URL String url, @NotNull Produto produto) {

        URLValidator.urlValida(url, "URL inválida.");
        Assert.notNull(produto, "Produto não pode ser nulo.");

        this.url = url;
        this.produto = produto;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Foto foto = (Foto) o;
        return Objects.equals(fotoId, foto.fotoId) &&
                url.equals(foto.url) &&
                produto.equals(foto.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fotoId, url, produto);
    }
}
