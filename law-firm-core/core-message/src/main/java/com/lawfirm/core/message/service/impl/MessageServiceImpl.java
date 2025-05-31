package com.lawfirm.core.message.service.impl;

import com.lawfirm.model.message.dto.message.MessageCreateDTO;
import com.lawfirm.model.message.dto.message.MessageQueryDTO;
import com.lawfirm.model.message.service.MessageService;
import com.lawfirm.model.message.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 消息服务实现类
 * 提供基本的消息管理功能，支持内存存储（可扩展为数据库存储）
 */
@Slf4j
@Service("messageService")
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MessageServiceImpl implements MessageService {

    // 内存存储（生产环境应该使用数据库）
    private final Map<Long, MessageVO> messageStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Long createMessage(MessageCreateDTO createDTO) {
        log.info("创建消息: {}", createDTO.getTitle());
        
        Long messageId = idGenerator.getAndIncrement();
        MessageVO messageVO = new MessageVO();
        
        // 设置基本信息
        messageVO.setId(messageId);
        messageVO.setTitle(createDTO.getTitle());
        messageVO.setContent(createDTO.getContent());
        messageVO.setMessageType(createDTO.getMessageType());
        
        // 设置发送者信息
        messageVO.setSenderId(createDTO.getSenderId());
        messageVO.setSenderName(createDTO.getSenderName());
        messageVO.setSenderType(createDTO.getSenderType());
        
        // 设置接收者信息
        messageVO.setReceiverId(createDTO.getReceiverId());
        messageVO.setReceiverName(createDTO.getReceiverName());
        messageVO.setReceiverType(createDTO.getReceiverType());
        
        // 设置其他属性
        messageVO.setPriority(createDTO.getPriority());
        messageVO.setNeedConfirm(createDTO.getNeedConfirm());
        messageVO.setMessageConfig(createDTO.getMessageConfig());
        messageVO.setBusinessId(createDTO.getBusinessId());
        messageVO.setBusinessType(createDTO.getBusinessType());
        
        // 设置时间和状态
        messageVO.setCreateTime(LocalDateTime.now());
        messageVO.setStatus(0); // 0-未发送
        
        messageStore.put(messageId, messageVO);
        log.info("消息创建成功，ID: {}", messageId);
        
        return messageId;
    }

    @Override
    public void sendMessage(Long id) {
        log.info("发送消息: {}", id);
        
        MessageVO message = messageStore.get(id);
        if (message == null) {
            throw new IllegalArgumentException("消息不存在: " + id);
        }
        
        message.setSendTime(LocalDateTime.now());
        message.setStatus(1); // 1-已发送，未读
        
        log.info("消息发送成功: {}", id);
    }

    @Override
    public void readMessage(Long id) {
        log.info("阅读消息: {}", id);
        
        MessageVO message = messageStore.get(id);
        if (message == null) {
            throw new IllegalArgumentException("消息不存在: " + id);
        }
        
        if (Integer.valueOf(1).equals(message.getStatus())) { // 只有已发送未读的消息才能标记为已读
            message.setReadTime(LocalDateTime.now());
            message.setStatus(2); // 2-已读
            log.info("消息标记为已读: {}", id);
        }
    }

    @Override
    public void confirmMessage(Long id, String comment) {
        log.info("确认消息: {}, 意见: {}", id, comment);
        
        MessageVO message = messageStore.get(id);
        if (message == null) {
            throw new IllegalArgumentException("消息不存在: " + id);
        }
        
        if (message.getNeedConfirm() != null && Boolean.TRUE.equals(message.getNeedConfirm())) {
            message.setConfirmTime(LocalDateTime.now());
            message.setStatus(3); // 3-已确认
            // 这里可以保存确认意见，但MessageVO中没有这个字段，暂时记录日志
            log.info("消息确认成功: {}, 确认意见: {}", id, comment);
        } else {
            log.warn("消息不需要确认: {}", id);
        }
    }

    @Override
    public MessageVO getMessage(Long id) {
        log.debug("获取消息详情: {}", id);
        return messageStore.get(id);
    }

    @Override
    public List<MessageVO> listMessages(MessageQueryDTO queryDTO) {
        log.debug("查询消息列表: {}", queryDTO);
        
        return messageStore.values().stream()
                .filter(message -> {
                    // 按接收者过滤
                    if (queryDTO.getReceiverId() != null && 
                        !queryDTO.getReceiverId().equals(message.getReceiverId())) {
                        return false;
                    }
                    
                    // 按消息类型过滤
                    if (queryDTO.getMessageType() != null && 
                        !queryDTO.getMessageType().equals(message.getMessageType())) {
                        return false;
                    }
                    
                    // 按业务ID过滤
                    if (queryDTO.getBusinessId() != null && 
                        !queryDTO.getBusinessId().equals(message.getBusinessId())) {
                        return false;
                    }
                    
                    // 按状态过滤
                    if (queryDTO.getStatus() != null && 
                        !queryDTO.getStatus().equals(message.getStatus())) {
                        return false;
                    }
                    
                    return true;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageVO> listMyUnreadMessages(Long receiverId) {
        log.debug("获取未读消息: {}", receiverId);
        
        return messageStore.values().stream()
                .filter(message -> receiverId.equals(message.getReceiverId()) && 
                                 Integer.valueOf(1).equals(message.getStatus())) // 1-已发送，未读
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageVO> listMyReadMessages(Long receiverId) {
        log.debug("获取已读消息: {}", receiverId);
        
        return messageStore.values().stream()
                .filter(message -> receiverId.equals(message.getReceiverId()) && 
                                 (Integer.valueOf(2).equals(message.getStatus()) || 
                                  Integer.valueOf(3).equals(message.getStatus()))) // 2-已读，3-已确认
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageVO> listMySentMessages(Long senderId) {
        log.debug("获取发送的消息: {}", senderId);
        
        return messageStore.values().stream()
                .filter(message -> senderId.equals(message.getSenderId()))
                .collect(Collectors.toList());
    }

    @Override
    public void markAllRead(Long receiverId) {
        log.info("标记全部已读: {}", receiverId);
        
        messageStore.values().stream()
                .filter(message -> receiverId.equals(message.getReceiverId()) && 
                                 Integer.valueOf(1).equals(message.getStatus())) // 只标记未读消息
                .forEach(message -> {
                    message.setReadTime(LocalDateTime.now());
                    message.setStatus(2); // 2-已读
                });
        
        log.info("全部消息已标记为已读: {}", receiverId);
    }

    @Override
    public void deleteMessages(List<Long> ids) {
        log.info("批量删除消息: {}", ids);
        
        if (ids != null) {
            for (Long id : ids) {
                messageStore.remove(id);
            }
            log.info("消息删除成功，数量: {}", ids.size());
        }
    }
    
    /**
     * 获取存储统计信息
     */
    public String getStorageStats() {
        return String.format("内存消息总数: %d", messageStore.size());
    }
} 