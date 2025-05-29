package com.mooky.pet_diary.domain.user.constraints;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * 8 ~ 20자
 * <p>영문, 숫자 및 특수문자 (?, !,@, #, $, %, -, _) 만 가능
 * <p>영문, 숫자 및 특수문자 (?, !,@, #, $, %, -, _) 중 2개 이상
 */
public class PasswordValidator implements ConstraintValidator<PasswordConstraints, String> {

    private boolean hasNonAlphabetNumberSpecialChar(String value) {
        final Pattern regex = Pattern.compile("[^A-Za-z0-9|^?!@#$%_-]");
        return regex.matcher(value).find();
    }

    private boolean hasAtLeastTwoRequiredTypes(String value) {
        final String hasAlphabet = "(?=.*[A-Za-z])";
        final String hasNumbers = "(?=.*[0-9])";
        final String hasSpecialChars = "(?=.*[?!@#$%_-])";
        final Pattern regex = Pattern.compile("(" + hasAlphabet + hasNumbers + ")|("
            + hasAlphabet + hasSpecialChars + ")|(" + hasNumbers + hasSpecialChars + ")");
        return regex.matcher(value).find();
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String errorMessage = "";

        if (value == null) {
            errorMessage = "비밀번호를 입력해주세요";
        } else if (value.length() < 8) {
            errorMessage = "비밀번호는 최소 8자여야합니다";
        } else if (value.length() > 20) {
            errorMessage = "비밀번호는 최대 20자여야합니다";
        } else if (this.hasNonAlphabetNumberSpecialChar(value)) {
         errorMessage = "영문, 숫자 및 특수문자 (?, !, @, #, $, %, -, _) 만 가능합니다";
        } else if (!this.hasAtLeastTwoRequiredTypes(value)) {
         errorMessage = "영문, 숫자 및 특수문자 (?, !, @, #, $, %, -, _) 중 2개 이상을 사용해야됩니다";
        }

        if (!errorMessage.isEmpty()) {
            if (context != null) { // testing 일때 context is null
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(errorMessage).addConstraintViolation();
            }
            return false;
        }
        return true;
    }
  
  
}
