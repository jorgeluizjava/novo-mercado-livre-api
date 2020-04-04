package br.com.deveficiente.nossomercadolivreapi.shared.infra;

import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;

public class Email {

    @NotBlank
    private String de;

    @NotBlank
    private String para;

    @NotBlank
    private String assunto;

    @NotBlank
    private String corpo;

    public Email(@NotBlank String de, @NotBlank String para, @NotBlank String assunto, @NotBlank String corpo) {

        Assert.hasText(de, "Dê não informado");
        Assert.hasText(para, "Destinatário do e-mail não informado.");
        Assert.hasText(assunto, "Assunto do e-mail não informado.");
        Assert.hasText(corpo, "Corpo do e-mail não informado");

        this.de = de;
        this.para = para;
        this.assunto = assunto;
        this.corpo = corpo;
    }

    public String getDe() {
        return de;
    }

    public String getPara() {
        return para;
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
                "de='" + de + '\'' +
                ", para='" + para + '\'' +
                ", assunto='" + assunto + '\'' +
                ", corpo='" + corpo + '\'' +
                '}';
    }
}
