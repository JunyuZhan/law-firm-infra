package com.lawfirm.archive.service;

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
 * 归档模块消息服务
 * 处理归档任务、存储提醒、数据清理等通知
 */
@Service("archiveMessageService")
public class ArchiveMessageService {

    private static final Logger log = LoggerFactory.getLogger(ArchiveMessageService.class);

    @Autowired(required = false)
    @Qualifier("messageSender")
    private MessageSender messageSender;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 发送归档任务完成通知
     */
    public void sendArchiveTaskCompletedNotification(Long taskId, String taskType, String description, List<Long> managerIds) {
        if (managerIds == null || managerIds.isEmpty()) return;

        try {
            String title = "归档任务完成：" + taskType;
            String content = String.format("任务描述：%s，归档任务已完成。", description);
            
            for (Long managerId : managerIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(managerId);
                    message.setBusinessId(taskId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[归档消息] 归档任务完成通知已发送: taskId={}, taskType={}, recipients={}", 
                    taskId, taskType, managerIds.size());

        } catch (Exception e) {
            log.error("发送归档任务完成通知失败: taskId={}, error={}", taskId, e.getMessage());
        }
    }

    /**
     * 发送存储空间不足警告
     */
    public void sendStorageSpaceWarning(String storageLocation, double usagePercentage, List<Long> adminIds) {
        if (adminIds == null || adminIds.isEmpty()) return;

        try {
            String title = "存储空间不足警告：" + storageLocation;
            String content = String.format("当前使用率：%.1f%%，请及时清理或扩容。", usagePercentage);
            
            for (Long adminId : adminIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(adminId);
                    message.setType(MessageTypeEnum.SYSTEM);
                    messageSender.send(message);
                }
            }
            
            log.info("[归档消息] 存储空间警告已发送: storageLocation={}, usagePercentage={}, recipients={}", 
                    storageLocation, usagePercentage, adminIds.size());

        } catch (Exception e) {
            log.error("发送存储空间警告失败: storageLocation={}, error={}", storageLocation, e.getMessage());
        }
    }

    /**
     * 发送数据清理提醒
     */
    public void sendDataCleanupReminder(String dataType, LocalDateTime cleanupDate, List<Long> managerIds) {
        if (managerIds == null || managerIds.isEmpty()) return;

        try {
            String title = "数据清理提醒：" + dataType;
            String content = String.format("计划清理时间：%s，请确认清理计划。", 
                    cleanupDate.format(DATE_TIME_FORMATTER));
            
            for (Long managerId : managerIds) {
                if (messageSender != null) {
                    BaseMessage message = new BaseMessage();
                    message.setTitle(title);
                    message.setContent(content);
                    message.setReceiverId(managerId);
                    message.setType(MessageTypeEnum.NOTICE);
                    messageSender.send(message);
                }
            }
            
            log.info("[归档消息] 数据清理提醒已发送: dataType={}, cleanupDate={}, recipients={}", 
                    dataType, cleanupDate, managerIds.size());

        } catch (Exception e) {
            log.error("发送数据清理提醒失败: dataType={}, error={}", dataType, e.getMessage());
        }
    }
} 