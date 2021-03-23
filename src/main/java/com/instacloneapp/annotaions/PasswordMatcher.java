package com.instacloneapp.annotaions;

import com.instacloneapp.validations.PasswordMatcherValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatcherValidation.class)
@Documented
public @interface PasswordMatcher {

    String message() default "Passwords should be equal";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
