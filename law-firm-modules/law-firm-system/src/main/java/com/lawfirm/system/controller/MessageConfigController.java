package com.lawfirm.system.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.system.constant.SystemConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.lawfirm.core.message.handler.NotificationHandler.NotificationService;
import com.lawfirm.model.message.entity.base.BaseNotify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息服务配置管理控制器
 * 专门管理消息服务相关的配置和测试
 */
@Slf4j
@Tag(name = "消息服务配置管理", description = "管理邮件、短信等消息服务的配置和测试")
@RestController("MessageConfigController")
@RequestMapping(SystemConstants.API_MESSAGE_PREFIX + "/config")
@Validated
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class MessageConfigController extends BaseController {

    private final NotificationService emailService;
    private final NotificationService smsService;

    @Autowired(required = false)
    private JavaMailSender mailSender;

    // 使用构造函数注入，并明确指定Bean
    public MessageConfigController(
        @Qualifier("emailNotificationService") NotificationService emailService,
        @Qualifier("smsNotificationService") NotificationService smsService
    ) {
        this.emailService = emailService;
        this.smsService = smsService;
    }

    /**
     * 获取消息服务配置概览
     */
    @Operation(summary = "获取消息服务配置概览", description = "查看各消息服务的启用状态和基本配置信息")
    @GetMapping("/overview")
    @PreAuthorize("hasAuthority('system:message:config:view')")
    public CommonResult<MessageServiceOverview> getServiceOverview() {
        try {
            MessageServiceOverview overview = new MessageServiceOverview();
            
            // 检查邮件服务状态
            overview.setEmailEnabled(mailSender != null);
            overview.setEmailStatus(mailSender != null ? "已配置" : "未配置");
            
            // 检查短信服务状态
            overview.setSmsEnabled(true); // 短信服务已实现（模拟模式）
            overview.setSmsStatus("已实现（模拟模式）");
            
            // 检查其他服务
            overview.setInternalEnabled(true);
            overview.setWebsocketEnabled(true);
            
            return CommonResult.success(overview);
        } catch (Exception e) {
            log.error("获取消息服务配置概览失败", e);
            return CommonResult.error("获取失败: " + e.getMessage());
        }
    }

    /**
     * 测试邮件服务配置
     */
    @Operation(summary = "测试邮件服务", description = "发送测试邮件验证邮件服务配置是否正确")
    @PostMapping("/test/email")
    @PreAuthorize("hasAuthority('system:message:config:test')")
    @Log(title = "消息配置", businessType = "OTHER")
    public CommonResult<String> testEmailService(@Valid @RequestBody EmailTestRequest request) {
        try {
            BaseNotify testNotification = new BaseNotify();
            testNotification.setTitle(request.getSubject());
            testNotification.setContent(request.getContent());
            testNotification.setReceivers(Arrays.asList(request.getToEmail()));

            emailService.send(testNotification);
            
            return CommonResult.success("邮件测试发送成功");
        } catch (Exception e) {
            log.error("邮件服务测试失败", e);
            return CommonResult.error("邮件测试失败: " + e.getMessage());
        }
    }

    /**
     * 测试短信服务配置
     */
    @Operation(summary = "测试短信服务", description = "发送测试短信验证短信服务配置是否正确")
    @PostMapping("/test/sms")
    @PreAuthorize("hasAuthority('system:message:config:test')")
    @Log(title = "消息配置", businessType = "OTHER")
    public CommonResult<String> testSmsService(@Valid @RequestBody SmsTestRequest request) {
        try {
            BaseNotify testNotification = new BaseNotify();
            testNotification.setTitle("测试短信");
            testNotification.setContent(request.getContent());
            testNotification.setReceivers(Arrays.asList(request.getPhoneNumber()));

            smsService.send(testNotification);
            
            return CommonResult.success("短信测试发送成功（当前为模拟模式）");
        } catch (Exception e) {
            log.error("短信服务测试失败", e);
            return CommonResult.error("短信测试失败: " + e.getMessage());
        }
    }

    /**
     * 获取邮件服务配置建议
     */
    @Operation(summary = "获取邮件配置建议", description = "根据邮件服务商提供配置建议")
    @GetMapping("/suggestions/email")
    @PreAuthorize("hasAuthority('system:message:config:view')")
    public CommonResult<List<EmailConfigSuggestion>> getEmailConfigSuggestions() {
        try {
            List<EmailConfigSuggestion> suggestions = Arrays.asList(
                new EmailConfigSuggestion("QQ邮箱", "smtp.qq.com", 587, true, "需要开启SMTP服务并获取授权码"),
                new EmailConfigSuggestion("163邮箱", "smtp.163.com", 25, false, "需要开启SMTP服务"),
                new EmailConfigSuggestion("Gmail", "smtp.gmail.com", 587, true, "需要开启两步验证并使用应用专用密码"),
                new EmailConfigSuggestion("腾讯企业邮箱", "smtp.exmail.qq.com", 465, true, "企业邮箱，推荐用于生产环境"),
                new EmailConfigSuggestion("阿里云邮箱", "smtp.mxhichina.com", 465, true, "企业邮箱服务")
            );
            
            return CommonResult.success(suggestions);
        } catch (Exception e) {
            log.error("获取邮件配置建议失败", e);
            return CommonResult.error("获取失败: " + e.getMessage());
        }
    }

    /**
     * 获取短信服务商配置建议
     */
    @Operation(summary = "获取短信服务商建议", description = "获取主流短信服务商的配置信息和价格对比")
    @GetMapping("/suggestions/sms")
    @PreAuthorize("hasAuthority('system:message:config:view')")
    public CommonResult<List<SmsProviderSuggestion>> getSmsProviderSuggestions() {
        try {
            List<SmsProviderSuggestion> suggestions = Arrays.asList(
                new SmsProviderSuggestion("阿里云短信", "dysmsapi.aliyuncs.com", "0.045元/条", "已集成SDK，推荐使用"),
                new SmsProviderSuggestion("腾讯云短信", "sms.tencentcloudapi.com", "0.055元/条", "需要集成SDK"),
                new SmsProviderSuggestion("华为云短信", "smsapi.cn-north-4.myhuaweicloud.com", "0.040元/条", "需要集成SDK"),
                new SmsProviderSuggestion("网易云信", "api.netease.im", "0.050元/条", "需要集成SDK")
            );
            
            return CommonResult.success(suggestions);
        } catch (Exception e) {
            log.error("获取短信服务商建议失败", e);
            return CommonResult.error("获取失败: " + e.getMessage());
        }
    }

    /**
     * 验证邮件配置
     */
    @Operation(summary = "验证邮件配置", description = "验证邮件服务器配置的有效性")
    @PostMapping("/validate/email")
    @PreAuthorize("hasAuthority('system:message:config:test')")
    public CommonResult<Map<String, Object>> validateEmailConfig(@Valid @RequestBody EmailConfigValidation config) {
        try {
            Map<String, Object> result = new HashMap<>();
            result.put("host", config.getHost());
            result.put("port", config.getPort());
            result.put("ssl", config.isSsl());
            
            // 这里可以添加实际的SMTP连接测试逻辑
            result.put("status", "配置格式正确");
            result.put("suggestion", "建议先测试发送邮件验证连接");
            
            return CommonResult.success(result);
        } catch (Exception e) {
            log.error("验证邮件配置失败", e);
            return CommonResult.error("验证失败: " + e.getMessage());
        }
    }

    // ================================ 请求实体类 ================================

    @Data
    public static class EmailTestRequest {
        @NotBlank(message = "收件人邮箱不能为空")
        @Email(message = "邮箱格式不正确")
        private String toEmail;

        @NotBlank(message = "邮件主题不能为空")
        private String subject = "系统邮件服务测试";

        @NotBlank(message = "邮件内容不能为空")
        private String content = "这是一封测试邮件，用于验证邮件服务配置是否正确。如果您收到这封邮件，说明邮件服务已正常工作。";
    }

    @Data
    public static class SmsTestRequest {
        @NotBlank(message = "手机号不能为空")
        private String phoneNumber;

        @NotBlank(message = "短信内容不能为空")
        private String content = "【律师事务所】您好，这是一条测试短信，用于验证短信服务配置。";
    }

    @Data
    public static class EmailConfigValidation {
        @NotBlank(message = "SMTP服务器地址不能为空")
        private String host;

        private Integer port = 587;

        private boolean ssl = true;

        @NotBlank(message = "用户名不能为空")
        private String username;

        @NotBlank(message = "密码不能为空")
        private String password;
    }

    // ================================ 响应实体类 ================================

    @Data
    public static class MessageServiceOverview {
        private boolean emailEnabled;
        private String emailStatus;
        private boolean smsEnabled;
        private String smsStatus;
        private boolean internalEnabled;
        private boolean websocketEnabled;
    }

    @Data
    public static class EmailConfigSuggestion {
        private String provider;
        private String host;
        private Integer port;
        private boolean ssl;
        private String description;

        public EmailConfigSuggestion(String provider, String host, Integer port, boolean ssl, String description) {
            this.provider = provider;
            this.host = host;
            this.port = port;
            this.ssl = ssl;
            this.description = description;
        }
    }

    @Data
    public static class SmsProviderSuggestion {
        private String provider;
        private String endpoint;
        private String price;
        private String description;

        public SmsProviderSuggestion(String provider, String endpoint, String price, String description) {
            this.provider = provider;
            this.endpoint = endpoint;
            this.price = price;
            this.description = description;
        }
    }
} 