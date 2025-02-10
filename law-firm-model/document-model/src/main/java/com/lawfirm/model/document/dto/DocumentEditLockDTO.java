package com.lawfirm.model.document.dto;

import com.lawfirm.model.document.dto.base.DocumentEditBaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文档编辑锁定数据传输对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DocumentEditLockDTO extends DocumentEditBaseDTO {
    
    private String lockData;          // 锁定数据
    private Integer lockDuration;     // 锁定时长（分钟）
    private String reason;            // 锁定原因
} 