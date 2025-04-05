package com.lawfirm.model.schedule.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 日程与案件关联VO
 */
@Data
@Accessors(chain = true)
public class ScheduleCaseRelationVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 关联ID
     */
    private Long id;
    
    /**
     * 日程ID
     */
    private Long scheduleId;
    
    /**
     * 案件ID
     */
    private Long caseId;
    
    /**
     * 案件标题
     */
    private String caseTitle;
    
    /**
     * 案件编号
     */
    private String caseNumber;
    
    /**
     * 关联描述
     */
    private String description;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 案件详细信息
     * 存储从案件模块获取的数据
     * 不参与序列化过程
     */
    private transient Map<String, Object> caseInfo;
} 