package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Documented
public @interface PasswordStrength {
    // 아래 3개 모두 @Constraint 에서 기본적으로 사용하는 어노테이션 옵션 파라미터라 꼭 있어야한다 그렇지 않으면 런타임 오류 발생
    String message() default "회원가입 시 강력한 패스워드 규칙에 위배되었습니다";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int min() default 1;
    int max() default Integer.MAX_VALUE;
}
