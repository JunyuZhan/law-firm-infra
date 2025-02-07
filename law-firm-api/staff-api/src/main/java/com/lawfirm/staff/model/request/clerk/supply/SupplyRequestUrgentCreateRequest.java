package com.lawfirm.staff.model.request.clerk.supply;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "办公用品紧急申请创建请求")
public class SupplyRequestUrgentCreateRequest {

    @NotBlank(message = "申请标题不能为空")
    @Schema(description = "申请标题")
    private String title;

    @NotBlank(message = "紧急原因不能为空")
    @Schema(description = "紧急原因")
    private String urgentReason;

    @Schema(description = "申请描述")
    private String description;

    @NotNull(message = "申请类型不能为空")
    @Schema(description = "申请类型")
    private Integer type;

    @Schema(description = "期望交付日期")
    private String expectedDeliveryDate;

    @NotEmpty(message = "申请物品不能为空")
    @Valid
    @Schema(description = "申请物品列表")
    private List<SupplyRequestItemCreateRequest> items;

    @Schema(description = "备注")
    private String remark;
} 