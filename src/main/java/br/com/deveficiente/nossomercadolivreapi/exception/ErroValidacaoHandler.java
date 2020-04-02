package br.com.deveficiente.nossomercadolivreapi.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class ErroValidacaoHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public List<ErroValidacaoCampo> handleErroValidacaoCampo(Exception exception) {

        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;
            return getErroValidacaoCampo(ex.getBindingResult());
        } else {
            BindException ex = (BindException) exception;
            return getErroValidacaoCampo(ex.getBindingResult());
        }
    }

    private List<ErroValidacaoCampo> getErroValidacaoCampo(BindingResult bindingResult) {

        return bindingResult
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    return new ErroValidacaoCampo(fieldError.getField(), fieldError.getDefaultMessage());
                })
                .collect(toList());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ErroAplicacao handleErroAplicacao(Exception exception) {
        return new ErroAplicacao(exception.getMessage());
    }
}
