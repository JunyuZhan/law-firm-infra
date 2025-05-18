package com.lawfirm.document.config.aspect;

import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.common.security.annotation.RequiresRoles;
import com.lawfirm.common.security.annotation.Logical;
import com.lawfirm.common.security.context.SecurityContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 文档模块安全切面
 * 用于处理文档相关的权限和角色校验
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SecurityAspect {

    private final SecurityContext securityContext;

    /**
     * 配置切入点
     */
    @Pointcut("execution(* com.lawfirm.document..*.controller..*.*(..))")
    public void securityPointcut() {
    }

    /**
     * 环绕通知
     */
    @Around("securityPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 检查权限注解
        RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
        if (requiresPermissions != null) {
            checkPermissions(requiresPermissions.value(), requiresPermissions.logical());
        }

        // 检查角色注解
        RequiresRoles requiresRoles = method.getAnnotation(RequiresRoles.class);
        if (requiresRoles != null) {
            checkRoles(requiresRoles.value(), requiresRoles.logical());
        }

        // 记录安全操作
        logSecurityOperation(method.getName(), joinPoint.getArgs());

        // 执行方法
        return joinPoint.proceed();
    }

    /**
     * 检查权限
     */
    private void checkPermissions(String[] permissions, Logical logical) {
        boolean hasPermission = false;
        if (permissions != null && permissions.length > 0 && securityContext.getAuthorization() != null) {
            if (logical == Logical.AND) {
                hasPermission = Arrays.stream(permissions).allMatch(p -> securityContext.getAuthorization().hasPermission(p));
            } else {
                hasPermission = Arrays.stream(permissions).anyMatch(p -> securityContext.getAuthorization().hasPermission(p));
            }
        }
        if (!hasPermission) {
            throw new SecurityException("无权限: " + Arrays.toString(permissions));
        }
        log.debug("检查权限 - Permissions: {}, Logical: {}", Arrays.toString(permissions), logical);
    }

    /**
     * 检查角色
     */
    private void checkRoles(String[] roles, Logical logical) {
        boolean hasRole = false;
        if (roles != null && roles.length > 0 && securityContext.getAuthorization() != null) {
            if (logical == Logical.AND) {
                hasRole = Arrays.stream(roles).allMatch(r -> securityContext.getAuthorization().hasRole(r));
            } else {
                hasRole = Arrays.stream(roles).anyMatch(r -> securityContext.getAuthorization().hasRole(r));
            }
        }
        if (!hasRole) {
            throw new SecurityException("无角色: " + Arrays.toString(roles));
        }
        log.debug("检查角色 - Roles: {}, Logical: {}", Arrays.toString(roles), logical);
    }

    /**
     * 记录安全操作
     */
    private void logSecurityOperation(String methodName, Object[] args) {
        log.info("安全操作 - Method: {}, User: {}", 
                methodName, securityContext.getAuthentication().getPrincipal());
    }
}
