package com.lawfirm.model.workflow.service;

import java.util.List;
import java.util.Map;

/**
 * 通知服务接口
 * 提供通知相关的功能
 *
 * @author claude
 */
public interface NotificationService {

    /**
     * 发送通知
     *
     * @param recipientId 接收人ID
     * @param subject 通知主题
     * @param content 通知内容
     * @param variables 通知变量
     */
    void sendNotification(String recipientId, String subject, String content, Map<String, Object> variables);

    /**
     * 发送批量通知
     *
     * @param recipientIds 接收人ID列表
     * @param subject 通知主题
     * @param content 通知内容
     * @param variables 通知变量
     */
    void sendBatchNotification(List<String> recipientIds, String subject, String content, Map<String, Object> variables);

    /**
     * 发送系统通知
     *
     * @param subject 通知主题
     * @param content 通知内容
     * @param variables 通知变量
     */
    void sendSystemNotification(String subject, String content, Map<String, Object> variables);
} 