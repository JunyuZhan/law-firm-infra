package com.lawfirm.core.message.controller;

import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.entity.MessageTemplateEntity;
import com.lawfirm.model.base.message.entity.UserMessageSettingEntity;
import com.lawfirm.model.base.message.service.BusinessMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 消息控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/messages")
@Tag(name = "消息管理", description = "消息相关接口")
public class MessageController {
    
    private final BusinessMessageService messageService;
    
    public MessageController(BusinessMessageService messageService) {
        this.messageService = messageService;
    }
    
    /**
     * 发送普通消息
     */
    @PostMapping
    @Operation(summary = "发送普通消息", description = "发送一条普通消息给指定用户")
    public String sendMessage(@RequestBody MessageEntity message) {
        return messageService.sendMessage(message);
    }
    
    /**
     * 发送模板消息
     */
    @PostMapping("/template/{templateCode}")
    @Operation(summary = "发送模板消息", description = "使用指定模板发送消息")
    public String sendTemplateMessage(
            @Parameter(description = "模板编码") @PathVariable String templateCode,
            @Parameter(description = "模板参数") @RequestBody Map<String, Object> params,
            @Parameter(description = "接收者ID") @RequestParam Long receiverId,
            @Parameter(description = "业务类型") @RequestParam(required = false) String businessType,
            @Parameter(description = "业务ID") @RequestParam(required = false) String businessId) {
        return messageService.sendTemplateMessage(templateCode, params, receiverId, businessType, businessId);
    }
    
    /**
     * 发送系统通知
     */
    @PostMapping("/notices")
    @Operation(summary = "发送系统通知", description = "发送系统通知给多个用户")
    public List<String> sendSystemNotice(
            @Parameter(description = "通知标题") @RequestParam String title,
            @Parameter(description = "通知内容") @RequestParam String content,
            @Parameter(description = "接收者ID列表") @RequestBody List<Long> receiverIds) {
        return messageService.sendSystemNotice(title, content, receiverIds);
    }
    
    /**
     * 标记消息已读
     */
    @PutMapping("/{messageId}/read")
    @Operation(summary = "标记消息已读", description = "将指定消息标记为已读")
    public void markAsRead(
            @Parameter(description = "消息ID") @PathVariable String messageId,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        messageService.markAsRead(messageId, userId);
    }
    
    /**
     * 批量标记消息已读
     */
    @PutMapping("/read")
    @Operation(summary = "批量标记消息已读", description = "批量将多条消息标记为已读")
    public void markAsRead(
            @Parameter(description = "消息ID列表") @RequestBody List<String> messageIds,
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        messageService.markAsRead(messageIds, userId);
    }
    
    /**
     * 获取未读消息数量
     */
    @GetMapping("/unread/count")
    @Operation(summary = "获取未读消息数量", description = "获取用户的未读消息数量")
    public long getUnreadCount(
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        return messageService.getUnreadCount(userId);
    }
    
    /**
     * 获取消息列表
     */
    @GetMapping
    @Operation(summary = "获取消息列表", description = "分页获取用户的消息列表")
    public Page<MessageEntity> listMessages(
            @Parameter(description = "用户ID") @RequestParam Long userId,
            @Parameter(description = "分页参数") Pageable pageable) {
        return messageService.listMessages(userId, pageable);
    }
    
    /**
     * 创建消息模板
     */
    @PostMapping("/templates")
    @Operation(summary = "创建消息模板", description = "创建一个新的消息模板")
    public String createTemplate(
            @Parameter(description = "模板信息") @RequestBody MessageTemplateEntity template) {
        return messageService.createTemplate(template);
    }
    
    /**
     * 更新消息模板
     */
    @PutMapping("/templates/{templateId}")
    @Operation(summary = "更新消息模板", description = "更新指定的消息模板")
    public void updateTemplate(
            @Parameter(description = "模板ID") @PathVariable String templateId,
            @Parameter(description = "模板信息") @RequestBody MessageTemplateEntity template) {
        template.setId(templateId);
        messageService.updateTemplate(template);
    }
    
    /**
     * 删除消息模板
     */
    @DeleteMapping("/templates/{templateId}")
    @Operation(summary = "删除消息模板", description = "删除指定的消息模板")
    public void deleteTemplate(
            @Parameter(description = "模板ID") @PathVariable String templateId) {
        messageService.deleteTemplate(templateId);
    }
    
    /**
     * 获取消息模板
     */
    @GetMapping("/templates/{templateId}")
    @Operation(summary = "获取消息模板", description = "获取指定的消息模板详情")
    public MessageTemplateEntity getTemplate(
            @Parameter(description = "模板ID") @PathVariable String templateId) {
        return messageService.getTemplate(templateId);
    }
    
    /**
     * 获取用户消息设置
     */
    @GetMapping("/settings/{userId}")
    @Operation(summary = "获取用户消息设置", description = "获取指定用户的消息设置")
    public List<UserMessageSettingEntity> getUserSettings(
            @Parameter(description = "用户ID") @PathVariable Long userId) {
        return messageService.getUserSettings(userId);
    }
    
    /**
     * 更新用户消息设置
     */
    @PutMapping("/settings/{userId}")
    @Operation(summary = "更新用户消息设置", description = "更新指定用户的消息设置")
    public void updateUserSetting(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @Parameter(description = "消息设置") @RequestBody UserMessageSettingEntity setting) {
        setting.setUserId(userId);
        messageService.updateUserSetting(setting);
    }
} 