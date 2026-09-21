package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordStrength, String> {
    private int minimumSize;
    private int maximumSize;

    @Override
    public void initialize(PasswordStrength annotation) {
        this.minimumSize = annotation.min();
        this.maximumSize = annotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return (value.length() >= this.minimumSize && value.length() <= this.maximumSize);
    }
}
