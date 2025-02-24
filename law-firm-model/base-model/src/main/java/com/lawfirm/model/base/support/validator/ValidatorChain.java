package com.lawfirm.model.base.support.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * 验证链
 * 用于组合多个验证器进行链式验证
 */
public class ValidatorChain<T> {

    private final List<Predicate<T>> validators = new ArrayList<>();

    /**
     * 添加验证器
     */
    public ValidatorChain<T> addValidator(Predicate<T> validator) {
        if (validator == null) {
            throw new IllegalArgumentException("验证器不能为空");
        }
        validators.add(validator);
        return this;
    }

    /**
     * 执行验证
     * 所有验证器都通过才返回true
     */
    public boolean validate(T target) {
        return validators.stream().allMatch(v -> v.test(target));
    }
} 