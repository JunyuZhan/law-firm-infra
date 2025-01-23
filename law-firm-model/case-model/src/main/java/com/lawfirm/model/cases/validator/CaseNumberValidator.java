package com.lawfirm.model.cases.validator;

import com.lawfirm.model.cases.utils.CaseNumberUtils;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CaseNumberValidator.CaseNumberValidatorImpl.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CaseNumberValidator {

    String message() default "案件编号格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class CaseNumberValidatorImpl implements ConstraintValidator<CaseNumberValidator, String> {

        @Override
        public void initialize(CaseNumberValidator constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) {
                return true;  // 如果为空，由@NotNull处理
            }
            return CaseNumberUtils.validateCaseNumber(value);
        }
    }
} 