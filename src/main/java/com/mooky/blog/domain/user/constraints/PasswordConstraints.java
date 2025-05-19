package com.mooky.blog.domain.user.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = PasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraints {
    String message() default "wrong password format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
