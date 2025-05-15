package com.mooky.blog.domain.user.constraints;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UsernameValidator.class)
/*
 * TODO medium article
 * NEED @Retention(RetentionPolicy.RUNTIME) for constraint to apply
 * default is RetentionPolicy.Class
 * retention policy - how long annotations are stored and accessible
 * runtime makes constraint available for inspection and use at runtime 
 */
@Retention(RetentionPolicy.RUNTIME) 
public @interface UsernameConstraints {
  String message() default "Invalid username format";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
