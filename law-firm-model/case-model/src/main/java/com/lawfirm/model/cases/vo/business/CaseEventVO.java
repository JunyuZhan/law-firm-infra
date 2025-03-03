package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.event.EventPriorityEnum;
import com.lawfirm.model.cases.enums.event.EventStatusEnum;
import com.lawfirm.model.cases.enums.event.EventTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.Duration;

/**
 * 案件事件视图对象
 * 
 * 包含事件的基本信息，如事件标题、类型、状态、时间等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseEventVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 事件标题
     */
    private String eventTitle;

    /**
     * 事件类型
     */
    private EventTypeEnum eventType;

    /**
     * 事件状态
     */
    private EventStatusEnum eventStatus;

    /**
     * 事件优先级
     */
    private EventPriorityEnum eventPriority;

    /**
     * 事件描述
     */
    private String eventDescription;

    /**
     * 开始时间
     */
    private transient LocalDateTime startTime;

    /**
     * 结束时间
     */
    private transient LocalDateTime endTime;

    /**
     * 是否全天事件
     */
    private Boolean isAllDay;

    /**
     * 地点
     */
    private String location;

    /**
     * 地址
     */
    private String address;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 组织者ID
     */
    private Long organizerId;

    /**
     * 组织者姓名
     */
    private String organizerName;

    /**
     * 参与人IDs（逗号分隔）
     */
    private String participantIds;

    /**
     * 参与人姓名（逗号分隔）
     */
    private String participantNames;

    /**
     * 是否需要提醒
     */
    private Boolean needReminder;

    /**
     * 提醒时间
     */
    private transient LocalDateTime reminderTime;

    /**
     * 提醒方式（1:系统消息, 2:邮件, 3:短信, 4:多种方式）
     */
    private Integer reminderMethod;

    /**
     * 是否已提醒
     */
    private Boolean isReminded;

    /**
     * 是否重复事件
     */
    private Boolean isRecurring;

    /**
     * 重复规则（iCalendar RRULE格式）
     */
    private String recurrenceRule;

    /**
     * 父事件ID（重复事件的原始事件）
     */
    private Long parentEventId;

    /**
     * 关联文档IDs（逗号分隔）
     */
    private String documentIds;

    /**
     * 关联任务IDs（逗号分隔）
     */
    private String taskIds;

    /**
     * 是否公开
     */
    private Boolean isPublic;

    /**
     * 是否已取消
     */
    private Boolean isCancelled;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 取消时间
     */
    private transient LocalDateTime cancelTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 判断事件是否已完成
     */
    public boolean isCompleted() {
        return this.eventStatus != null && 
               this.eventStatus == EventStatusEnum.COMPLETED;
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
    public CaseEventVO addParticipant(Long id, String name) {
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
    public CaseEventVO removeParticipant(Long id) {
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