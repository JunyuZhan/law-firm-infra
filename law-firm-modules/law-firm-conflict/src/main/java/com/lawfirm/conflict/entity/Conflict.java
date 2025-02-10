package com.lawfirm.conflict.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 利益冲突实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("conflict")
public class Conflict extends BaseEntity {
    
    /**
     * 冲突编号
     */
    private String conflictNo;
    
    /**
     * 冲突类型（1:当事人冲突、2:案件冲突、3:律师冲突、4:业务冲突）
     */
    private Integer conflictType;
    
    /**
     * 冲突描述
     */
    private String description;
    
    /**
     * 相关方A（可能是当事人ID、案件ID、律师ID等）
     */
    private String partyA;
    
    /**
     * 相关方B（可能是当事人ID、案件ID、律师ID等）
     */
    private String partyB;
    
    /**
     * 发现时间
     */
    private java.time.LocalDateTime discoveryTime;
    
    /**
     * 状态（1:待处理、2:处理中、3:已解决、4:已驳回）
     */
    private Integer status;
    
    /**
     * 处理人ID
     */
    private Long handlerId;
    
    /**
     * 处理意见
     */
    private String handleOpinion;
    
    /**
     * 处理时间
     */
    private java.time.LocalDateTime handleTime;
    
    /**
     * 风险等级（1:低、2:中、3:高）
     */
    private Integer riskLevel;
}
