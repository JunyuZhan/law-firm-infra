package com.lawfirm.core.message.controller;

import com.lawfirm.core.message.model.Message;
import com.lawfirm.core.message.model.MessageTemplate;
import com.lawfirm.core.message.model.UserMessageSetting;
import com.lawfirm.core.message.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 消息控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/message")
public class MessageController {
    
    private final MessageService messageService;
    
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    
    /**
     * 发送普通消息
     */
    @PostMapping("/send")
    public String sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }
    
    /**
     * 发送模板消息
     */
    @PostMapping("/send/template")
    public String sendTemplateMessage(@RequestParam String templateCode,
                                    @RequestBody Map<String, Object> params,
                                    @RequestParam Long receiverId,
                                    @RequestParam(required = false) String businessType,
                                    @RequestParam(required = false) String businessId) {
        return messageService.sendTemplateMessage(templateCode, params, receiverId, businessType, businessId);
    }
    
    /**
     * 发送系统通知
     */
    @PostMapping("/send/notice")
    public List<String> sendSystemNotice(@RequestParam String title,
                                       @RequestParam String content,
                                       @RequestBody List<Long> receiverIds) {
        return messageService.sendSystemNotice(title, content, receiverIds);
    }
    
    /**
     * 标记消息已读
     */
    @PostMapping("/read/{messageId}")
    public void markAsRead(@PathVariable String messageId,
                          @RequestParam Long userId) {
        messageService.markAsRead(messageId, userId);
    }
    
    /**
     * 批量标记消息已读
     */
    @PostMapping("/read/batch")
    public void markAsRead(@RequestBody List<String> messageIds,
                          @RequestParam Long userId) {
        messageService.markAsRead(messageIds, userId);
    }
    
    /**
     * 获取未读消息数量
     */
    @GetMapping("/unread/count")
    public long getUnreadCount(@RequestParam Long userId) {
        return messageService.getUnreadCount(userId);
    }
    
    /**
     * 获取消息列表
     */
    @GetMapping("/list")
    public List<Message> listMessages(@RequestParam Long userId,
                                    @RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "20") int size) {
        return messageService.listMessages(userId, page, size);
    }
    
    /**
     * 创建消息模板
     */
    @PostMapping("/template")
    public String createTemplate(@RequestBody MessageTemplate template) {
        return messageService.createTemplate(template);
    }
    
    /**
     * 更新消息模板
     */
    @PutMapping("/template")
    public void updateTemplate(@RequestBody MessageTemplate template) {
        messageService.updateTemplate(template);
    }
    
    /**
     * 删除消息模板
     */
    @DeleteMapping("/template/{templateId}")
    public void deleteTemplate(@PathVariable String templateId) {
        messageService.deleteTemplate(templateId);
    }
    
    /**
     * 获取消息模板
     */
    @GetMapping("/template/{templateId}")
    public MessageTemplate getTemplate(@PathVariable String templateId) {
        return messageService.getTemplate(templateId);
    }
    
    /**
     * 获取用户消息设置
     */
    @GetMapping("/setting")
    public UserMessageSetting getUserSetting(@RequestParam Long userId) {
        return messageService.getUserSetting(userId);
    }
    
    /**
     * 更新用户消息设置
     */
    @PutMapping("/setting")
    public void updateUserSetting(@RequestBody UserMessageSetting setting) {
        messageService.updateUserSetting(setting);
    }
} 