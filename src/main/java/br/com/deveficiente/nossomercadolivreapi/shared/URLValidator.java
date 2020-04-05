package br.com.deveficiente.nossomercadolivreapi.shared;

import org.apache.commons.validator.routines.UrlValidator;

public class URLValidator {

    public static void urlValida(String url, String message) {
        if (!UrlValidator.getInstance().isValid(url)) {
            throw new IllegalArgumentException(message);
        }
    }
}
