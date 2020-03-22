package br.com.deveficiente.nossomercadolivreapi.usuario;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UsuarioRequest {

    @NotEmpty(message = "Login não informado.")
    @Email
    private String login;

    @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
    private String senha;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "UsuarioRequest{" +
                "login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }

    public Usuario criaUsuario() {
        return new Usuario(login, new BCryptPassword(senha));
    }
}
