package br.com.deveficiente.nossomercadolivreapi.produto;

import br.com.deveficiente.nossomercadolivreapi.shared.infra.Uploader;
import br.com.deveficiente.nossomercadolivreapi.usuario.UsuarioRepository;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProdutoRequest {

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


    public Produto criaProduto(UsuarioRepository usuarioRepository, Uploader uploader) {

        List<String> urlsFotos = uploader.upload(fotos);
        List<Foto> fotosDoProduto = criaFotos(urlsFotos);
        List<Caracteristica> caracteristicasDoProduto = criaCaracteristicas();

        return new Produto(nome, valor, quantidade, descricao, fotosDoProduto, caracteristicasDoProduto);
    }

    private List<Foto> criaFotos(List<String> urlsFotos) {

        List<Foto> fotosProduto = new ArrayList<>();

        for (String url : urlsFotos) {
            Foto foto = new Foto(url);
            fotosProduto.add(foto);
        }

        return fotosProduto;
    }

    private List<Caracteristica> criaCaracteristicas() {

        List<Caracteristica> categoriasProduto = new ArrayList<>();

        for (CaracteristicaRequest caracteristicaRequest : caracteristicas) {
            Caracteristica caracteristica = caracteristicaRequest.criaCaracteristica();
            categoriasProduto.add(caracteristica);
        }

        return categoriasProduto;
    }
}
