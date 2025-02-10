package com.lawfirm.core.message.sender;

import com.lawfirm.common.message.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

/**
 * 邮件发送实现
 */
@Slf4j
@Component
public class EmailSender implements EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Override
    public void send(String to, String subject, String content, boolean isHtml) {
        try {
            if (mailSender == null) {
                log.warn("JavaMailSender not configured");
                return;
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, isHtml);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send email", e);
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public void sendSms(String mobile, String content, String templateCode, Object... params) {
        // Not implemented
    }

    @Override
    public void sendWechat(String openId, String templateId, String url, Object data) {
        // Not implemented
    }

    @Override
    public void sendWebSocket(String userId, Object message) {
        // Not implemented
    }
} 