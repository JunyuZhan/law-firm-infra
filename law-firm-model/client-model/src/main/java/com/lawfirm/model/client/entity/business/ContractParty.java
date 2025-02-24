package com.lawfirm.model.client.entity.business;

import com.lawfirm.model.client.entity.base.ClientRelation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 合同当事人
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ContractParty extends ClientRelation {

    /**
     * 合同ID
     */
    private Long contractId;

    /**
     * 当事人类型 1-甲方 2-乙方 3-丙方
     */
    private Integer partyType;

    /**
     * 签约角色
     */
    private String signRole;

    /**
     * 签约资格 0-无效 1-有效
     */
    private Integer signQualification;

    /**
     * 签约时间
     */
    private LocalDateTime signTime;

    /**
     * 履约状态 0-未履约 1-履约中 2-已履约 3-违约
     */
    private Integer performanceStatus;

    /**
     * 合同权利（JSON格式）
     */
    private String contractRights;

    /**
     * 合同义务（JSON格式）
     */
    private String contractObligations;
} 