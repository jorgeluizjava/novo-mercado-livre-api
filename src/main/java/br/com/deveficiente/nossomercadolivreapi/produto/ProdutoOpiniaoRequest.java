package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import br.com.deveficiente.nossomercadolivreapi.usuario.UsuarioRepository;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class ProdutoOpiniaoRequest {

    @Min(value = 1, message = "Nota não pode ser menor que 1.")
    @Max(value = 5, message = "Nota não pode ser maior que 5")
    private int nota;

    @NotEmpty(message = "Título obrigatório")
    private String tituloOpiniao;

    @Length(min = 1, max = 500,  message = "A descrição é obrigatória e pode ter até 500 caracteres.")
    private String descricao;


    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getTituloOpiniao() {
        return tituloOpiniao;
    }

    public void setTituloOpiniao(String tituloOpiniao) {
        this.tituloOpiniao = tituloOpiniao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "ProdutoOpiniaoRequest{" +
                ", nota=" + nota +
                ", tituloOpiniao='" + tituloOpiniao + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    public ProdutoOpiniao criaProdutoOpiniao(Produto produto, Usuario usuarioLogado, UsuarioRepository usuarioRepository, ProdutoRepository produtoRepository) {

        Assert.notNull(produto, "Produto não informado.");
        Assert.notNull(usuarioLogado, "Usuário não informado.");

        return new ProdutoOpiniao(nota, tituloOpiniao, descricao, produto, usuarioLogado);
    }
}
