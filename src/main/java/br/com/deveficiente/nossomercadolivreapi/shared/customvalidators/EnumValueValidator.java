package br.com.deveficiente.nossomercadolivreapi.shared.customvalidators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<ValidEnumValues, String> {

    private ValidEnumValues annotation;

    @Override
    public void initialize(ValidEnumValues annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {

        if (valueForValidation == null || valueForValidation.isEmpty()) {
            return false;
        }

        boolean result = false;

        Enum<?>[] enumValues = annotation.enumClass().getEnumConstants();
        if (enumValues != null) {

            for (Object enumValue: enumValues) {
                if (valueForValidation.equals(enumValue.toString())) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }
}
