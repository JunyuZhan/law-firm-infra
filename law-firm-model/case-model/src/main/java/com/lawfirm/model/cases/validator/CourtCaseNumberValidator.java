package com.lawfirm.model.cases.validator;

import com.lawfirm.model.cases.utils.CourtUtils;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CourtCaseNumberValidator.CourtCaseNumberValidatorImpl.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CourtCaseNumberValidator {

    String message() default "法院案号格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class CourtCaseNumberValidatorImpl implements ConstraintValidator<CourtCaseNumberValidator, String> {

        @Override
        public void initialize(CourtCaseNumberValidator constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;  // 如果为空，由@NotNull处理
            }
            return CourtUtils.validateCourtCaseNumber(value);
        }
    }
} 