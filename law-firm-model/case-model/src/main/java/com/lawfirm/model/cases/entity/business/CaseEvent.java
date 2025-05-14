package com.lawfirm.model.cases.entity.business;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.enums.event.EventPriorityEnum;
import com.lawfirm.model.cases.enums.event.EventStatusEnum;
import com.lawfirm.model.cases.enums.event.EventTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.time.Duration;

/**
 * 案件事件实体类
 * 
 * 案件事件记录了与案件相关的各类事件信息，包括事件基本信息、
 * 时间信息、地点信息、参与人信息、提醒信息等。
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("case_event")
@Schema(description = "案件事件实体类")
public class CaseEvent extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    @Schema(description = "案件ID")
    @TableField("case_id")
    private Long caseId;

    /**
     * 案件编号
     */
    @Schema(description = "案件编号")
    @TableField("case_number")
    private String caseNumber;

    /**
     * 事件标题
     */
    @Schema(description = "事件标题")
    @TableField("event_title")
    private String eventTitle;

    /**
     * 事件类型
     */
    @Schema(description = "事件类型")
    @TableField("event_type")
    private Integer eventType;

    /**
     * 事件状态
     */
    @Schema(description = "事件状态")
    @TableField("event_status")
    private Integer eventStatus;

    /**
     * 事件优先级
     */
    @Schema(description = "事件优先级")
    @TableField("event_priority")
    private Integer eventPriority;

    /**
     * 事件描述
     */
    @Schema(description = "事件描述")
    @TableField("event_description")
    private String eventDescription;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @TableField("start_time")
    private transient LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    @TableField("end_time")
    private transient LocalDateTime endTime;

    /**
     * 是否全天事件
     */
    @Schema(description = "是否全天事件")
    @TableField("is_all_day")
    private Boolean isAllDay;

    /**
     * 地点
     */
    @Schema(description = "地点")
    @TableField("location")
    private String location;

    /**
     * 地址
     */
    @Schema(description = "地址")
    @TableField("address")
    private String address;

    /**
     * 经度
     */
    @Schema(description = "经度")
    @TableField("longitude")
    private Double longitude;

    /**
     * 纬度
     */
    @Schema(description = "纬度")
    @TableField("latitude")
    private Double latitude;

    /**
     * 组织者ID
     */
    @Schema(description = "组织者ID")
    @TableField("organizer_id")
    private Long organizerId;

    /**
     * 组织者姓名
     */
    @Schema(description = "组织者姓名")
    @TableField("organizer_name")
    private String organizerName;

    /**
     * 参与人IDs（逗号分隔）
     */
    @Schema(description = "参与人IDs（逗号分隔）")
    @TableField("participant_ids")
    private String participantIds;

    /**
     * 参与人姓名（逗号分隔）
     */
    @Schema(description = "参与人姓名（逗号分隔）")
    @TableField("participant_names")
    private String participantNames;

    /**
     * 是否需要提醒
     */
    @Schema(description = "是否需要提醒")
    @TableField("need_reminder")
    private Boolean needReminder;

    /**
     * 提醒时间
     */
    @Schema(description = "提醒时间")
    @TableField("reminder_time")
    private transient LocalDateTime reminderTime;

    /**
     * 提醒方式（1:系统消息, 2:邮件, 3:短信, 4:多种方式）
     */
    @Schema(description = "提醒方式（1:系统消息, 2:邮件, 3:短信, 4:多种方式）")
    @TableField("reminder_method")
    private Integer reminderMethod;

    /**
     * 是否已提醒
     */
    @Schema(description = "是否已提醒")
    @TableField("is_reminded")
    private Boolean isReminded;

    /**
     * 是否重复事件
     */
    @Schema(description = "是否重复事件")
    @TableField("is_recurring")
    private Boolean isRecurring;

    /**
     * 重复规则（iCalendar RRULE格式）
     */
    @Schema(description = "重复规则（iCalendar RRULE格式）")
    @TableField("recurrence_rule")
    private String recurrenceRule;

    /**
     * 父事件ID（重复事件的原始事件）
     */
    @Schema(description = "父事件ID（重复事件的原始事件）")
    @TableField("parent_event_id")
    private Long parentEventId;

    /**
     * 关联文档IDs（逗号分隔）
     */
    @Schema(description = "关联文档IDs（逗号分隔）")
    @TableField("document_ids")
    private String documentIds;

    /**
     * 关联任务IDs（逗号分隔）
     */
    @Schema(description = "关联任务IDs（逗号分隔）")
    @TableField("task_ids")
    private String taskIds;

    /**
     * 是否公开
     */
    @Schema(description = "是否公开")
    @TableField("is_public")
    private Boolean isPublic;

    /**
     * 是否已取消
     */
    @Schema(description = "是否已取消")
    @TableField("is_cancelled")
    private Boolean isCancelled;

    /**
     * 取消原因
     */
    @Schema(description = "取消原因")
    @TableField("cancel_reason")
    private String cancelReason;

    /**
     * 取消时间
     */
    @Schema(description = "取消时间")
    @TableField("cancel_time")
    private transient LocalDateTime cancelTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @TableField("remarks")
    private String remarks;

    /**
     * 获取事件类型枚举
     */
    public EventTypeEnum getEventTypeEnum() {
        return EventTypeEnum.valueOf(this.eventType);
    }

    /**
     * 设置事件类型
     */
    public CaseEvent setEventTypeEnum(EventTypeEnum eventTypeEnum) {
        this.eventType = eventTypeEnum != null ? eventTypeEnum.getValue() : null;
        return this;
    }

    /**
     * 获取事件状态枚举
     */
    public EventStatusEnum getEventStatusEnum() {
        return EventStatusEnum.valueOf(this.eventStatus);
    }

    /**
     * 设置事件状态
     */
    public CaseEvent setEventStatusEnum(EventStatusEnum eventStatusEnum) {
        this.eventStatus = eventStatusEnum != null ? eventStatusEnum.getValue() : null;
        return this;
    }

    /**
     * 获取事件优先级枚举
     */
    public EventPriorityEnum getEventPriorityEnum() {
        return EventPriorityEnum.valueOf(this.eventPriority);
    }

    /**
     * 设置事件优先级
     */
    public CaseEvent setEventPriorityEnum(EventPriorityEnum eventPriorityEnum) {
        this.eventPriority = eventPriorityEnum != null ? eventPriorityEnum.getValue() : null;
        return this;
    }

    /**
     * 判断事件是否已完成
     */
    public boolean isCompleted() {
        return this.eventStatus != null && 
               this.getEventStatusEnum() == EventStatusEnum.COMPLETED;
    }

    /**
     * 判断事件是否已取消
     */
    public boolean isCancelled() {
        return Boolean.TRUE.equals(this.isCancelled);
    }

    /**
     * 判断事件是否进行中
     */
    public boolean isInProgress() {
        LocalDateTime now = LocalDateTime.now();
        return !isCancelled() && 
               startTime != null && endTime != null && 
               !startTime.isAfter(now) && !endTime.isBefore(now);
    }

    /**
     * 判断事件是否即将开始
     */
    public boolean isUpcoming() {
        LocalDateTime now = LocalDateTime.now();
        return !isCancelled() && 
               startTime != null && startTime.isAfter(now);
    }

    /**
     * 判断事件是否已过期
     */
    public boolean isExpired() {
        LocalDateTime now = LocalDateTime.now();
        return !isCancelled() && 
               endTime != null && endTime.isBefore(now);
    }

    /**
     * 获取事件持续时间（分钟）
     */
    public Long getDurationMinutes() {
        if (startTime == null || endTime == null) {
            return null;
        }
        return Duration.between(startTime, endTime).toMinutes();
    }

    /**
     * 获取事件持续时间的格式化字符串
     */
    public String getFormattedDuration() {
        if (startTime == null || endTime == null) {
            return "";
        }
        
        Duration duration = Duration.between(startTime, endTime);
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        
        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append("天");
        }
        if (hours > 0 || days > 0) {
            sb.append(hours).append("小时");
        }
        sb.append(minutes).append("分钟");
        
        return sb.toString();
    }

    /**
     * 获取距离事件开始的剩余时间（分钟）
     */
    public Long getMinutesUntilStart() {
        if (startTime == null) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(startTime)) {
            return 0L;
        }
        return Duration.between(now, startTime).toMinutes();
    }

    /**
     * 获取参与人ID数组
     */
    public Long[] getParticipantIdArray() {
        if (participantIds == null || participantIds.isEmpty()) {
            return new Long[0];
        }
        
        String[] idStrings = participantIds.split(",");
        Long[] ids = new Long[idStrings.length];
        
        for (int i = 0; i < idStrings.length; i++) {
            try {
                ids[i] = Long.parseLong(idStrings[i].trim());
            } catch (NumberFormatException e) {
                ids[i] = null;
            }
        }
        
        return ids;
    }

    /**
     * 获取参与人姓名数组
     */
    public String[] getParticipantNameArray() {
        if (participantNames == null || participantNames.isEmpty()) {
            return new String[0];
        }
        
        return participantNames.split(",");
    }

    /**
     * 添加参与人
     */
    public CaseEvent addParticipant(Long id, String name) {
        if (id == null) {
            return this;
        }
        
        // 处理参与人ID
        if (participantIds == null || participantIds.isEmpty()) {
            participantIds = id.toString();
        } else if (!participantIds.contains(id.toString())) {
            participantIds += "," + id;
        }
        
        // 处理参与人姓名
        if (name != null && !name.isEmpty()) {
            if (participantNames == null || participantNames.isEmpty()) {
                participantNames = name;
            } else if (!participantNames.contains(name)) {
                participantNames += "," + name;
            }
        }
        
        return this;
    }

    /**
     * 移除参与人
     */
    public CaseEvent removeParticipant(Long id) {
        if (id == null || participantIds == null || participantIds.isEmpty()) {
            return this;
        }
        
        Long[] ids = getParticipantIdArray();
        String[] names = getParticipantNameArray();
        
        StringBuilder newIds = new StringBuilder();
        StringBuilder newNames = new StringBuilder();
        
        for (int i = 0; i < ids.length; i++) {
            if (!id.equals(ids[i])) {
                if (newIds.length() > 0) {
                    newIds.append(",");
                }
                newIds.append(ids[i]);
                
                if (i < names.length) {
                    if (newNames.length() > 0) {
                        newNames.append(",");
                    }
                    newNames.append(names[i]);
                }
            }
        }
        
        participantIds = newIds.toString();
        participantNames = newNames.toString();
        
        return this;
    }
} 