package com.lawfirm.model.base.message.repository;

import com.lawfirm.model.base.message.entity.UserMessageSettingEntity;
import com.lawfirm.model.base.message.enums.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户消息设置仓库接口
 */
@Repository
public interface UserMessageSettingRepository extends JpaRepository<UserMessageSettingEntity, String> {
    
    /**
     * 查询用户的消息类型设置
     */
    UserMessageSettingEntity findByUserIdAndType(Long userId, MessageType type);
    
    /**
     * 查询用户的所有消息设置
     */
    List<UserMessageSettingEntity> findByUserId(Long userId);
    
    /**
     * 删除用户的所有设置
     */
    void deleteByUserId(Long userId);
} 