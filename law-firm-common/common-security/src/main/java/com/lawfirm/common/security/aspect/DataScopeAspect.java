package com.lawfirm.common.security.aspect;

import com.lawfirm.common.security.annotation.DataScope;
import com.lawfirm.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.Signature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * 数据权限处理
 */
@Slf4j
@Aspect
@Component
public class DataScopeAspect {
    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(com.lawfirm.common.security.annotation.DataScope)")
    public void dataScopePointCut() {
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) throws Throwable {
        handleDataScope(point);
    }

    protected void handleDataScope(final JoinPoint joinPoint) {
        // 获得注解
        DataScope controllerDataScope = getAnnotationLog(joinPoint);
        if (controllerDataScope == null) {
            return;
        }
        // 获取当前的用户
        Long currentUserId = SecurityUtils.getUserId();
        if (currentUserId != null) {
            // 如果是超级管理员，则不过滤数据
            if (SecurityUtils.isAdmin()) {
                dataScopeFilter(joinPoint, currentUserId, controllerDataScope.deptAlias(),
                        controllerDataScope.userAlias(), DATA_SCOPE_ALL);
            } else {
                dataScopeFilter(joinPoint, currentUserId, controllerDataScope.deptAlias(),
                        controllerDataScope.userAlias(), DATA_SCOPE_SELF);
            }
        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param userId 用户ID
     * @param deptAlias 部门别名
     * @param userAlias 用户别名
     * @param dataScope 数据权限
     */
    public static void dataScopeFilter(JoinPoint joinPoint, Long userId, String deptAlias, String userAlias, String dataScope) {
        StringBuilder sqlString = new StringBuilder();

        // 全部数据权限
        if (DATA_SCOPE_ALL.equals(dataScope)) {
            sqlString = new StringBuilder();
        }
        // 仅本人数据权限
        else if (DATA_SCOPE_SELF.equals(dataScope)) {
            if (StringUtils.hasText(userAlias)) {
                sqlString.append(String.format(" OR %s.user_id = %d ", userAlias, userId));
            } else {
                sqlString.append(String.format(" OR create_by = %d ", userId));
            }
        }
        
        // 保存数据库sql
        log.info("拼接的数据权限SQL:{}", sqlString.toString());
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private DataScope getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(DataScope.class);
        }
        return null;
    }
} 