package com.lawfirm.staff.model.request.lawyer.cases;

import com.lawfirm.common.data.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "案件分页查询请求")
public class CasePageRequest extends PageQuery {

    @Schema(description = "案件编号")
    private String caseNo;

    @Schema(description = "案件名称")
    private String caseName;

    @Schema(description = "案件类型")
    private Integer type;

    @Schema(description = "案件状态")
    private Integer status;

    @Schema(description = "客户ID")
    private Long clientId;

    @Schema(description = "负责人ID")
    private Long ownerId;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "关键字")
    private String keyword;
} 