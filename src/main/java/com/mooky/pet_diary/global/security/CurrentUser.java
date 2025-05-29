package com.mooky.pet_diary.global.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see CurrentUserResolver
 * @return (Long) userId of current user based on jwt
 */
@Target(ElementType.PARAMETER) // where it is used
@Retention(RetentionPolicy.RUNTIME) // when is the annotation available
public @interface CurrentUser {
    
}
