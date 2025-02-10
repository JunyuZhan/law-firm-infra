package com.lawfirm.staff.model.response.clerk.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "会议参会人员响应")
public class MeetingParticipantResponse {

    @Schema(description = "参会人ID")
    private Long userId;

    @Schema(description = "参会人姓名")
    private String userName;

    @Schema(description = "参会角色")
    private Integer role;

    @Schema(description = "参会角色名称")
    private String roleName;

    @Schema(description = "备注")
    private String remark;
} 