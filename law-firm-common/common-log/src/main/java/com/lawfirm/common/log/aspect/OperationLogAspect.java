package com.lawfirm.common.log.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.common.core.utils.IpUtils;
import com.lawfirm.common.core.utils.ServletUtils;
import com.lawfirm.common.log.annotation.OperationLog;
import com.lawfirm.common.log.domain.OperationLogDO;
import com.lawfirm.common.log.service.OperationLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 操作日志切面
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {

    private final OperationLogService operationLogService;
    private final ObjectMapper objectMapper;

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.lawfirm.common.log.annotation.OperationLog)")
    public void logPointcut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     * @param result    返回值
     */
    @AfterReturning(pointcut = "logPointcut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        handleLog(joinPoint, null, result);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e        异常
     */
    @AfterThrowing(value = "logPointcut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object result) {
        try {
            // 获取注解
            OperationLog operationLog = getAnnotationLog(joinPoint);
            if (operationLog == null) {
                return;
            }

            // 获取当前的用户（这里需要根据实际情况获取）
            // TODO: 获取当前用户信息
            Long userId = 0L;
            String username = "admin";

            // 日志实体
            OperationLogDO operLog = new OperationLogDO();
            
            // 请求的地址
            HttpServletRequest request = ServletUtils.getRequest();
            String ip = IpUtils.getIpAddr(request);
            operLog.setOperatorIp(ip);
            operLog.setOperationLocation(IpUtils.getRealAddressByIP(ip));
            operLog.setRequestUrl(request.getRequestURI());
            operLog.setRequestMethod(request.getMethod());
            
            if (e != null) {
                operLog.setStatus(1);
                operLog.setErrorMsg(e.getMessage());
            } else {
                operLog.setStatus(0);
            }

            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");

            // 设置请求方式
            operLog.setRequestMethod(request.getMethod());

            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, operationLog, operLog, result);

            // 设置操作人员
            operLog.setOperatorId(userId);
            operLog.setOperatorName(username);
            operLog.setOperationTime(LocalDateTime.now());

            // 保存数据库
            operationLogService.saveOperationLog(operLog);
        } catch (Exception exp) {
            log.error("记录操作日志异常：{}", exp.getMessage());
        }
    }

    /**
     * 获取注解中对方法的描述信息
     */
    private void getControllerMethodDescription(JoinPoint joinPoint, OperationLog operationLog,
                                              OperationLogDO operLog, Object result) throws Exception {
        // 设置action动作
        operLog.setDescription(operationLog.description());
        // 设置操作类型
        operLog.setOperationType(operationLog.operationType());
        // 是否需要保存request，参数和值
        if (operationLog.saveRequestData()) {
            // 获取参数的信息
            setRequestValue(joinPoint, operLog);
        }
        // 是否需要保存response，参数和值
        if (operationLog.saveResponseData() && result != null) {
            operLog.setResponseResult(objectMapper.writeValueAsString(result));
        }
    }

    /**
     * 获取请求的参数，放到log中
     */
    private void setRequestValue(JoinPoint joinPoint, OperationLogDO operLog) throws Exception {
        String requestMethod = operLog.getRequestMethod();
        if (requestMethod != null && requestMethod.length() > 0) {
            String params = Arrays.toString(joinPoint.getArgs());
            operLog.setRequestParams(params);
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private OperationLog getAnnotationLog(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(OperationLog.class);
        }
        return null;
    }
} 