package com.lawfirm.staff.model.request.clerk.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "任务更新请求")
public class TaskUpdateRequest {

    @NotNull(message = "任务ID不能为空")
    @Schema(description = "任务ID")
    private Long id;

    @NotBlank(message = "任务标题不能为空")
    @Schema(description = "任务标题")
    private String title;

    @Schema(description = "任务内容")
    private String content;

    @NotNull(message = "任务类型不能为空")
    @Schema(description = "任务类型")
    private Integer type;

    @NotNull(message = "任务状态不能为空")
    @Schema(description = "任务状态")
    private Integer status;

    @NotNull(message = "优先级不能为空")
    @Schema(description = "优先级")
    private Integer priority;

    @NotBlank(message = "开始时间不能为空")
    @Schema(description = "开始时间")
    private String startTime;

    @NotBlank(message = "结束时间不能为空")
    @Schema(description = "结束时间")
    private String endTime;

    @NotNull(message = "负责人不能为空")
    @Schema(description = "负责人ID")
    private Long assigneeId;

    @Schema(description = "关联案件ID")
    private Long matterId;

    @Schema(description = "参与人列表")
    private List<TaskParticipant> participants;

    @Schema(description = "附件列表")
    private List<TaskAttachment> attachments;

    @Schema(description = "备注")
    private String remark;

    @Data
    @Schema(description = "任务参与人")
    public static class TaskParticipant {

        @NotNull(message = "参与人ID不能为空")
        @Schema(description = "参与人ID")
        private Long userId;

        @Schema(description = "参与角色")
        private Integer role;

        @Schema(description = "备注")
        private String remark;
    }

    @Data
    @Schema(description = "任务附件")
    public static class TaskAttachment {

        @NotBlank(message = "附件名称不能为空")
        @Schema(description = "附件名称")
        private String name;

        @Schema(description = "附件类型")
        private String type;

        @Schema(description = "附件大小")
        private Long size;

        @NotBlank(message = "附件URL不能为空")
        @Schema(description = "附件URL")
        private String url;
    }
} 