package com.lawfirm.cases.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * 案件信息实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("case_info")
public class CaseInfo extends ModelBaseEntity<CaseInfo> {
    
    /**
     * 案件编号
     */
    private String caseNumber;
    
    /**
     * 案件名称
     */
    private String caseName;
    
    /**
     * 案件类型
     */
    private String caseType;
    
    /**
     * 案件状态
     */
    private String caseStatus;
    
    /**
     * 委托人ID
     */
    private Long clientId;
    
    /**
     * 委托人名称
     */
    private String clientName;
    
    /**
     * 主办律师ID
     */
    private Long lawyerId;
    
    /**
     * 主办律师名称
     */
    private String lawyerName;
    
    /**
     * 协办律师IDs
     */
    private String assistLawyerIds;
    
    /**
     * 协办律师名称
     */
    private String assistLawyerNames;
    
    /**
     * 案件受理时间
     */
    private LocalDateTime acceptTime;
    
    /**
     * 预计结案时间
     */
    private LocalDateTime expectedEndTime;
    
    /**
     * 实际结案时间
     */
    private LocalDateTime actualEndTime;
    
    /**
     * 案件标的额
     */
    private BigDecimal caseAmount;
    
    /**
     * 案件收费
     */
    private BigDecimal caseFee;
    
    /**
     * 案件描述
     */
    private String caseDescription;
    
    /**
     * 案件备注
     */
    private String remarks;
} 