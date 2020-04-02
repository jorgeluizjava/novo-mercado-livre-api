package br.com.deveficiente.nossomercadolivreapi.produto;

import org.springframework.util.Assert;

import javax.validation.constraints.NotEmpty;

public class CaracteristicaRequest {

    @NotEmpty(message = "O nome da caracterista é obrigatório")
    private String nome;

    @NotEmpty(message = "A descrição da caracterista é obrigatória")
    private String descricao;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Caracteristica criaCaracteristica(Produto produto) {
        Assert.notNull(produto, "Produto não informado.");
        return new Caracteristica(nome, descricao, produto);
    }
}
