package br.com.deveficiente.nossomercadolivreapi.email;

import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @NotNull
    private LocalDateTime dataEnvio = LocalDateTime.now();

    @NotBlank
    private String assunto;

    @NotBlank
    private String corpo;

    public Email(@NotNull Usuario de, @NotNull Usuario para, @NotBlank String assunto, @NotBlank String corpo) {

        Assert.notNull(de, "Dê não informado");
        Assert.notNull(para, "Destinatário do e-mail não informado.");
        Assert.hasText(assunto, "Assunto do e-mail não informado.");
        Assert.hasText(corpo, "Corpo do e-mail não informado");

        this.usuario = de;
        this.vendedor = para;
        this.assunto = assunto;
        this.corpo = corpo;
    }

    public String getDe() {
        return usuario.getLogin();
    }

    public String getPara() {
        return vendedor.getLogin();
    }

    public String getAssunto() {
        return assunto;
    }

    public String getCorpo() {
        return corpo;
    }

    @Override
    public String toString() {
        return "Email{" +
                "de='" + getDe() + '\'' +
                ", para='" + getPara() + '\'' +
                ", assunto='" + assunto + '\'' +
                ", corpo='" + corpo + '\'' +
                '}';
    }
}
