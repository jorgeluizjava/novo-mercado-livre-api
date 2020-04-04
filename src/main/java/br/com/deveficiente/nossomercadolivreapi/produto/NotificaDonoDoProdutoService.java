package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.email.Email;
import br.com.deveficiente.nossomercadolivreapi.email.EnviadorDeEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NotificaDonoDoProdutoService {

    @Autowired
    private EnviadorDeEmailService enviadorDeEmailService;

    public void executa(ProdutoPergunta produtoPergunta, UriComponentsBuilder uriComponentsBuilder) {

        Assert.notNull(produtoPergunta, "Produto Pergunta não pode ser nulo.");

        Email email = produtoPergunta.constroiNotificaoParaDonoProduto(uriComponentsBuilder);
        enviadorDeEmailService.envia(email);
    }
}
