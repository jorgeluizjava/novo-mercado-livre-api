package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.shared.infra.Email;
import br.com.deveficiente.nossomercadolivreapi.shared.infra.EnviadorDeEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotBlank;

@Service
public class ProdutoPerguntaService {

    @Autowired
    private EnviadorDeEmailService enviadorDeEmailService;

    public void notificaVendedor(ProdutoPergunta produtoPergunta, UriComponentsBuilder uriComponentsBuilder) {

        Assert.notNull(produtoPergunta, "Produto Pergunta não pode ser nulo.");

        String de = produtoPergunta.getEmailUsuario();
        String para = produtoPergunta.getEmailVendedor();
        String assunto = para + " você tem uma nova pergunta!";

        String link = produtoPergunta.getLinkDetalheProduto(uriComponentsBuilder);
        String corpo = produtoPergunta.getTitulo() + " \n" + "Detalhe produto: " + link;

        Email email = new Email(de, para, assunto, corpo);
        enviadorDeEmailService.envia(email);
    }
}
