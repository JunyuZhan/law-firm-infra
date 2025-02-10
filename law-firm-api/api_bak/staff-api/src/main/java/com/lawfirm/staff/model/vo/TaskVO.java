package com.lawfirm.staff.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "任务视图对象")
public class TaskVO {

    @Schema(description = "任务ID")
    private Long id;

    @Schema(description = "任务标题")
    private String title;

    @Schema(description = "任务内容")
    private String content;

    @Schema(description = "任务类型")
    private Integer type;

    @Schema(description = "任务类型名称")
    private String typeName;

    @Schema(description = "任务状态")
    private Integer status;

    @Schema(description = "任务状态名称")
    private String statusName;

    @Schema(description = "优先级")
    private Integer priority;

    @Schema(description = "优先级名称")
    private String priorityName;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "负责人ID")
    private Long assigneeId;

    @Schema(description = "负责人姓名")
    private String assigneeName;

    @Schema(description = "创建人ID")
    private Long creatorId;

    @Schema(description = "创建人姓名")
    private String creatorName;

    @Schema(description = "关联案件ID")
    private Long matterId;

    @Schema(description = "关联案件名称")
    private String matterName;

    @Schema(description = "参与人列表")
    private List<TaskParticipant> participants;

    @Schema(description = "附件列表")
    private List<TaskAttachment> attachments;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;

    @Data
    @Schema(description = "任务参与人")
    public static class TaskParticipant {

        @Schema(description = "参与人ID")
        private Long userId;

        @Schema(description = "参与人姓名")
        private String userName;

        @Schema(description = "参与角色")
        private Integer role;

        @Schema(description = "参与角色名称")
        private String roleName;

        @Schema(description = "备注")
        private String remark;
    }

    @Data
    @Schema(description = "任务附件")
    public static class TaskAttachment {

        @Schema(description = "附件ID")
        private Long id;

        @Schema(description = "附件名称")
        private String name;

        @Schema(description = "附件类型")
        private String type;

        @Schema(description = "附件大小")
        private Long size;

        @Schema(description = "附件URL")
        private String url;

        @Schema(description = "上传人ID")
        private Long uploaderId;

        @Schema(description = "上传人姓名")
        private String uploaderName;

        @Schema(description = "上传时间")
        private String uploadTime;
    }
} 