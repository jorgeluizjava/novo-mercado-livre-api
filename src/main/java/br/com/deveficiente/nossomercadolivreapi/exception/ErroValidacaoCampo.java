package br.com.deveficiente.nossomercadolivreapi.exception;

public class ErroValidacaoCampo {

    private String campo;
    private String mensagem;

    public ErroValidacaoCampo(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
