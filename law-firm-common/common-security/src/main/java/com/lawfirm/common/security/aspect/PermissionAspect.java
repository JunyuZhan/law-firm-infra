package com.lawfirm.common.security.aspect;

import cn.dev33.satoken.stp.StpUtil;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.security.annotation.Logical;
import com.lawfirm.common.security.annotation.RequiresPermissions;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 权限校验切面
 */
@Slf4j
@Aspect
@Component
public class PermissionAspect {

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.lawfirm.common.security.annotation.RequiresPermissions)")
    public void permissionPointCut() {
    }

    /**
     * 处理前拦截
     */
    @Before("permissionPointCut()")
    public void doBefore(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
        
        if (requiresPermissions != null) {
            String[] permissions = requiresPermissions.value();
            Logical logical = requiresPermissions.logical();
            
            if (permissions.length > 0) {
                try {
                    if (Logical.AND.equals(logical)) {
                        StpUtil.checkPermissionAnd(permissions);
                    } else {
                        StpUtil.checkPermissionOr(permissions);
                    }
                } catch (Exception e) {
                    log.error("权限校验异常", e);
                    throw new BusinessException("您没有访问权限");
                }
            }
        }
    }
} 