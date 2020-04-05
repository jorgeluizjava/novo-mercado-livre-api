package br.com.deveficiente.nossomercadolivreapi.produto.detalhe;

import br.com.deveficiente.nossomercadolivreapi.produto.Caracteristica;

public class CaracteristicaProdutoDetalheDTO {

    private String nome;
    private String descricao;

    public CaracteristicaProdutoDetalheDTO(Caracteristica caracteristica) {
        nome = caracteristica.getNome();
        descricao = caracteristica.getDescricao();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
