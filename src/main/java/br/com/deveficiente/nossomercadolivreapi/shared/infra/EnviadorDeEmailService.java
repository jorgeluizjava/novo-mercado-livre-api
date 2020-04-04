package br.com.deveficiente.nossomercadolivreapi.shared.infra;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class EnviadorDeEmailService {

    public void envia(Email email) {

        Assert.notNull(email, "Email não pode ser nulo.");

        System.out.printf("Enviando e-mail Dê: %s Para: %s com assunto: %s com o conteúdo: %s \n",
                email.getDe(),
                email.getPara(),
                email.getAssunto(),
                email.getCorpo());

        System.out.println("Email enviado com sucesso.");
    }
}
