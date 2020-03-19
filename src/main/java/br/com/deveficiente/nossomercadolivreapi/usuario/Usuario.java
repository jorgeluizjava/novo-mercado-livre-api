package br.com.deveficiente.nossomercadolivreapi.usuario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID usuarioId;

    @NotEmpty(message = "Login não informado.")
    @Email
    private String login;

    @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
    private String senha;

    @PastOrPresent
    private LocalDateTime createdAt = LocalDateTime.now();

    @Deprecated
    public Usuario() {
    }

    public Usuario(@NotEmpty @Email String login, @Size(min = 6) String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }
}
