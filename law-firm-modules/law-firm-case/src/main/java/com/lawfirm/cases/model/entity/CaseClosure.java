package com.lawfirm.cases.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lawfirm.common.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 案件结案记录实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("case_closure")
public class CaseClosure extends BaseEntity {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    
    /**
     * 案件ID
     */
    private Long caseId;
    
    /**
     * 结案人ID
     */
    private Long closerId;
    
    /**
     * 结案人姓名
     */
    private String closerName;
    
    /**
     * 结案时间
     */
    private LocalDateTime closureTime;
    
    /**
     * 结案方式
     */
    private String closureMethod;
    
    /**
     * 结案原因
     */
    private String closureReason;
    
    /**
     * 实际收费金额
     */
    private BigDecimal actualFee;
    
    /**
     * 结案总结
     */
    private String summary;
    
    /**
     * 备注
     */
    private String remarks;
} 