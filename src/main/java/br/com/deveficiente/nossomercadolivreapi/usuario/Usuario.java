package br.com.deveficiente.nossomercadolivreapi.usuario;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;

    @NotEmpty
    @Email
    private String login;

    @Size(min = 6)
    private String senha;

    @PastOrPresent
    private LocalDateTime createdAt = LocalDateTime.now();

    @Deprecated
    public Usuario() {
    }

    /**
     *
     * @param login
     * @param bCryptPassword
     */
    public Usuario(@NotEmpty @Email String login, @NotNull BCryptPassword bCryptPassword) {

        Assert.notNull(bCryptPassword, "BCryptPassword não informado.");

        this.login = login;
        this.senha = bCryptPassword.getPassword();
    }

    public String getSenha() {
        return senha;
    }
}
