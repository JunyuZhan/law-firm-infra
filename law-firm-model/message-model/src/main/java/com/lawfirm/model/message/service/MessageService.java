package com.lawfirm.model.message.service;

import com.lawfirm.model.message.dto.message.MessageCreateDTO;
import com.lawfirm.model.message.dto.message.MessageQueryDTO;
import com.lawfirm.model.message.entity.system.SystemMessage;
import com.lawfirm.model.message.vo.MessageVO;

import java.util.List;

/**
 * 消息服务接口
 */
public interface MessageService {

    /**
     * 创建消息
     *
     * @param createDTO 创建参数
     * @return 消息ID
     */
    Long createMessage(MessageCreateDTO createDTO);

    /**
     * 发送消息
     *
     * @param id 消息ID
     */
    void sendMessage(Long id);

    /**
     * 阅读消息
     *
     * @param id 消息ID
     */
    void readMessage(Long id);

    /**
     * 确认消息
     *
     * @param id 消息ID
     * @param comment 确认意见
     */
    void confirmMessage(Long id, String comment);

    /**
     * 获取消息详情
     *
     * @param id 消息ID
     * @return 消息详情
     */
    MessageVO getMessage(Long id);

    /**
     * 查询消息列表
     *
     * @param queryDTO 查询参数
     * @return 消息列表
     */
    List<MessageVO> listMessages(MessageQueryDTO queryDTO);

    /**
     * 获取我的未读消息
     *
     * @param receiverId 接收者ID
     * @return 消息列表
     */
    List<MessageVO> listMyUnreadMessages(Long receiverId);

    /**
     * 获取我的已读消息
     *
     * @param receiverId 接收者ID
     * @return 消息列表
     */
    List<MessageVO> listMyReadMessages(Long receiverId);

    /**
     * 获取我发送的消息
     *
     * @param senderId 发送者ID
     * @return 消息列表
     */
    List<MessageVO> listMySentMessages(Long senderId);

    /**
     * 标记全部已读
     *
     * @param receiverId 接收者ID
     */
    void markAllRead(Long receiverId);

    /**
     * 批量删除消息
     *
     * @param ids 消息ID列表
     */
    void deleteMessages(List<Long> ids);
} 