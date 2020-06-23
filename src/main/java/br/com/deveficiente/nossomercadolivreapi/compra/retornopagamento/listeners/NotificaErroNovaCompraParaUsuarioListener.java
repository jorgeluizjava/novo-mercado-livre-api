package br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.listeners;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.Compra;
import br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.NovoPagamentoEvent;
import br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.Pagamento;
import br.com.deveficiente.nossomercadolivreapi.email.Email;
import br.com.deveficiente.nossomercadolivreapi.email.EnviadorDeEmailService;
import br.com.deveficiente.nossomercadolivreapi.gatewaypagamento.GatewayPagamentoType;
import br.com.deveficiente.nossomercadolivreapi.produto.Produto;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import br.com.deveficiente.nossomercadolivreapi.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class NotificaErroNovaCompraParaUsuarioListener implements ApplicationListener<NovoPagamentoEvent>, Ordered {

    @Autowired
    private EnviadorDeEmailService enviadorDeEmailService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void onApplicationEvent(NovoPagamentoEvent novoPagamentoEvent) {

        Pagamento pagamento = novoPagamentoEvent.getPagamento();
        if (!pagamento.comSucesso()) {

            Compra compra = pagamento.getCompra();

            Usuario de = getUsuarioDoSistema();
            Usuario para = compra.getUsuario();

            String assunto = "Falha ao processar pagamento referente a compraId: " + compra.getId();

            String corpo = String.format("Houve uma falha ao processar o pagamento id: %s \n Tente novamente através do link: %s",
                    pagamento.getId(),
                    compra.getUrlGatewayPagamento(novoPagamentoEvent.getUriComponentsBuilder()));

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
        return 4;
    }
}
