package com.lawfirm.staff.model.response.clerk.meeting;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "会议参与者响应")
public class MeetingParticipantResponse {

    @Schema(description = "参与者ID")
    private Long id;

    @Schema(description = "参与者姓名")
    private String name;

    @Schema(description = "参与者类型")
    private Integer type;

    @Schema(description = "参与者类型名称")
    private String typeName;

    @Schema(description = "参与状态")
    private Integer status;

    @Schema(description = "参与状态名称")
    private String statusName;

    @Schema(description = "备注")
    private String remark;
} 