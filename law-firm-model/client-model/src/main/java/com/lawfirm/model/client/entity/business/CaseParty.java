package com.lawfirm.model.client.entity.business;

import com.lawfirm.model.client.entity.base.ClientRelation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 案件当事人
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CaseParty extends ClientRelation {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 当事人类型 1-原告 2-被告 3-第三人
     */
    private Integer partyType;

    /**
     * 当事人角色
     */
    private String partyRole;

    /**
     * 代理类型 1-特别授权 2-一般授权
     */
    private Integer agentType;

    /**
     * 委托时间
     */
    private LocalDateTime entrustTime;

    /**
     * 代理权限（JSON格式）
     */
    private String agentAuthorities;

    /**
     * 案件状态 0-未开始 1-进行中 2-已结束
     */
    private Integer caseStatus;
} 
