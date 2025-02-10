package com.lawfirm.model.base.message.repository;

import com.lawfirm.model.base.message.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 消息仓库
 */
@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, String> {
} 