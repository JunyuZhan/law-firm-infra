package com.lawfirm.core.message.sender;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.lawfirm.common.message.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 短信发送实现（阿里云）
 */
@Slf4j
@Component
public class SmsSender implements SmsService {

    private final Client smsClient;

    public SmsSender(Client smsClient) {
        this.smsClient = smsClient;
    }

    @Override
    public void send(String mobile, String content, String templateCode, Object... params) {
        try {
            SendSmsRequest request = new SendSmsRequest()
                    .setPhoneNumbers(mobile)
                    .setTemplateCode(templateCode)
                    .setTemplateParam(String.format("{\"code\":\"%s\"}", params[0]));
            smsClient.sendSms(request);
        } catch (Exception e) {
            log.error("Failed to send SMS", e);
            throw new RuntimeException("Failed to send SMS", e);
        }
    }

    @Override
    public void sendEmail(String to, String subject, String content, boolean isHtml) {
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