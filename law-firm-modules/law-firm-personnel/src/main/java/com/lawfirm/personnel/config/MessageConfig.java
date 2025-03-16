package com.lawfirm.personnel.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * 人事模块消息配置
 * 配置模块间的消息集成
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "personnel.notification", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MessageConfig {

    /**
     * 邮件通知主题前缀
     */
    public static final String EMAIL_SUBJECT_PREFIX = "[律所人事] ";
    
    /**
     * 员工入职通知标题
     */
    public static final String NEW_EMPLOYEE_SUBJECT = EMAIL_SUBJECT_PREFIX + "新员工入职通知";
    
    /**
     * 员工离职通知标题
     */
    public static final String EMPLOYEE_RESIGN_SUBJECT = EMAIL_SUBJECT_PREFIX + "员工离职通知";
    
    /**
     * 合同到期提醒标题
     */
    public static final String CONTRACT_EXPIRY_SUBJECT = EMAIL_SUBJECT_PREFIX + "合同到期提醒";
    
    /**
     * 生日祝福标题
     */
    public static final String BIRTHDAY_WISHES_SUBJECT = EMAIL_SUBJECT_PREFIX + "生日祝福";
    
    /**
     * 消息模板配置
     */
    @Bean
    public void initMessageTemplates() {
        log.info("初始化人事模块消息模板");
        
        // 在实际实现中，这里会注册消息模板或加载消息模板配置
        // 例如：将模板注册到消息服务
        // messageTemplateService.registerTemplate("employee_welcome", "欢迎加入我们的团队，{name}！");
    }
} 