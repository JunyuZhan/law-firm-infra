package com.lawfirm.personnel.config;

import com.lawfirm.core.message.config.MessageConfig;
import com.lawfirm.core.message.service.MessageTemplateService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;
import jakarta.annotation.PostConstruct;

/**
 * 人事模块消息配置
 * 配置模块间的消息集成，扩展核心消息模块功能
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = false)
@Import(MessageConfig.class) // 引入核心消息配置
public class PersonnelMessageConfig {

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
    
    private final MessageTemplateService messageTemplateService;
    
    /**
     * 构造函数注入消息模板服务
     */
    public PersonnelMessageConfig(MessageTemplateService messageTemplateService) {
        this.messageTemplateService = messageTemplateService;
    }
    
    /**
     * 初始化人事模块消息模板
     */
    @PostConstruct
    public void initPersonnelMessageTemplates() {
        log.info("初始化人事模块消息模板");
        
        // 使用核心消息模块的模板服务注册人事模块特有的模板
        messageTemplateService.registerTemplate("employee_welcome", "欢迎加入我们的团队，{name}！");
        messageTemplateService.registerTemplate("employee_resign", "感谢您在公司的贡献，祝您未来一切顺利！");
        messageTemplateService.registerTemplate("contract_expiry", "您的合同将于{date}到期，请注意办理相关手续。");
        messageTemplateService.registerTemplate("birthday_wishes", "祝您生日快乐，工作顺利！");
    }
} 