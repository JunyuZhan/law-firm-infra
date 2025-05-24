package com.lawfirm.model.analysis.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.contract.vo.ContractVO;
import com.lawfirm.model.client.vo.ClientVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 财务分析结果VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "财务分析结果VO")
public class FinanceAnalysisResultVO extends BaseVO {
    private static final long serialVersionUID = 1L;
    /** 统计周期 */
    @Schema(description = "统计周期")
    private String period;
    /** 总收入 */
    @Schema(description = "总收入")
    private Double totalIncome;
    /** 总支出 */
    @Schema(description = "总支出")
    private Double totalExpense;
    /** 利润 */
    @Schema(description = "利润")
    private Double profit;
    /** 关联合同 */
    @Schema(description = "关联合同")
    private ContractVO contractInfo;
    /** 关联客户 */
    @Schema(description = "关联客户")
    private ClientVO clientInfo;
} 