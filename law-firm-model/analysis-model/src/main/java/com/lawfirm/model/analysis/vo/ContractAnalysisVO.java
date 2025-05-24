package com.lawfirm.model.analysis.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.contract.vo.ContractVO;
import com.lawfirm.model.client.vo.ClientVO;
import com.lawfirm.model.personnel.vo.PersonVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 合同分析VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "合同分析结果VO")
public class ContractAnalysisVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    /** 合同信息 */
    @Schema(description = "合同信息")
    private ContractVO contract;
    /** 客户信息 */
    @Schema(description = "客户信息")
    private ClientVO client;
    /** 主办律师信息 */
    @Schema(description = "主办律师信息")
    private PersonVO leadAttorney;
    /** 合同金额 */
    @Schema(description = "合同金额")
    private Double amount;
    /** 签约日期 */
    @Schema(description = "签约日期")
    private String signingDate;
} 