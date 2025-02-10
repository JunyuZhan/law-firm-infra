package com.lawfirm.staff.model.request.clerk.supply;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "办公用品批量申请创建请求")
public class SupplyRequestBatchCreateRequest {

    @NotEmpty(message = "申请列表不能为空")
    @Valid
    @Schema(description = "申请列表")
    private List<SupplyRequestCreateRequest> requests;
} 