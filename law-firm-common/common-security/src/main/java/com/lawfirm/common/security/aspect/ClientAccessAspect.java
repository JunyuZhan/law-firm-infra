package com.lawfirm.common.security.aspect;

import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.security.annotation.ClientAccess;
import com.lawfirm.common.security.annotation.Logical;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 客户端访问控制切面
 */
@Slf4j
@Aspect
@Component
public class ClientAccessAspect {

    /**
     * 客户端类型请求头
     */
    private static final String CLIENT_TYPE_HEADER = "Client-Type";

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.lawfirm.common.security.annotation.ClientAccess)")
    public void clientAccessPointCut() {
    }

    @Before("clientAccessPointCut()")
    public void doBefore(JoinPoint point) {
        handleClientAccess(point);
    }

    protected void handleClientAccess(final JoinPoint joinPoint) {
        // 获得注解
        ClientAccess clientAccess = getAnnotation(joinPoint);
        if (clientAccess == null) {
            return;
        }

        // 获取请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new BusinessException("无法获取请求信息");
        }

        HttpServletRequest request = attributes.getRequest();
        String clientType = request.getHeader(CLIENT_TYPE_HEADER);

        // 验证客户端类型
        String[] allowedClients = clientAccess.value();
        if (allowedClients.length > 0) {
            boolean hasAccess = false;
            if (Logical.AND.equals(clientAccess.logical())) {
                // 需要满足所有客户端类型
                hasAccess = Arrays.stream(allowedClients).allMatch(client -> client.equals(clientType));
            } else {
                // 满足任一客户端类型
                hasAccess = Arrays.stream(allowedClients).anyMatch(client -> client.equals(clientType));
            }

            if (!hasAccess) {
                log.warn("客户端类型[{}]无权访问", clientType);
                throw new BusinessException("当前客户端无权访问");
            }
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private ClientAccess getAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        if (method != null) {
            return method.getAnnotation(ClientAccess.class);
        }
        return null;
    }
} 