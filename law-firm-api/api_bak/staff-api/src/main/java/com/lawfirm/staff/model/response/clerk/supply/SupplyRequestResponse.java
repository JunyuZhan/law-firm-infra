package com.lawfirm.staff.model.response.clerk.supply;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "办公用品申请响应")
public class SupplyRequestResponse {

    @Schema(description = "申请ID")
    private Long id;

    @Schema(description = "申请标题")
    private String title;

    @Schema(description = "申请描述")
    private String description;

    @Schema(description = "申请类型")
    private Integer type;

    @Schema(description = "申请状态")
    private Integer status;

    @Schema(description = "是否紧急")
    private Boolean urgent;

    @Schema(description = "紧急原因")
    private String urgentReason;

    @Schema(description = "期望交付日期")
    private String expectedDeliveryDate;

    @Schema(description = "预计交付日期")
    private String estimatedDeliveryDate;

    @Schema(description = "申请人ID")
    private Long requesterId;

    @Schema(description = "申请人姓名")
    private String requesterName;

    @Schema(description = "审批人ID")
    private Long approverId;

    @Schema(description = "审批人姓名")
    private String approverName;

    @Schema(description = "审批时间")
    private String approveTime;

    @Schema(description = "审批意见")
    private String approveComment;

    @Schema(description = "驳回原因")
    private String rejectReason;

    @Schema(description = "申请物品列表")
    private List<SupplyRequestItemResponse> items;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;
}

@Data
@Schema(description = "办公用品申请物品响应")
class SupplyRequestItemResponse {

    @Schema(description = "物品ID")
    private Long supplyId;

    @Schema(description = "物品名称")
    private String supplyName;

    @Schema(description = "物品类型")
    private Integer supplyType;

    @Schema(description = "申请数量")
    private Integer quantity;

    @Schema(description = "备注")
    private String remark;
} 