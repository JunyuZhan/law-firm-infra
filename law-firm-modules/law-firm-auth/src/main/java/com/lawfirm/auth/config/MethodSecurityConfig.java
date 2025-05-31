package com.lawfirm.auth.config;

import com.lawfirm.auth.security.evaluator.BusinessPermissionEvaluator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * 方法安全配置类
 * <p>
 * 配置Spring Security方法级别的安全控制，包括：
 * - 自定义PermissionEvaluator
 * - 方法安全表达式处理器
 * </p>
 * 
 * <p>该配置使得@PreAuthorize注解中的hasPermission表达式能够正确工作</p>
 * 
 * @author law-firm-system
 * @since 1.0.0
 */
@Slf4j
@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
@ConditionalOnProperty(name = "law-firm.database.enabled", havingValue = "true", matchIfMissing = true)
public class MethodSecurityConfig {

    private final BusinessPermissionEvaluator businessPermissionEvaluator;

    /**
     * 配置方法安全表达式处理器
     * <p>
     * 注册自定义的PermissionEvaluator，使得@PreAuthorize注解中的hasPermission表达式
     * 能够使用我们自定义的权限检查逻辑。
     * </p>
     * 
     * @return 方法安全表达式处理器
     */
    @Bean("authMethodSecurityExpressionHandler")
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        log.info("配置方法安全表达式处理器，注册自定义PermissionEvaluator");
        
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(businessPermissionEvaluator);
        
        log.info("方法安全表达式处理器配置完成，hasPermission表达式现在可以正常工作");
        return expressionHandler;
    }
}