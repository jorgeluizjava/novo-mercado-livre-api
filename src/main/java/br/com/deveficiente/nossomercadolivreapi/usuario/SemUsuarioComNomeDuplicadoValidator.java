package br.com.deveficiente.nossomercadolivreapi.usuario;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

public class SemUsuarioComNomeDuplicadoValidator implements Validator {

    private UsuarioRepository usuarioRepository;

    public SemUsuarioComNomeDuplicadoValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsuarioRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UsuarioRequest usuarioRequest = (UsuarioRequest) o;
        Optional<Usuario> optionalUsuario = usuarioRepository.findByLogin(usuarioRequest.getLogin());
        if (optionalUsuario.isPresent()) {
            errors.rejectValue("login", null, "Já existe um usuário com o e-mail: " + usuarioRequest.getLogin());
        }
    }
}
