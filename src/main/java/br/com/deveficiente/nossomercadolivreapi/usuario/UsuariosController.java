package br.com.deveficiente.nossomercadolivreapi.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @InitBinder(value = {"usuarioRequest"})
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(new SemUsuarioComNomeDuplicadoValidator(usuarioRepository));
    }

    @PostMapping
    public void cria(@RequestBody @Valid  UsuarioRequest usuarioRequest) {

        Usuario novoUsuario = usuarioRequest.criaUsuario();
        usuarioRepository.save(novoUsuario);
    }
}
