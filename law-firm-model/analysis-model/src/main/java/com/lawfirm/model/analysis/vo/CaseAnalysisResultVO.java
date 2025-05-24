package com.lawfirm.model.analysis.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.vo.base.CaseQueryVO;
import com.lawfirm.model.client.vo.ClientVO;
import com.lawfirm.model.personnel.vo.PersonVO;
import com.lawfirm.model.contract.vo.ContractVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 案件分析结果VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "案件分析结果VO")
public class CaseAnalysisResultVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    @Schema(description = "案件ID")
    private Long caseId;
    @Schema(description = "案件类型")
    private String caseType;
    @Schema(description = "数量")
    private Integer total;
    @Schema(description = "案件简要信息")
    private CaseQueryVO caseInfo;
    @Schema(description = "客户信息")
    private ClientVO clientInfo;
    @Schema(description = "主办律师信息")
    private PersonVO mainLawyer;
    @Schema(description = "关联合同信息")
    private ContractVO contractInfo;
} 