package com.lawfirm.core.workflow.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 历史任务模型
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class HistoricTask extends Task {
    
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    
    /**
     * 持续时间(毫秒)
     */
    private Long durationInMillis;
    
    /**
     * 删除原因
     */
    private String deleteReason;
} 