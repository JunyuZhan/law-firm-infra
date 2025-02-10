package com.lawfirm.model.base.message.repository;

import com.lawfirm.model.base.message.entity.MessageTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 消息模板仓库
 */
@Repository
public interface MessageTemplateRepository extends JpaRepository<MessageTemplateEntity, String> {
    
    /**
     * 根据编码和启用状态查询模板
     */
    MessageTemplateEntity findByCodeAndEnabledTrue(String code);
} 