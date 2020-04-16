package br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.listeners;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.Compra;
import br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.NovoPagamentoEvent;
import br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.Pagamento;
import br.com.deveficiente.nossomercadolivreapi.email.Email;
import br.com.deveficiente.nossomercadolivreapi.email.EnviadorDeEmailService;
import br.com.deveficiente.nossomercadolivreapi.produto.Produto;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import br.com.deveficiente.nossomercadolivreapi.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class NotificaErroPagamentoNovaCompraParaSetorListener implements ApplicationListener<NovoPagamentoEvent>, Ordered {

    @Autowired
    private EnviadorDeEmailService enviadorDeEmailService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void onApplicationEvent(NovoPagamentoEvent novoPagamentoEvent) {

        Pagamento pagamento = novoPagamentoEvent.getPagamento();
        if (!pagamento.comSucesso()) {

            Compra compra = pagamento.getCompra();
            Produto produto = compra.getProduto();

            Usuario de = getUsuarioDoSistema();
            Usuario para = getUsuarioSetorDeCompras();

            String assunto = "Falha ao processar pagamento referente a compraId: " + compra.getId();

            String link = novoPagamentoEvent
                                .getUriComponentsBuilder().path("/api/produtos/{produtoId}/detalhes")
                                .buildAndExpand(produto.getProdutoId()).toUriString();

            String corpo = String.format("Houve uma falha ao processar o pagamento id: %s notificar o usuário: %s, efetuou uma compra do produto: %s, quantidade: %d no valor total de: %s, \n Detalhe Produto: %s",
                                        pagamento.getId(),
                                        compra.getUsuario().getLogin(),
                                        produto.getNome(),
                                        compra.getQuantidade(),
                                        compra.getValor(),
                                        link);

            Email email = new Email(de, para, assunto, corpo);

            enviadorDeEmailService.envia(email);
        }
    }

    private Usuario getUsuarioDoSistema() {
        String email = "usuariodosistema@email.com.br";
        return usuarioRepository.findByLogin(email).get();
    }

    private Usuario getUsuarioSetorDeCompras() {
        String email = "usuariosetordecompras@email.com.br";
        return usuarioRepository.findByLogin(email).get();
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
