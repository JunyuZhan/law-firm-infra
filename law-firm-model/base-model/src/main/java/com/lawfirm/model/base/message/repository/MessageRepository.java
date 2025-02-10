package com.lawfirm.model.base.message.repository;

import com.lawfirm.model.base.message.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息仓库接口
 */
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, String> {
    
    /**
     * 查询用户的未读消息
     */
    List<MessageEntity> findByReceiverIdAndIsReadFalse(Long receiverId);
    
    /**
     * 统计用户的未读消息数量
     */
    long countByReceiverIdAndIsReadFalse(Long receiverId);
    
    /**
     * 分页查询用户的消息
     */
    Page<MessageEntity> findByReceiverId(Long receiverId, Pageable pageable);
    
    /**
     * 查询用户发送的消息
     */
    List<MessageEntity> findBySenderId(Long senderId);
    
    /**
     * 根据业务类型和业务ID查询消息
     */
    List<MessageEntity> findByBusinessTypeAndBusinessId(String businessType, String businessId);
} 