package com.lawfirm.staff.model.request.clerk.task;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "任务创建请求")
public class TaskCreateRequest {

    /**
     * 任务标题
     */
    @NotBlank(message = "任务标题不能为空")
    @Schema(description = "任务标题")
    private String title;

    /**
     * 任务内容
     */
    @Schema(description = "任务内容")
    private String content;

    /**
     * 任务类型
     */
    @NotNull(message = "任务类型不能为空")
    @Schema(description = "任务类型")
    private Integer type;

    /**
     * 任务状态
     */
    @NotNull(message = "任务状态不能为空")
    @Schema(description = "任务状态")
    private Integer status;

    /**
     * 优先级
     */
    @NotNull(message = "优先级不能为空")
    @Schema(description = "优先级")
    private Integer priority;

    /**
     * 开始时间
     */
    @NotBlank(message = "开始时间不能为空")
    @Schema(description = "开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @NotBlank(message = "结束时间不能为空")
    @Schema(description = "结束时间")
    private String endTime;

    /**
     * 负责人ID
     */
    @NotNull(message = "负责人不能为空")
    @Schema(description = "负责人ID")
    private Long assigneeId;

    /**
     * 关联案件ID
     */
    @Schema(description = "关联案件ID")
    private Long matterId;

    /**
     * 参与人列表
     */
    @Schema(description = "参与人列表")
    private List<TaskParticipant> participants;

    /**
     * 附件列表
     */
    @Schema(description = "附件列表")
    private List<TaskAttachment> attachments;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    @Data
    @Schema(description = "任务参与人")
    public static class TaskParticipant {

        /**
         * 参与人ID
         */
        @NotNull(message = "参与人ID不能为空")
        @Schema(description = "参与人ID")
        private Long userId;

        /**
         * 参与角色
         */
        @Schema(description = "参与角色")
        private Integer role;

        /**
         * 备注
         */
        @Schema(description = "备注")
        private String remark;
    }

    @Data
    @Schema(description = "任务附件")
    public static class TaskAttachment {

        /**
         * 附件名称
         */
        @NotBlank(message = "附件名称不能为空")
        @Schema(description = "附件名称")
        private String name;

        /**
         * 附件类型
         */
        @Schema(description = "附件类型")
        private String type;

        /**
         * 附件大小
         */
        @Schema(description = "附件大小")
        private Long size;

        /**
         * 附件URL
         */
        @NotBlank(message = "附件URL不能为空")
        @Schema(description = "附件URL")
        private String url;
    }
} 