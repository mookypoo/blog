package com.mooky.pet_diary.domain.user.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = EmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailConstraints {
    String message() default "wrong email format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
