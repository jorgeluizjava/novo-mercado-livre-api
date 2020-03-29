package br.com.deveficiente.nossomercadolivreapi.exception;

public class ErroAplicacao {

    private String mensagem;

    public ErroAplicacao(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
