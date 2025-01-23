package com.lawfirm.model.base.status;

import com.lawfirm.model.base.enums.StatusEnum;

/**
 * 状态感知接口
 */
public interface StatusAware {
    
    /**
     * 获取状态
     */
    StatusEnum getStatus();
    
    /**
     * 设置状态
     */
    void setStatus(StatusEnum status);
    
    default boolean isEnabled() {  // 判断是否启用
        return StatusEnum.ENABLED.equals(getStatus());
    }
    
    default boolean isDisabled() {  // 判断是否禁用
        return StatusEnum.DISABLED.equals(getStatus());
    }
    
    default boolean isDeleted() {  // 判断是否删除
        return StatusEnum.DELETED.equals(getStatus());
    }
    
    default boolean isLocked() {  // 判断是否锁定
        return StatusEnum.LOCKED.equals(getStatus());
    }
    
    default boolean isExpired() {  // 判断是否过期
        return StatusEnum.EXPIRED.equals(getStatus());
    }
} 