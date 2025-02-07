package com.lawfirm.staff.model.request.lawyer.client;

import com.lawfirm.model.base.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "客户分页查询请求")
public class ClientPageRequest extends PageQuery {

    @Schema(description = "客户名称")
    private String name;

    @Schema(description = "客户类型")
    private Integer type;

    @Schema(description = "客户状态")
    private Integer status;

    @Schema(description = "负责人ID")
    private Long ownerId;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "关键字")
    private String keyword;
} 