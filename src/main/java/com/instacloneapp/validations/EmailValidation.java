package com.instacloneapp.validations;

import com.instacloneapp.annotaions.ValidEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation implements ConstraintValidator<ValidEmail, String> {

    public static final String EMAIL_PATTERN = "^(.+)@(.+)$";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        return (validateEmail(email));
    }

    public boolean validateEmail(String email) {

        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();

    }
}
