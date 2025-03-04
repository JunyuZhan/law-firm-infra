package com.lawfirm.core.audit.aspect;

import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.core.audit.annotation.AuditField;
import com.lawfirm.core.audit.util.FieldChangeUtils;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Arrays;

/**
 * 字段变更审计切面
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AuditFieldAspect {

    private final AuditService auditService;

    @Around("@annotation(auditField)")
    public Object around(ProceedingJoinPoint point, AuditField auditField) throws Throwable {
        // 检查是否有权限访问
        MethodSignature signature = (MethodSignature) point.getSignature();
        RequiresPermissions permissions = signature.getMethod().getAnnotation(RequiresPermissions.class);
        if (permissions != null && !checkPermissions(permissions.value())) {
            throw new SecurityException("无权访问");
        }

        // 获取方法参数
        Object[] args = point.getArgs();
        if (args == null || args.length == 0) {
            return point.proceed();
        }

        // 获取目标对象（第一个参数）
        Object target = args[0];
        
        // 执行目标方法
        Object result = point.proceed();

        // 比较字段变更
        Map<String, Object[]> changes = FieldChangeUtils.compareFields(target, result, auditField.fields());
        
        if (!changes.isEmpty()) {
            // 构建审计日志
            AuditLogDTO logDTO = new AuditLogDTO()
                    .setDescription(auditField.description())
                    .setBeforeData(changes.values().stream()
                            .map(values -> String.valueOf(values[0]))
                            .reduce((a, b) -> a + "," + b)
                            .orElse(""))
                    .setAfterData(changes.values().stream()
                            .map(values -> String.valueOf(values[1]))
                            .reduce((a, b) -> a + "," + b)
                            .orElse(""))
                    .setChangedFields(String.join(",", changes.keySet()));

            // 记录审计日志
            if (auditField.async()) {
                auditService.logAsync(logDTO);
            } else {
                auditService.log(logDTO);
            }
        }

        return result;
    }

    /**
     * 检查用户是否拥有所需权限
     */
    private boolean checkPermissions(String[] requiredPerms) {
        if (requiredPerms == null || requiredPerms.length == 0) {
            return true;
        }
        return Arrays.stream(requiredPerms)
                .allMatch(SecurityUtils::hasPermission);
    }
} 