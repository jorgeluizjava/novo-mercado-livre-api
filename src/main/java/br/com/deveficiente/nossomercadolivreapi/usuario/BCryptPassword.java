package br.com.deveficiente.nossomercadolivreapi.usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class BCryptPassword {

    @NotEmpty
    @Size(min = 6)
    private String password;

    @Deprecated
    public BCryptPassword() {
    }

    public BCryptPassword(@NotEmpty @Size(min = 6) String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public String getPassword() {
        return password;
    }
}
