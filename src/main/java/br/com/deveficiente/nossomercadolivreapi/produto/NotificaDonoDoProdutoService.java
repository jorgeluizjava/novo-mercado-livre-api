package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.shared.infra.Email;
import br.com.deveficiente.nossomercadolivreapi.shared.infra.EnviadorDeEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotBlank;

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
