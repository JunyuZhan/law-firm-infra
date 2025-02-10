package com.lawfirm.staff.config;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Feign Fallback基础配置类
 */
@Slf4j
public abstract class FeignFallbackConfig {

    /**
     * 处理服务降级
     *
     * @param serviceName 服务名称
     * @param methodName 方法名称
     * @param type 返回类型的Class对象
     * @return 降级响应
     */
    protected <T> Result<T> handleFallback(String serviceName, String methodName, Class<T> type) {
        log.error("{}的{}方法调用失败，执行服务降级", serviceName, methodName);
        return Result.error(HttpStatus.SERVICE_UNAVAILABLE.value(), 
            String.format("%s服务暂时不可用", serviceName));
    }

    @SuppressWarnings("unchecked")
    protected <T, R> Result<T> handleFallback(String serviceName, String methodName, Class<T> outerType, Class<R> innerType) {
        log.error("{}的{}方法调用失败，执行服务降级", serviceName, methodName);
        Result<T> result = new Result<>();
        result.setCode(HttpStatus.SERVICE_UNAVAILABLE.value());
        result.setMessage(String.format("%s服务暂时不可用", serviceName));
        return result;
    }

    /**
     * 处理分页服务降级
     *
     * @param serviceName 服务名称
     * @param methodName 方法名称
     * @param type 分页数据类型的Class对象
     * @return 降级响应
     */
    protected <T> Result<PageResult<T>> handlePageFallback(String serviceName, String methodName, Class<T> type) {
        log.error("{}的{}方法调用失败，执行服务降级", serviceName, methodName);
        return Result.error(HttpStatus.SERVICE_UNAVAILABLE.value(), 
            String.format("%s服务暂时不可用", serviceName));
    }

    /**
     * 创建泛型类型
     *
     * @param rawType 原始类型
     * @param actualTypeArgument 泛型参数类型
     * @return 泛型类型
     */
    protected ParameterizedType createParameterizedType(Class<?> rawType, Class<?> actualTypeArgument) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{actualTypeArgument};
            }

            @Override
            public Type getRawType() {
                return rawType;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }

    /**
     * 创建泛型类型
     *
     * @param rawType 原始类型
     * @param actualTypeArgument 泛型参数类型
     * @return 泛型类型
     */
    protected ParameterizedType createParameterizedType(Class<?> rawType, ParameterizedType actualTypeArgument) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{actualTypeArgument};
            }

            @Override
            public Type getRawType() {
                return rawType;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
} 