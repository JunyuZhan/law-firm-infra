package com.lawfirm.core.message.sender;

import com.lawfirm.common.message.WechatService;
import com.lawfirm.core.message.config.MessageConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 微信消息发送实现
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WechatSender implements WechatService {

    private final RestTemplate restTemplate;
    private final MessageConfig messageConfig;
    
    private static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    @Override
    public void send(String openId, String templateId, String url, Object data) {
        try {
            // 1. 获取access token
            String accessToken = getAccessToken();
            
            // 2. 构建请求参数
            Map<String, Object> params = Map.of(
                "touser", openId,
                "template_id", templateId,
                "url", url,
                "data", data
            );
            
            // 3. 发送请求
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(params, headers);
            
            restTemplate.postForObject(SEND_TEMPLATE_URL + accessToken, request, Map.class);
            
            log.info("Wechat message sent successfully: openId={}, templateId={}", openId, templateId);
        } catch (Exception e) {
            log.error("Failed to send wechat message", e);
            throw new RuntimeException("Failed to send wechat message", e);
        }
    }
    
    private String getAccessToken() {
        String url = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
                messageConfig.getWechat().getAppId(),
                messageConfig.getWechat().getAppSecret());
        
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response != null && response.containsKey("access_token")) {
            return (String) response.get("access_token");
        }
        
        throw new RuntimeException("Failed to get wechat access token");
    }
} 