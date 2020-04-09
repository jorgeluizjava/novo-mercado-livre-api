package br.com.deveficiente.nossomercadolivreapi.produto.detalhe;

import br.com.deveficiente.nossomercadolivreapi.produto.ProdutoPergunta;

public class PerguntaProdutoDetalheDTO {

    private Long perguntaId;
    private String titulo;

    public PerguntaProdutoDetalheDTO(ProdutoPergunta produtoPergunta) {
        this.perguntaId = produtoPergunta.getPerguntaId();
        this.titulo = produtoPergunta.getTitulo();
    }

    public Long getPerguntaId() {
        return perguntaId;
    }

    public String getTitulo() {
        return titulo;
    }
}
