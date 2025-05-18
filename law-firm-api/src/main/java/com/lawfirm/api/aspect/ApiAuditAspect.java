package com.lawfirm.api.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.common.web.utils.IpUtils;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import com.lawfirm.model.log.service.AuditService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;

/**
 * API访问审计切面
 * 集成core-audit模块功能，记录API访问日志
 */
@Aspect
@Component("apiAuditAspect")
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "law-firm.api.audit.enabled", havingValue = "true", matchIfMissing = true)
public class ApiAuditAspect {

    @Qualifier("coreAuditServiceImpl")
    private final AuditService auditService;
    private final ObjectMapper objectMapper;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    // 排除的URL路径
    private static final String[] EXCLUDE_PATHS = {
            "/static/**", 
            "/webjars/**", 
            "/error/**", 
            "/favicon.ico", 
            "/actuator/**"
    };

    // 敏感字段列表
    private static final String[] SENSITIVE_FIELDS = {
            "password", 
            "secret", 
            "token", 
            "creditCard", 
            "idCard"
    };

    /**
     * 定义API控制器切点
     */
    @Pointcut("execution(* com.lawfirm.api.controller..*.*(..))")
    public void apiAuditPointcut() {
    }

    /**
     * 环绕通知，记录API访问日志
     */
    @Around("apiAuditPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return joinPoint.proceed();
        }

        HttpServletRequest request = attributes.getRequest();
        String requestURI = request.getRequestURI();

        // 检查是否需要排除该路径
        if (isPathExcluded(requestURI)) {
            return joinPoint.proceed();
        }

        // 获取控制器和方法信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = method.getName();

        // 创建审计日志对象
        AuditLogDTO auditLog = new AuditLogDTO();
        auditLog.setModule(className.replace("Controller", ""));
        auditLog.setDescription(className + "#" + methodName);
        auditLog.setOperateType(getOperationType(methodName, request.getMethod()));
        auditLog.setBusinessType(BusinessTypeEnum.OTHER); // 默认为其他业务类型
        auditLog.setOperationTime(LocalDateTime.now());
        auditLog.setOperatorIp(IpUtils.getIpAddr(request));

        // 设置操作人信息
        try {
            auditLog.setOperatorId(SecurityUtils.getUserId());
            auditLog.setOperatorName(SecurityUtils.getUsername());
        } catch (Exception e) {
            // 未登录或获取用户信息失败，使用默认值
            auditLog.setOperatorName("anonymous");
        }

        // 记录请求参数
        try {
            Object[] args = joinPoint.getArgs();
            // 过滤掉文件上传等二进制参数
            List<Object> filteredArgs = new ArrayList<>();
            for (Object arg : args) {
                if (!(arg instanceof MultipartFile)) {
                    filteredArgs.add(arg);
                } else {
                    filteredArgs.add("[文件上传]");
                }
            }
            // 记录请求参数
            auditLog.setBeforeData(objectMapper.writeValueAsString(filteredArgs));
        } catch (Exception e) {
            auditLog.setBeforeData("[参数序列化异常]");
            log.warn("请求参数序列化失败", e);
        }

        // 开始时间
        long startTime = System.currentTimeMillis();

        try {
            // 执行目标方法
            Object result = joinPoint.proceed();

            // 计算执行时间
            long executeTime = System.currentTimeMillis() - startTime;
            
            // 记录执行时间
            auditLog.setChangedFields("executeTime:" + executeTime + "ms");
            auditLog.setStatus(0); // 成功

            // 记录响应结果
            try {
                auditLog.setAfterData(objectMapper.writeValueAsString(result));
            } catch (Exception e) {
                auditLog.setAfterData("[响应结果序列化异常]");
                log.warn("响应结果序列化失败", e);
            }

            // 异步保存审计日志
            auditService.logAsync(auditLog);

            return result;
        } catch (Throwable e) {
            // 计算执行时间
            long executeTime = System.currentTimeMillis() - startTime;
            auditLog.setChangedFields("executeTime:" + executeTime + "ms");
            auditLog.setStatus(1); // 失败
            auditLog.setErrorMsg(e.getMessage());

            // 记录异常堆栈
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            auditLog.setAfterData(sw.toString());

            // 异步保存审计日志
            auditService.logAsync(auditLog);

            throw e;
        }
    }

    /**
     * 检查路径是否需要排除
     */
    private boolean isPathExcluded(String path) {
        for (String pattern : EXCLUDE_PATHS) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据方法名和请求方法获取操作类型
     */
    private OperateTypeEnum getOperationType(String methodName, String requestMethod) {
        if (methodName.startsWith("get") || methodName.startsWith("query") || 
            methodName.startsWith("find") || methodName.startsWith("list") || 
            methodName.startsWith("select") || methodName.startsWith("count") || 
            methodName.startsWith("check") || "GET".equalsIgnoreCase(requestMethod)) {
            return OperateTypeEnum.QUERY;
        } else if (methodName.startsWith("add") || methodName.startsWith("save") || 
                  methodName.startsWith("insert") || methodName.startsWith("create") || 
                  "POST".equalsIgnoreCase(requestMethod)) {
            return OperateTypeEnum.CREATE;
        } else if (methodName.startsWith("update") || methodName.startsWith("modify") || 
                  methodName.startsWith("edit") || "PUT".equalsIgnoreCase(requestMethod)) {
            return OperateTypeEnum.UPDATE;
        } else if (methodName.startsWith("delete") || methodName.startsWith("remove") || 
                  "DELETE".equalsIgnoreCase(requestMethod)) {
            return OperateTypeEnum.DELETE;
        } else if (methodName.startsWith("import")) {
            return OperateTypeEnum.IMPORT;
        } else if (methodName.startsWith("export")) {
            return OperateTypeEnum.EXPORT;
        } else {
            return OperateTypeEnum.OTHER;
        }
    }
} 