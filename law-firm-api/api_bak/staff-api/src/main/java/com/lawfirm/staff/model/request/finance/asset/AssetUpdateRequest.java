package com.lawfirm.staff.model.request.finance.asset;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Schema(description = "更新资产请求")
public class AssetUpdateRequest {

    @Schema(description = "资产ID")
    @NotNull(message = "资产ID不能为空")
    private Long id;

    @Schema(description = "资产名称")
    @NotBlank(message = "资产名称不能为空")
    private String name;

    @Schema(description = "资产类型")
    @NotNull(message = "资产类型不能为空")
    private Integer type;

    @Schema(description = "资产编号")
    @NotBlank(message = "资产编号不能为空")
    private String code;

    @Schema(description = "资产价值")
    @NotNull(message = "资产价值不能为空")
    private BigDecimal value;

    @Schema(description = "购买日期")
    @NotBlank(message = "购买日期不能为空")
    private String purchaseDate;

    @Schema(description = "使用部门ID")
    @NotNull(message = "使用部门不能为空")
    private Long departmentId;

    @Schema(description = "使用人ID")
    private Long userId;

    @Schema(description = "资产状态")
    @NotNull(message = "资产状态不能为空")
    private Integer status;

    @Schema(description = "资产描述")
    private String description;

    @Schema(description = "备注")
    private String remark;
} 