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
public class NotificaSucessoNovaCompraParaVendedorListener implements ApplicationListener<NovoPagamentoEvent>, Ordered {

    @Autowired
    private EnviadorDeEmailService enviadorDeEmailService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void onApplicationEvent(NovoPagamentoEvent novoPagamentoEvent) {

        Pagamento pagamento = novoPagamentoEvent.getPagamento();
        if (pagamento.comSucesso()) {

            Compra compra = pagamento.getCompra();
            Produto produto = compra.getProduto();

            Usuario de = getUsuarioDoSistema();
            Usuario para = compra.getUsuario();

            String assunto = "Pagamento realizado com sucesso referente a compraId: " + compra.getId();

            String link = novoPagamentoEvent
                                .getUriComponentsBuilder().path("/api/produtos/{produtoId}/detalhes")
                                .buildAndExpand(produto.getProdutoId()).toUriString();

            String corpo = String.format("Acabamos de receber o pagamento id: %s com sucesso. \n Referente ao produto: %s, com quantidade: %d no valor total de: %s já está sendo separado, \n Visualizar detalhe produto: %s",
                                        pagamento.getId(),
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

    @Override
    public int getOrder() {
        return 2;
    }
}
