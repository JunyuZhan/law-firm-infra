package com.lawfirm.staff.model.request.clerk.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "会议室维护信息更新请求")
public class MeetingRoomMaintenanceUpdateRequest {

    @NotNull(message = "维护类型不能为空")
    @Schema(description = "维护类型")
    private Integer maintenanceType;

    @NotBlank(message = "维护内容不能为空")
    @Schema(description = "维护内容")
    private String maintenanceContent;

    @Schema(description = "维护人员")
    private String maintenanceStaff;

    @Schema(description = "维护时间")
    private String maintenanceTime;

    @Schema(description = "下次维护时间")
    private String nextMaintenanceTime;

    @Schema(description = "维护结果")
    private String maintenanceResult;

    @Schema(description = "备注")
    private String remark;
} 