package com.lawfirm.core.message.service.impl;

import com.lawfirm.common.security.context.SecurityContextHolder;
import com.lawfirm.common.security.crypto.CryptoService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.message.entity.base.BaseMessage;
import com.lawfirm.model.message.service.MessageService;
import com.lawfirm.model.message.dto.message.MessageCreateDTO;
import com.lawfirm.model.message.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * 消息发送实现类
 * 支持数据库和内存两种存储方式
 */
@Slf4j
@Component("messageSender")
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MessageSenderImpl implements MessageSender {

    @Autowired(required = false)
    @Qualifier("commonCryptoService")
    private CryptoService cryptoService;
    
    @Autowired(required = false)
    private MessageService messageService;
    
    @Value("${message.storage.type:memory}")
    private String storageType;

    // 内存存储模拟（作为降级方案）
    private static final Map<Long, BaseMessage> MESSAGE_STORE = new ConcurrentHashMap<>();

    @Override
    public void send(BaseMessage message) {
        log.info("发送消息: {}, 存储类型: {}", 
                message != null ? message.getClass().getSimpleName() : "null", storageType);
        
        if (!SecurityContextHolder.hasPermission("message:send")) {
            throw new SecurityException("无权发送消息");
        }
        
        // 数据预处理
        if (message.isContainsSensitiveData() && cryptoService != null) {
            message.setContent(cryptoService.encrypt(message.getContent()));
        }
        
        if (message.getId() == null) {
            message.setId(System.currentTimeMillis());
        }
        
        if (message.getSendTime() == null) {
            message.setSendTime(LocalDateTime.now());
        }

        // 根据配置选择存储方式
        try {
            if ("database".equalsIgnoreCase(storageType) && messageService != null) {
                // 转换为DTO并保存到数据库
                MessageCreateDTO createDTO = convertToCreateDTO(message);
                Long messageId = messageService.createMessage(createDTO);
                message.setId(messageId);
                // 同时发送消息
                messageService.sendMessage(messageId);
                log.info("[SEND][DB] 消息已存储到数据库: id={}, type={}", messageId, message.getType());
            } else {
                // 内存存储（降级）
                MESSAGE_STORE.put(message.getId(), message);
                log.info("[SEND][MEMORY] 消息已存储到内存: id={}, type={}", message.getId(), message.getType());
            }
        } catch (Exception e) {
            log.error("数据库存储失败，降级到内存存储: {}", e.getMessage());
            MESSAGE_STORE.put(message.getId(), message);
            log.info("[SEND][FALLBACK] 消息已降级存储到内存: id={}, type={}", message.getId(), message.getType());
        }
    }

    @Override
    public BaseMessage getMessage(String messageId) {
        log.info("获取消息: {}, 存储类型: {}", messageId, storageType);
        
        if (!SecurityContextHolder.hasPermission("message:view")) {
            throw new SecurityException("无权查看消息");
        }
        
        try {
            Long id = Long.valueOf(messageId);
            
            if ("database".equalsIgnoreCase(storageType) && messageService != null) {
                // 从数据库获取
                MessageVO messageVO = messageService.getMessage(id);
                if (messageVO != null) {
                    BaseMessage message = convertFromVO(messageVO);
                    log.info("[GET][DB] 从数据库获取消息: id={}", id);
                    return message;
                }
            }
            
            // 从内存获取（降级或主要方式）
            BaseMessage message = MESSAGE_STORE.get(id);
            if (message != null) {
                log.info("[GET][MEMORY] 从内存获取消息: id={}", id);
            }
            return message;
            
        } catch (Exception e) {
            log.warn("获取消息失败: messageId={}, error={}", messageId, e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteMessage(String messageId) {
        log.info("删除消息: {}, 存储类型: {}", messageId, storageType);
        
        if (!SecurityContextHolder.hasPermission("message:delete")) {
            throw new SecurityException("无权删除消息");
        }
        
        try {
            Long id = Long.valueOf(messageId);
            
            if ("database".equalsIgnoreCase(storageType) && messageService != null) {
                // 从数据库删除
                messageService.deleteMessages(List.of(id));
                log.info("[DELETE][DB] 消息已从数据库删除: id={}", id);
            }
            
            // 从内存删除（无论哪种方式都执行，确保一致性）
            MESSAGE_STORE.remove(id);
            log.info("[DELETE][MEMORY] 消息已从内存删除: id={}", id);
            
        } catch (Exception e) {
            log.warn("删除消息失败: messageId={}, error={}", messageId, e.getMessage());
        }
    }

    @Override
    public void deleteMessages(List<Long> messageIds) {
        log.info("批量删除消息, 数量: {}, 存储类型: {}", 
                messageIds != null ? messageIds.size() : 0, storageType);
        
        if (!SecurityContextHolder.hasPermission("message:delete")) {
            throw new SecurityException("无权删除消息");
        }
        
        if (messageIds == null || messageIds.isEmpty()) {
            return;
        }
        
        try {
            if ("database".equalsIgnoreCase(storageType) && messageService != null) {
                // 从数据库批量删除
                messageService.deleteMessages(messageIds);
                log.info("[DELETE][DB] 批量删除消息从数据库: count={}", messageIds.size());
            }
            
            // 从内存批量删除（无论哪种方式都执行）
            for (Long id : messageIds) {
                MESSAGE_STORE.remove(id);
            }
            log.info("[DELETE][MEMORY] 批量删除消息从内存: count={}", messageIds.size());
            
        } catch (Exception e) {
            log.error("批量删除消息失败: count={}, error={}", messageIds.size(), e.getMessage());
        }
    }

    /**
     * 转换BaseMessage为MessageCreateDTO
     */
    private MessageCreateDTO convertToCreateDTO(BaseMessage message) {
        MessageCreateDTO dto = new MessageCreateDTO();
        dto.setTitle(message.getTitle());
        dto.setContent(message.getContent());
        dto.setMessageType(message.getType() != null ? message.getType().getValue() : null);
        dto.setSenderId(message.getSenderId());
        dto.setSenderName(message.getSenderName());
        dto.setSenderType(message.getSenderType());
        dto.setReceiverId(message.getReceiverId());
        dto.setReceiverName(message.getReceiverName());
        dto.setReceiverType(message.getReceiverType());
        dto.setPriority(message.getPriority());
        dto.setNeedConfirm(message.getNeedConfirm());
        dto.setMessageConfig(message.getMessageConfig());
        dto.setBusinessId(message.getBusinessId());
        dto.setBusinessType(message.getBusinessType());
        return dto;
    }

    /**
     * 转换MessageVO为BaseMessage
     */
    private BaseMessage convertFromVO(MessageVO vo) {
        BaseMessage message = new BaseMessage();
        message.setId(vo.getId());
        message.setTitle(vo.getTitle());
        message.setContent(vo.getContent());
        message.setType(com.lawfirm.model.message.enums.MessageTypeEnum.getByValue(vo.getMessageType()));
        message.setSenderId(vo.getSenderId());
        message.setSenderName(vo.getSenderName());
        message.setSenderType(vo.getSenderType());
        message.setReceiverId(vo.getReceiverId());
        message.setReceiverName(vo.getReceiverName());
        message.setReceiverType(vo.getReceiverType());
        message.setSendTime(vo.getSendTime());
        message.setReadTime(vo.getReadTime());
        message.setProcessTime(vo.getProcessTime());
        message.setPriority(vo.getPriority());
        message.setNeedConfirm(vo.getNeedConfirm());
        message.setConfirmTime(vo.getConfirmTime());
        message.setMessageConfig(vo.getMessageConfig());
        message.setBusinessId(vo.getBusinessId());
        message.setBusinessType(vo.getBusinessType());
        return message;
    }

    /**
     * 获取当前存储状态统计
     */
    public String getStorageStats() {
        StringBuilder stats = new StringBuilder();
        stats.append("存储类型: ").append(storageType);
        stats.append(", 内存消息数: ").append(MESSAGE_STORE.size());
        
        if ("database".equalsIgnoreCase(storageType) && messageService != null) {
            try {
                // MessageService没有count方法，使用查询方式估算
                stats.append(", 数据库存储: 已启用");
            } catch (Exception e) {
                stats.append(", 数据库连接异常");
            }
        }
        
        return stats.toString();
    }
} 