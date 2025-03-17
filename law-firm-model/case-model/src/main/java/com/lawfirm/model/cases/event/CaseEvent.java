package com.lawfirm.model.cases.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 案件事件基类
 * 所有案件相关事件都应继承此基类
 */
@Data
@NoArgsConstructor
public abstract class CaseEvent implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 事件ID
     */
    private String eventId;
    
    /**
     * 案件ID
     * 与案件实体ID字段保持一致 {@link com.lawfirm.model.cases.entity.base.Case}
     * 实体ID类型由 {@link com.lawfirm.common.data.entity.DataEntity#getId()} 定义
     */
    private Long caseId;
    
    /**
     * 案件编号
     * 与案件实体的案件编号字段保持一致 {@link com.lawfirm.model.cases.entity.base.Case#getCaseNumber()}
     */
    private String caseNumber;
    
    /**
     * 事件发生时间
     */
    private LocalDateTime occurTime;
    
    /**
     * 事件触发者ID
     * 与 {@link com.lawfirm.common.data.entity.DataEntity#getCreateBy()} 保持类型一致
     */
    private String triggerId;
    
    /**
     * 事件触发者姓名
     */
    private String triggerName;
    
    /**
     * 事件来源
     */
    private String source;
    
    /**
     * 事件消息
     */
    private String message;
    
    /**
     * 获取事件类型
     * 
     * @return 事件类型
     */
    public abstract String getEventType();
    
    public CaseEvent(Long caseId, String caseNumber) {
        this.caseId = caseId;
        this.caseNumber = caseNumber;
        this.occurTime = LocalDateTime.now();
    }
} 