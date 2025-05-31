package com.lawfirm.system.controller;

import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.core.message.handler.NotificationHandler.NotificationService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.entity.base.BaseNotify;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import com.lawfirm.system.constant.SystemConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 消息控制器
 * 提供前端消息通知相关接口
 */
@Slf4j
@RestController
@RequestMapping(SystemConstants.API_MESSAGE_PREFIX)
@Validated
public class MessageController {

    private final MessageSender messageSender;
    private final NotificationService emailService;
    private final NotificationService smsService;
    private final NotificationService internalService;
    private final NotificationService webSocketService;

    public MessageController(
            @Qualifier("messageSender") MessageSender messageSender,
            @Qualifier("emailNotificationService") NotificationService emailService,
            @Qualifier("smsNotificationService") NotificationService smsService,
            @Qualifier("internalNotificationService") NotificationService internalService,
            @Qualifier("webSocketNotificationService") NotificationService webSocketService) {
        this.messageSender = messageSender;
        this.emailService = emailService;
        this.smsService = smsService;
        this.internalService = internalService;
        this.webSocketService = webSocketService;
    }

    /**
     * 发送站内消息
     */
    @PostMapping("/send/internal")
    public CommonResult<String> sendInternalMessage(@Valid @RequestBody InternalMessageRequest request) {
        try {
            log.info("发送站内消息: 标题={}, 接收者数量={}", request.getTitle(), request.getReceivers().size());

            BaseNotify notification = buildNotification(request);
            internalService.send(notification);

            return CommonResult.success("站内消息发送成功");
        } catch (Exception e) {
            log.error("发送站内消息失败", e);
            return CommonResult.error("发送失败: " + e.getMessage());
        }
    }

    /**
     * 发送邮件通知
     */
    @PostMapping("/send/email")
    public CommonResult<String> sendEmailNotification(@Valid @RequestBody EmailMessageRequest request) {
        try {
            log.info("发送邮件通知: 标题={}, 接收者数量={}", request.getTitle(), request.getReceivers().size());

            BaseNotify notification = buildNotification(request);
            emailService.send(notification);

            return CommonResult.success("邮件通知发送成功");
        } catch (Exception e) {
            log.error("发送邮件通知失败", e);
            return CommonResult.error("发送失败: " + e.getMessage());
        }
    }

    /**
     * 发送短信通知
     */
    @PostMapping("/send/sms")
    public CommonResult<String> sendSmsNotification(@Valid @RequestBody SmsMessageRequest request) {
        try {
            log.info("发送短信通知: 接收者数量={}", request.getReceivers().size());

            BaseNotify notification = buildNotification(request);
            smsService.send(notification);

            return CommonResult.success("短信通知发送成功");
        } catch (Exception e) {
            log.error("发送短信通知失败", e);
            return CommonResult.error("发送失败: " + e.getMessage());
        }
    }

    /**
     * 发送WebSocket实时通知
     */
    @PostMapping("/send/websocket")
    public CommonResult<String> sendWebSocketNotification(@Valid @RequestBody WebSocketMessageRequest request) {
        try {
            log.info("发送WebSocket通知: 标题={}, 接收者数量={}", request.getTitle(), request.getReceivers().size());

            BaseNotify notification = buildNotification(request);
            webSocketService.send(notification);

            return CommonResult.success("WebSocket通知发送成功");
        } catch (Exception e) {
            log.error("发送WebSocket通知失败", e);
            return CommonResult.error("发送失败: " + e.getMessage());
        }
    }

    /**
     * 发送多渠道通知（组合发送）
     */
    @PostMapping("/send/multi")
    public CommonResult<String> sendMultiChannelNotification(@Valid @RequestBody MultiChannelMessageRequest request) {
        try {
            log.info("发送多渠道通知: 标题={}, 渠道={}, 接收者数量={}", 
                request.getTitle(), request.getChannels(), request.getReceivers().size());

            BaseNotify notification = buildNotification(request);
            Map<String, String> results = new HashMap<>();
            int successCount = 0;

            // 根据选择的渠道发送
            for (String channel : request.getChannels()) {
                try {
                    String result = sendByChannel(channel, notification);
                    results.put(channel, result);
                    if ("成功".equals(result)) {
                        successCount++;
                    }
                } catch (Exception e) {
                    results.put(channel, "失败: " + e.getMessage());
                }
            }

            if (successCount > 0) {
                String message = String.format("多渠道通知发送完成，成功: %d个渠道", successCount);
                if (successCount < request.getChannels().size()) {
                    message += "，部分失败，详情: " + results.toString();
                }
                return CommonResult.success(message);
            } else {
                return CommonResult.error("所有渠道发送失败，详情: " + results.toString());
            }
        } catch (Exception e) {
            log.error("发送多渠道通知失败", e);
            return CommonResult.error("发送失败: " + e.getMessage());
        }
    }

    /**
     * 保存业务消息到数据库
     */
    @PostMapping("/save")
    public CommonResult<String> saveMessage(@Valid @RequestBody SaveMessageRequest request) {
        try {
            log.info("保存业务消息: 标题={}, 类型={}", request.getTitle(), request.getType());

            BaseMessage message = new BaseMessage();
            message.setTitle(request.getTitle());
            message.setContent(request.getContent());
            message.setReceiverId(request.getReceiverId());
            message.setBusinessId(request.getBusinessId());
            message.setType(MessageTypeEnum.valueOf(request.getType()));

            messageSender.send(message);

            return CommonResult.success("消息保存成功");
        } catch (IllegalArgumentException e) {
            log.error("消息类型无效: {}", request.getType(), e);
            return CommonResult.error("消息类型无效: " + request.getType());
        } catch (Exception e) {
            log.error("保存业务消息失败", e);
            return CommonResult.error("保存失败: " + e.getMessage());
        }
    }

    /**
     * 测试消息服务连通性
     */
    @GetMapping("/test")
    public CommonResult<Map<String, String>> testMessageServices() {
        try {
            BaseNotify testNotification = createTestNotification();

            Map<String, String> results = new HashMap<>();
            results.put("email", testService(emailService, testNotification));
            results.put("sms", testService(smsService, testNotification));
            results.put("internal", testService(internalService, testNotification));
            results.put("websocket", testService(webSocketService, testNotification));

            return CommonResult.success(results, "消息服务测试完成");
        } catch (Exception e) {
            log.error("测试消息服务失败", e);
            return CommonResult.error("测试失败: " + e.getMessage());
        }
    }

    /**
     * 获取支持的消息渠道列表
     */
    @GetMapping("/channels")
    public CommonResult<List<String>> getSupportedChannels() {
        try {
            List<String> channels = Arrays.asList("EMAIL", "SMS", "INTERNAL", "WEBSOCKET");
            return CommonResult.success(channels, "获取支持的消息渠道成功");
        } catch (Exception e) {
            log.error("获取支持的消息渠道失败", e);
            return CommonResult.error("获取失败: " + e.getMessage());
        }
    }

    // ================================ 私有辅助方法 ================================

    /**
     * 构建通知对象
     */
    private BaseNotify buildNotification(InternalMessageRequest request) {
        BaseNotify notification = new BaseNotify();
        notification.setTitle(request.getTitle());
        notification.setContent(request.getContent());
        notification.setReceivers(request.getReceivers());
        return notification;
    }

    /**
     * 根据渠道发送消息
     */
    private String sendByChannel(String channel, BaseNotify notification) {
        switch (channel.toUpperCase()) {
            case "EMAIL":
                emailService.send(notification);
                return "成功";
            case "SMS":
                smsService.send(notification);
                return "成功";
            case "INTERNAL":
                internalService.send(notification);
                return "成功";
            case "WEBSOCKET":
                webSocketService.send(notification);
                return "成功";
            default:
                return "未知渠道: " + channel;
        }
    }

    /**
     * 创建测试通知
     */
    private BaseNotify createTestNotification() {
        BaseNotify testNotification = new BaseNotify();
        testNotification.setTitle("测试消息");
        testNotification.setContent("这是一条测试消息，用于验证消息服务的连通性");
        testNotification.setReceivers(Arrays.asList("test-user"));
        return testNotification;
    }

    /**
     * 测试单个服务
     */
    private String testService(NotificationService service, BaseNotify notification) {
        try {
            service.send(notification);
            return "正常";
        } catch (Exception e) {
            return "异常: " + e.getMessage();
        }
    }

    // ================================ 请求实体类 ================================

    public static class InternalMessageRequest {
        @NotBlank(message = "消息标题不能为空")
        private String title;

        @NotBlank(message = "消息内容不能为空")
        private String content;

        @NotEmpty(message = "接收者列表不能为空")
        private List<String> receivers;

        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        public List<String> getReceivers() { return receivers; }
        public void setReceivers(List<String> receivers) { this.receivers = receivers; }
    }

    public static class EmailMessageRequest extends InternalMessageRequest {
        // 继承基础字段即可
    }

    public static class SmsMessageRequest extends InternalMessageRequest {
        // 继承基础字段即可
    }

    public static class WebSocketMessageRequest extends InternalMessageRequest {
        // 继承基础字段即可
    }

    public static class MultiChannelMessageRequest extends InternalMessageRequest {
        @NotEmpty(message = "通知渠道不能为空")
        private List<String> channels;

        public List<String> getChannels() { return channels; }
        public void setChannels(List<String> channels) { this.channels = channels; }
    }

    public static class SaveMessageRequest {
        @NotBlank(message = "消息标题不能为空")
        private String title;

        @NotBlank(message = "消息内容不能为空")
        private String content;

        @NotNull(message = "接收者ID不能为空")
        private Long receiverId;

        private Long businessId;

        @NotBlank(message = "消息类型不能为空")
        private String type;

        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        public Long getReceiverId() { return receiverId; }
        public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }

        public Long getBusinessId() { return businessId; }
        public void setBusinessId(Long businessId) { this.businessId = businessId; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
    }
} 