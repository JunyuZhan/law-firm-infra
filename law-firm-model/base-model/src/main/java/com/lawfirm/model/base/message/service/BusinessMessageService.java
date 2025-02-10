package com.lawfirm.model.base.message.service;

import com.lawfirm.model.base.message.entity.MessageEntity;
import com.lawfirm.model.base.message.entity.MessageTemplateEntity;
import com.lawfirm.model.base.message.entity.UserMessageSettingEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * 业务消息服务接口
 */
public interface BusinessMessageService {

    /**
     * 发送普通消息
     *
     * @param message 消息
     * @return 消息ID
     */
    String sendMessage(MessageEntity message);

    /**
     * 发送模板消息
     *
     * @param templateCode 模板编码
     * @param params 模板参数
     * @param receiverId 接收者ID
     * @param businessType 业务类型
     * @param businessId 业务ID
     * @return 消息ID
     */
    String sendTemplateMessage(String templateCode, Map<String, Object> params, Long receiverId, String businessType, String businessId);

    /**
     * 发送系统通知
     *
     * @param title 标题
     * @param content 内容
     * @param receiverIds 接收者ID列表
     * @return 消息ID列表
     */
    List<String> sendSystemNotice(String title, String content, List<Long> receiverIds);

    /**
     * 标记消息已读
     *
     * @param messageId 消息ID
     * @param userId 用户ID
     */
    void markAsRead(String messageId, Long userId);

    /**
     * 批量标记消息已读
     *
     * @param messageIds 消息ID列表
     * @param userId 用户ID
     */
    void markAsRead(List<String> messageIds, Long userId);

    /**
     * 获取用户未读消息数量
     *
     * @param userId 用户ID
     * @return 未读消息数量
     */
    long getUnreadCount(Long userId);

    /**
     * 获取用户消息列表
     *
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页大小
     * @return 消息列表
     */
    Page<MessageEntity> listMessages(Long userId, int page, int size);

    /**
     * 创建消息模板
     *
     * @param template 模板
     * @return 模板ID
     */
    String createTemplate(MessageTemplateEntity template);

    /**
     * 更新消息模板
     *
     * @param template 模板
     */
    void updateTemplate(MessageTemplateEntity template);

    /**
     * 删除消息模板
     *
     * @param templateId 模板ID
     */
    void deleteTemplate(String templateId);

    /**
     * 获取消息模板
     *
     * @param templateId 模板ID
     * @return 模板
     */
    MessageTemplateEntity getTemplate(String templateId);

    /**
     * 获取用户消息设置
     *
     * @param userId 用户ID
     * @return 消息设置
     */
    List<UserMessageSettingEntity> getUserSettings(Long userId);

    /**
     * 更新用户消息设置
     *
     * @param setting 消息设置
     */
    void updateUserSetting(UserMessageSettingEntity setting);

    /**
     * 订阅消息
     *
     * @param userId 用户ID
     * @param clientId 客户端ID
     */
    void subscribe(Long userId, String clientId);

    /**
     * 取消订阅
     *
     * @param userId 用户ID
     * @param clientId 客户端ID
     */
    void unsubscribe(Long userId, String clientId);
} 