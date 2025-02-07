package com.lawfirm.staff.model.request.clerk.supply;

import com.lawfirm.model.base.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "办公用品申请分页查询请求")
public class SupplyRequestPageRequest extends PageQuery {

    @Schema(description = "申请标题")
    private String title;

    @Schema(description = "申请状态")
    private Integer status;

    @Schema(description = "申请类型")
    private Integer type;

    @Schema(description = "申请人ID")
    private Long requesterId;

    @Schema(description = "审批人ID")
    private Long approverId;

    @Schema(description = "是否紧急")
    private Boolean urgent;

    @Schema(description = "创建开始时间")
    private String createTimeStart;

    @Schema(description = "创建结束时间")
    private String createTimeEnd;
} 