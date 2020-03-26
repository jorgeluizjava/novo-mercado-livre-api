package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.categoria.Categoria;
import br.com.deveficiente.nossomercadolivreapi.categoria.CategoriaRepository;
import br.com.deveficiente.nossomercadolivreapi.shared.infra.Uploader;
import br.com.deveficiente.nossomercadolivreapi.usuario.Usuario;
import br.com.deveficiente.nossomercadolivreapi.usuario.UsuarioRepository;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProdutoRequest {

    @NotNull(message = "A Categoria é obrigatória")
    @Min(value = 1, message = "O categoriaId deve ser maior do que zero.")
    private Long categoriaId;

    @NotEmpty(message = "O produto deve possuir pelo menos uma foto.")
    private List<MultipartFile> fotos;

    @NotEmpty(message = "O nome do produto é obrigatório")
    private String nome;

    @NotNull(message = "valor é obrigatório")
    @Min(value = 1, message = "O valor deve ser maior que zero.")
    private BigDecimal valor;

    @Min(value = 0, message = "A quantidade deve ser maior ou igual a zero.")
    private int quantidade;

    @Size(min = 3, message = "Informe pelo menos 3 caracteristicas.")
    private List<CaracteristicaRequest> caracteristicas;

    @NotEmpty(message = "A descrição é obrigatória")
    @Length(max = 1000)
    private String descricao;



    public List<MultipartFile> getFotos() {
        return fotos;
    }

    public void setFotos(List<MultipartFile> fotos) {
        this.fotos = fotos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public List<CaracteristicaRequest> getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(List<CaracteristicaRequest> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Produto criaProduto(Usuario usuario, CategoriaRepository categoriaRepository, Uploader uploader) {

        Optional<Categoria> optionalCategoria = categoriaRepository.findById(categoriaId);
        if (!optionalCategoria.isPresent()) {
            throw new IllegalArgumentException("categoriaId: " + categoriaId + " não encontrada.");
        }

        Categoria categoria = optionalCategoria.get();
        List<String> urlsFotos = uploader.upload(fotos);
        return new Produto(usuario, categoria, nome, valor, quantidade, descricao, urlsFotos, caracteristicas);
    }

}
