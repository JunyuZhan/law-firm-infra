package com.lawfirm.common.core.status;

import com.lawfirm.common.core.enums.StatusEnum;

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
} 