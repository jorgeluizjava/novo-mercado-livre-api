package br.com.deveficiente.nossomercadolivreapi.usuario;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;

    @NotEmpty
    @Email
    @Column(unique = true)
    private String login;

    @Size(min = 6)
    private String senha;

    @PastOrPresent
    private LocalDateTime createdAt = LocalDateTime.now();

    @Deprecated
    private Usuario() {
    }

    /**
     *
     * @param login
     * @param bCryptPassword
     */
    public Usuario(@NotEmpty @Email String login, @NotNull BCryptPassword bCryptPassword) {

        Assert.hasText(login, "Login não informado.");
        Assert.notNull(bCryptPassword, "BCryptPassword não informado.");

        this.login = login;
        this.senha = bCryptPassword.getPassword();
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }
}
