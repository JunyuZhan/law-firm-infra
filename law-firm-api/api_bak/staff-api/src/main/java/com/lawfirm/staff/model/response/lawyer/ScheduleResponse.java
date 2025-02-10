package com.lawfirm.staff.model.response.lawyer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "日程响应")
public class ScheduleResponse {

    @Schema(description = "日程ID")
    private Long id;

    @Schema(description = "日程标题")
    private String title;

    @Schema(description = "日程内容")
    private String content;

    @Schema(description = "日程类型")
    private Integer type;

    @Schema(description = "日程类型名称")
    private String typeName;

    @Schema(description = "开始时间")
    private String startTime;

    @Schema(description = "结束时间")
    private String endTime;

    @Schema(description = "提醒时间")
    private String remindTime;

    @Schema(description = "关联案件ID")
    private Long matterId;

    @Schema(description = "关联案件名称")
    private String matterName;

    @Schema(description = "创建人ID")
    private Long creatorId;

    @Schema(description = "创建人姓名")
    private String creatorName;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;

    @Schema(description = "参与人列表")
    private List<ParticipantInfo> participants;

    @Data
    @Schema(description = "参与人信息")
    public static class ParticipantInfo {
        
        @Schema(description = "参与人ID")
        private Long id;

        @Schema(description = "参与人姓名")
        private String name;

        @Schema(description = "参与人角色")
        private String role;
    }
} 