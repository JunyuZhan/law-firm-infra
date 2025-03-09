package com.lawfirm.model.system.dto.upgrade;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统升级更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UpgradeUpdateDTO extends UpgradeCreateDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 升级ID
     */
    private Long id;
    
    /**
     * 升级状态
     */
    private String status;
    
    /**
     * 实际升级时间
     */
    private java.time.LocalDateTime actualTime;
    
    /**
     * 升级结果
     */
    private String result;
} 