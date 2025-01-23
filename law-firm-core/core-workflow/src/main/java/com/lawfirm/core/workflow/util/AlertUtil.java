package com.lawfirm.core.workflow.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 告警通知工具类
 */
@Slf4j
@Component
public class AlertUtil {

    private final MongoTemplate mongoTemplate;
    private final JavaMailSender mailSender;
    
    @Autowired(required = false)
    public AlertUtil(MongoTemplate mongoTemplate, JavaMailSender mailSender) {
        this.mongoTemplate = mongoTemplate;
        this.mailSender = mailSender;
    }
    
    /**
     * 发送告警邮件
     *
     * @param subject 主题
     * @param content 内容
     * @param to 收件人
     */
    public void sendAlertEmail(String subject, String content, String... to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            
            mailSender.send(message);
            
            saveAlertRecord("EMAIL", subject, content, String.join(",", to));
            
            log.info("Alert email sent: subject={}, to={}", subject, String.join(",", to));
        } catch (Exception e) {
            log.error("Failed to send alert email", e);
        }
    }
    
    /**
     * 发送系统告警
     *
     * @param title 标题
     * @param content 内容
     * @param level 级别(INFO/WARN/ERROR)
     */
    public void sendSystemAlert(String title, String content, String level) {
        Map<String, Object> alert = new HashMap<>();
        alert.put("type", "SYSTEM");
        alert.put("title", title);
        alert.put("content", content);
        alert.put("level", level);
        alert.put("timestamp", LocalDateTime.now());
        
        try {
            mongoTemplate.save(alert, "system_alerts");
            log.info("System alert saved: title={}, level={}", title, level);
        } catch (Exception e) {
            log.error("Failed to save system alert", e);
        }
    }
    
    /**
     * 发送业务告警
     *
     * @param title 标题
     * @param content 内容
     * @param businessType 业务类型
     * @param businessId 业务ID
     */
    public void sendBusinessAlert(String title, String content, String businessType, String businessId) {
        Map<String, Object> alert = new HashMap<>();
        alert.put("type", "BUSINESS");
        alert.put("title", title);
        alert.put("content", content);
        alert.put("businessType", businessType);
        alert.put("businessId", businessId);
        alert.put("timestamp", LocalDateTime.now());
        
        try {
            mongoTemplate.save(alert, "business_alerts");
            log.info("Business alert saved: title={}, businessType={}, businessId={}", 
                    title, businessType, businessId);
        } catch (Exception e) {
            log.error("Failed to save business alert", e);
        }
    }
    
    /**
     * 保存告警记录
     */
    private void saveAlertRecord(String type, String title, String content, String target) {
        Map<String, Object> record = new HashMap<>();
        record.put("type", type);
        record.put("title", title);
        record.put("content", content);
        record.put("target", target);
        record.put("timestamp", LocalDateTime.now());
        
        try {
            mongoTemplate.save(record, "alert_records");
        } catch (Exception e) {
            log.error("Failed to save alert record", e);
        }
    }
} 