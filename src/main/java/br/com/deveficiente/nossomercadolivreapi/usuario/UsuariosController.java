package br.com.deveficiente.nossomercadolivreapi.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public void cria(@RequestBody @Valid  UsuarioRequest usuarioRequest) {

        Usuario novoUsuario = usuarioRequest.criaUsuario();
        usuarioRepository.save(novoUsuario);
    }
}
