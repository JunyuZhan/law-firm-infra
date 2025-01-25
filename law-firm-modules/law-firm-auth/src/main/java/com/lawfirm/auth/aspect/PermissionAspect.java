package com.lawfirm.auth.aspect;

import com.lawfirm.auth.annotation.RequirePermission;
import com.lawfirm.auth.model.LoginUser;
import com.lawfirm.common.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Set;

@Slf4j
@Aspect
@Component
public class PermissionAspect {

    @Around("@annotation(com.lawfirm.auth.annotation.RequirePermission)")
    public Object checkPermission(ProceedingJoinPoint point) throws Throwable {
        // 获取用户权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException("用户未认证");
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Set<String> permissions = loginUser.getPermissions();

        // 获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        RequirePermission requirePermission = method.getAnnotation(RequirePermission.class);

        // 检查权限
        String permission = requirePermission.value();
        boolean requireAll = requirePermission.requireAll();

        if (permissions == null || permissions.isEmpty()) {
            throw new BusinessException("用户无权限");
        }

        if (requireAll) {
            // 需要所有权限
            String[] permissionArray = permission.split(",");
            for (String p : permissionArray) {
                if (!permissions.contains(p.trim())) {
                    throw new BusinessException("用户无权限");
                }
            }
        } else {
            // 需要任意一个权限
            String[] permissionArray = permission.split(",");
            boolean hasPermission = false;
            for (String p : permissionArray) {
                if (permissions.contains(p.trim())) {
                    hasPermission = true;
                    break;
                }
            }
            if (!hasPermission) {
                throw new BusinessException("用户无权限");
            }
        }

        return point.proceed();
    }
} 