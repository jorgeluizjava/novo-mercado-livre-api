package br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.pagseguro;

import br.com.deveficiente.nossomercadolivreapi.compra.nova.Compra;
import br.com.deveficiente.nossomercadolivreapi.compra.nova.CompraRepository;
import br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.NovoPagamentoEvent;
import br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.Pagamento;
import br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.PagamentoRepository;
import br.com.deveficiente.nossomercadolivreapi.compra.retornopagamento.VerificaSeCompraJaEstaConcluidaValidator;
import br.com.deveficiente.nossomercadolivreapi.shared.FindById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
public class RetornoPagamentoPagSeguroController {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.addValidators(new VerificaSeCompraJaEstaConcluidaValidator(compraRepository));
    }

    @PostMapping(value = {"/api/retorno-pagamento/{compraId}/pagseguro"})
    @Transactional
    public void processaRetorno(@PathVariable("compraId") Long compraId, @Valid RetornoPagamentoPagSeguroRequest retornoPagamentoPagSeguroRequest, UriComponentsBuilder uriComponentsBuilder) {

        Compra compra = FindById.executa(compraId, compraRepository);

        Pagamento pagamento = retornoPagamentoPagSeguroRequest.criaPagamento(compra);
        pagamentoRepository.save(pagamento);

        compra.registra(pagamento);
        compraRepository.save(compra);

        applicationEventPublisher.publishEvent(new NovoPagamentoEvent(this, pagamento, uriComponentsBuilder));
    }
}
