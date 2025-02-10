package com.lawfirm.model.base.message.repository;

import com.lawfirm.model.base.message.entity.UserMessageSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户消息设置仓库
 */
@Repository
public interface UserMessageSettingRepository extends JpaRepository<UserMessageSettingEntity, Long> {
} 