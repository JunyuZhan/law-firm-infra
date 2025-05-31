package com.lawfirm.auth.service;

import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.enums.MessageTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 认证授权模块消息服务
 * 处理登录异常、权限变更、安全警告等通知
 */
@Service("authMessageService")
public class AuthMessageService {

    private static final Logger log = LoggerFactory.getLogger(AuthMessageService.class);

    @Autowired(required = false)
    @Qualifier("messageSender")
    private MessageSender messageSender;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 发送异常登录警告
     */
    public void sendAbnormalLoginWarning(Long userId, String username, String ipAddress, String location, LocalDateTime loginTime) {
        if (userId == null) return;

        try {
            String title = "异常登录警告";
            String content = String.format("用户：%s，IP：%s，地点：%s，时间：%s", 
                    username, ipAddress, location, loginTime.format(DATE_TIME_FORMATTER));
            
            if (messageSender != null) {
                BaseMessage message = new BaseMessage();
                message.setTitle(title);
                message.setContent(content);
                message.setReceiverId(userId);
                message.setType(MessageTypeEnum.SYSTEM);
                messageSender.send(message);
            }
            
            log.info("[认证消息] 异常登录警告已发送: userId={}, ipAddress={}", userId, ipAddress);

        } catch (Exception e) {
            log.error("发送异常登录警告失败: userId={}, error={}", userId, e.getMessage());
        }
    }

    /**
     * 发送权限变更通知
     */
    public void sendPermissionChangeNotification(Long userId, String username, String changeType, String roleOrPermission, List<Long> adminIds) {
        if (adminIds == null || adminIds.isEmpty()) return;

        try {
            String title = "权限变更通知：" + username;
            String content = String.format("变更类型：%s，角色/权限：%s", changeType, roleOrPermission);
            
            for (Long adminId : adminIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(adminId);
                    message.setBusinessId(userId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[认证消息] 权限变更通知已发送: userId={}, changeType={}, recipients={}", 
                    userId, changeType, adminIds.size());

        } catch (Exception e) {
            log.error("发送权限变更通知失败: userId={}, error={}", userId, e.getMessage());
        }
    }

    /**
     * 发送密码过期提醒
     */
    public void sendPasswordExpirationReminder(Long userId, String username, LocalDateTime expirationDate, int daysRemaining) {
        if (userId == null) return;

        try {
            String title = "密码过期提醒";
            String content = String.format("您的密码将于 %s 过期，还有%d天，请及时修改。", 
                    expirationDate.format(DATE_TIME_FORMATTER), daysRemaining);
            
            if (messageSender != null) {
                BaseMessage message = new BaseMessage();
                message.setTitle(title);
                message.setContent(content);
                message.setReceiverId(userId);
                message.setType(MessageTypeEnum.NOTICE);
                messageSender.send(message);
            }
            
            log.info("[认证消息] 密码过期提醒已发送: userId={}, daysRemaining={}", userId, daysRemaining);

        } catch (Exception e) {
            log.error("发送密码过期提醒失败: userId={}, error={}", userId, e.getMessage());
        }
    }

    /**
     * 发送账户锁定通知
     */
    public void sendAccountLockNotification(Long userId, String username, String reason, List<Long> adminIds) {
        if (adminIds == null || adminIds.isEmpty()) return;

        try {
            String title = "账户锁定通知：" + username;
            String content = String.format("锁定原因：%s，请及时处理。", reason);
            
            for (Long adminId : adminIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(adminId);
                    message.setBusinessId(userId);
                    message.setType(MessageTypeEnum.SYSTEM);
                    messageSender.send(message);
                }
            }
            
            log.info("[认证消息] 账户锁定通知已发送: userId={}, reason={}, recipients={}", 
                    userId, reason, adminIds.size());

        } catch (Exception e) {
            log.error("发送账户锁定通知失败: userId={}, error={}", userId, e.getMessage());
        }
    }
} 