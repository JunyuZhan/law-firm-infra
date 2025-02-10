package com.lawfirm.model.base.enums;

import com.lawfirm.common.core.enums.BaseEnum;

/**
 * 业务状态枚举接口
 */
public interface BusinessStatusEnum extends BaseEnum<String> {
    
    /**
     * 是否为终态（已完成、已关闭等）
     */
    boolean isFinal();
    
    /**
     * 是否可编辑
     */
    boolean isEditable();
    
    /**
     * 获取状态组
     */
    String getGroup();
    
    /**
     * 获取下一个可用状态列表
     */
    BusinessStatusEnum[] getNextStatus();
} 