package com.lawfirm.core.audit.aspect;

import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.common.log.annotation.AuditLog;
import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.model.auth.service.PermissionService;
import com.lawfirm.model.auth.vo.PermissionVO;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 审计日志切面
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "law-firm.core.audit", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnBean(name = "coreAuditServiceImpl")
public class AuditLogAspect {

    private final AuditService auditService;
    private final PermissionService permissionService;

    @Autowired
    public AuditLogAspect(@Qualifier("coreAuditServiceImpl") AuditService auditService, 
                          PermissionService permissionService) {
        this.auditService = auditService;
        this.permissionService = permissionService;
    }

    @Around("@annotation(auditLog)")
    public Object around(ProceedingJoinPoint point, AuditLog auditLog) throws Throwable {
        // 检查是否有权限访问
        MethodSignature signature = (MethodSignature) point.getSignature();
        RequiresPermissions permissions = signature.getMethod().getAnnotation(RequiresPermissions.class);
        if (permissions != null) {
            Long userId = SecurityUtils.getUserId();
            List<PermissionVO> userPerms = permissionService.listPermissionsByUserId(userId);
            if (!checkPermissions(userPerms, permissions.value())) {
                throw new SecurityException("无权访问");
            }
        }

        Object result = null;
        try {
            // 执行目标方法
            result = point.proceed();
            
            // 构建审计日志
            AuditLogDTO logDTO = buildAuditLog(point, auditLog, result, null);
            
            // 记录审计日志
            if (auditLog.async()) {
                auditService.logAsync(logDTO);
            } else {
                auditService.log(logDTO);
            }
            
            return result;
        } catch (Throwable e) {
            // 异常情况下也记录审计日志
            AuditLogDTO logDTO = buildAuditLog(point, auditLog, null, e);
            auditService.log(logDTO);
            throw e;
        }
    }

    /**
     * 检查用户是否拥有所需权限
     */
    private boolean checkPermissions(List<PermissionVO> userPerms, String[] requiredPerms) {
        if (requiredPerms == null || requiredPerms.length == 0) {
            return true;
        }
        Set<String> userPermCodes = userPerms.stream()
            .map(PermissionVO::getCode)
            .collect(Collectors.toSet());
        return Arrays.stream(requiredPerms)
            .allMatch(userPermCodes::contains);
    }

    private AuditLogDTO buildAuditLog(ProceedingJoinPoint point, AuditLog auditLog, 
                                    Object result, Throwable error) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        
        AuditLogDTO dto = new AuditLogDTO();
        dto.setModule(auditLog.module());
        dto.setOperateType(OperateTypeEnum.valueOf(auditLog.operateType()));
        dto.setBusinessType(BusinessTypeEnum.valueOf(auditLog.businessType()));
        dto.setDescription(auditLog.description());
        
        // 设置操作人信息
        dto.setOperatorId(SecurityUtils.getUserId());
        dto.setOperatorName(SecurityUtils.getUsername());
        
        // 设置操作时间
        dto.setOperationTime(LocalDateTime.now());
        
        // 设置操作状态
        if (error != null) {
            dto.setStatus(1);  // 异常
            dto.setErrorMsg(error.getMessage());
        } else {
            dto.setStatus(0);  // 正常
        }
        
        // 设置数据变更信息
        if (auditLog.logParams()) {
            dto.setBeforeData(Arrays.toString(point.getArgs()));
        }
        if (auditLog.logResult() && result != null) {
            dto.setAfterData(result.toString());
        }
        
        return dto;
    }
} 