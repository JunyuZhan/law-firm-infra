package com.lawfirm.staff.model.request.finance.asset;

import com.lawfirm.common.data.query.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "资产分页查询请求")
public class AssetPageRequest extends PageQuery {

    @Schema(description = "资产编号")
    private String assetNo;

    @Schema(description = "资产名称")
    private String assetName;

    @Schema(description = "资产类型")
    private Integer type;

    @Schema(description = "资产状态")
    private Integer status;

    @Schema(description = "部门ID")
    private Long departmentId;

    @Schema(description = "使用人ID")
    private Long userId;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "关键字")
    private String keyword;
} 