package br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.Compra;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String idTransacao;

    private Boolean status;

    @ManyToOne
    private Compra compra;

    @PastOrPresent
    private LocalDateTime createdAt = LocalDateTime.now();

    @Deprecated
    Pagamento() {

    }

    public Pagamento(@NotNull String idTransacao, @NotNull Boolean status, @NotNull Compra compra) {

        Assert.hasText(idTransacao, "idTransação não informado.");
        Assert.notNull(status, "Status não informado.");
        Assert.notNull(compra, "Compra não informada.");

        this.idTransacao = idTransacao;
        this.status = status;
        this.compra = compra;
    }

    public Long getId() {
        return id;
    }

    public boolean comSucesso() {
        return status;
    }

    public Compra getCompra() {
        return compra;
    }
}
