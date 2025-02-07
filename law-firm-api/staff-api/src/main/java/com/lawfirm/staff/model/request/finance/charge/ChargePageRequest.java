package com.lawfirm.staff.model.request.finance.charge;

import com.lawfirm.staff.model.base.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "费用分页查询请求")
public class ChargePageRequest extends PageQuery {

    @Schema(description = "关键字(费用名称)")
    private String keyword;

    @Schema(description = "费用类型")
    private Integer type;

    @Schema(description = "费用状态")
    private Integer status;

    @Schema(description = "最小金额")
    private BigDecimal minAmount;

    @Schema(description = "最大金额")
    private BigDecimal maxAmount;

    @Schema(description = "关联案件ID")
    private Long caseId;

    @Schema(description = "关联客户ID")
    private Long clientId;

    @Schema(description = "开始创建时间")
    private String createTimeStart;

    @Schema(description = "结束创建时间")
    private String createTimeEnd;
} 