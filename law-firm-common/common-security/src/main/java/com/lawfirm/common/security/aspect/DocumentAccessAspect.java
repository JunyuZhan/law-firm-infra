package com.lawfirm.common.security.aspect;

import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.security.annotation.DocumentAccess;
import com.lawfirm.common.security.annotation.Logical;
import com.lawfirm.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 文档访问控制切面
 */
@Slf4j
@Aspect
@Component
public class DocumentAccessAspect {

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.lawfirm.common.security.annotation.DocumentAccess)")
    public void documentAccessPointCut() {
    }

    @Before("documentAccessPointCut()")
    public void doBefore(JoinPoint point) {
        handleDocumentAccess(point);
    }

    protected void handleDocumentAccess(final JoinPoint joinPoint) {
        // 获得注解
        DocumentAccess documentAccess = getAnnotation(joinPoint);
        if (documentAccess == null) {
            return;
        }

        // 获取当前用户ID
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            throw new BusinessException("用户未登录");
        }

        // 如果是管理员，则不进行文档访问控制
        if (SecurityUtils.isAdmin()) {
            return;
        }

        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        String documentType = getDocumentType(args);
        String operation = getOperation(args);

        // 验证文档类型
        String[] allowedTypes = documentAccess.types();
        if (allowedTypes.length > 0) {
            boolean hasTypeAccess = false;
            if (Logical.AND.equals(documentAccess.logical())) {
                hasTypeAccess = Arrays.stream(allowedTypes).allMatch(type -> type.equals(documentType));
            } else {
                hasTypeAccess = Arrays.stream(allowedTypes).anyMatch(type -> type.equals(documentType));
            }

            if (!hasTypeAccess) {
                log.warn("用户[{}]无权访问文档类型[{}]", userId, documentType);
                throw new BusinessException("无权访问该类型的文档");
            }
        }

        // 验证操作类型
        String[] allowedOperations = documentAccess.operations();
        if (allowedOperations.length > 0) {
            boolean hasOperationAccess = false;
            if (Logical.AND.equals(documentAccess.logical())) {
                hasOperationAccess = Arrays.stream(allowedOperations).allMatch(op -> op.equals(operation));
            } else {
                hasOperationAccess = Arrays.stream(allowedOperations).anyMatch(op -> op.equals(operation));
            }

            if (!hasOperationAccess) {
                log.warn("用户[{}]无权进行操作[{}]", userId, operation);
                throw new BusinessException("无权进行该操作");
            }
        }

        // 检查文档所有者
        if (documentAccess.checkOwner()) {
            Long documentOwnerId = getDocumentOwnerId(args);
            if (!userId.equals(documentOwnerId)) {
                log.warn("用户[{}]不是文档所有者[{}]", userId, documentOwnerId);
                throw new BusinessException("您不是该文档的所有者");
            }
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private DocumentAccess getAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        if (method != null) {
            return method.getAnnotation(DocumentAccess.class);
        }
        return null;
    }

    /**
     * 从方法参数中获取文档类型
     */
    private String getDocumentType(Object[] args) {
        // TODO: 根据实际业务逻辑实现
        return "default";
    }

    /**
     * 从方法参数中获取操作类型
     */
    private String getOperation(Object[] args) {
        // TODO: 根据实际业务逻辑实现
        return "read";
    }

    /**
     * 从方法参数中获取文档所有者ID
     */
    private Long getDocumentOwnerId(Object[] args) {
        // TODO: 根据实际业务逻辑实现
        return 0L;
    }
} 