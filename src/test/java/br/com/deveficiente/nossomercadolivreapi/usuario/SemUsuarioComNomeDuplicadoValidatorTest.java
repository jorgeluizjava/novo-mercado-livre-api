package br.com.deveficiente.nossomercadolivreapi.usuario;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SemUsuarioComNomeDuplicadoValidatorTest {

    private UsuarioRepository usuarioRepository;

    private SemUsuarioComNomeDuplicadoValidator semUsuarioComNomeDuplicadoValidator;

    @BeforeEach
    public void setup() {
        usuarioRepository = mock(UsuarioRepository.class);
        semUsuarioComNomeDuplicadoValidator = new SemUsuarioComNomeDuplicadoValidator(usuarioRepository);
    }

    @Test
    public void deveRetornarErrorsListVaziaQuandoUsuarioEstiverSemNomeDuplicado() {

        UsuarioRequest usuarioRequest = new UsuarioRequest("emailunico@email.com.br", "123456");

        when(usuarioRepository.findByLogin(usuarioRequest.getLogin())).thenReturn(Optional.empty());

        Errors errors = new BeanPropertyBindingResult(usuarioRequest, "usuarioRequest");
        semUsuarioComNomeDuplicadoValidator.validate(usuarioRequest, errors);

        Assertions.assertFalse(errors.hasErrors());
    }

    @Test
    public void deveRetornarListaDeErroNaoVaziaQuandoUsuarioEstiverCemNomeDuplicado() {

        UsuarioRequest usuarioRequest = new UsuarioRequest("emailexistente@email.com.br", "123456");

        Usuario usuario = new Usuario(usuarioRequest.getLogin(), new BCryptPassword(usuarioRequest.getSenha()));

        when(usuarioRepository.findByLogin(usuarioRequest.getLogin())).thenReturn(Optional.of(usuario));

        Errors errors = new BeanPropertyBindingResult(usuarioRequest, "usuarioRequest");
        semUsuarioComNomeDuplicadoValidator.validate(usuarioRequest, errors);

        Assertions.assertTrue(errors.hasErrors());
        Assertions.assertEquals(errors.getFieldError().getDefaultMessage(), "Já existe um usuário com o e-mail: " + usuarioRequest.getLogin());
    }
}
