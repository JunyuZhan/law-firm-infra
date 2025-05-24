package com.lawfirm.model.analysis.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.client.vo.ClientVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户分析VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "客户分析结果VO")
public class ClientAnalysisVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    @Schema(description = "客户信息")
    private ClientVO client;
    @Schema(description = "贡献度")
    private Double contribution;
    @Schema(description = "风险等级")
    private String riskLevel;
    @Schema(description = "满意度")
    private Double satisfaction;
    @Schema(description = "关联案件数")
    private Integer caseCount;
    @Schema(description = "分析维度值（如行业、活跃度、风险等级等）")
    private String dimensionValue;
    @Schema(description = "客户数量")
    private Integer clientCount;
    @Schema(description = "占比（0-1）")
    private Double ratio;
} 