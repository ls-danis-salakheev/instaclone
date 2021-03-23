package com.instacloneapp.validations;

import com.instacloneapp.annotaions.PasswordMatcher;
import com.instacloneapp.payload.request.SignUpRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatcherValidation implements ConstraintValidator<PasswordMatcher, Object> {

    @Override
    public void initialize(PasswordMatcher constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        SignUpRequest request =  (SignUpRequest)obj;

        return request.getPassword().equals(request.getConfirmPassword());
    }
}
