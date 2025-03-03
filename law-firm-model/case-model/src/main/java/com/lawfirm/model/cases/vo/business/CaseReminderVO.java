package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.reminder.ReminderStatusEnum;
import com.lawfirm.model.cases.enums.reminder.ReminderTypeEnum;
import com.lawfirm.model.cases.enums.reminder.RepeatTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 案件提醒视图对象
 * 
 * 包含提醒的基本信息，如提醒标题、内容、时间、接收人等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseReminderVO extends BaseVO {

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
     * 提醒标题
     */
    private String reminderTitle;

    /**
     * 提醒内容
     */
    private String reminderContent;

    /**
     * 提醒类型
     */
    private ReminderTypeEnum reminderType;

    /**
     * 提醒状态
     */
    private ReminderStatusEnum reminderStatus;

    /**
     * 提醒时间
     */
    private transient LocalDateTime reminderTime;

    /**
     * 提前提醒时间（分钟）
     */
    private Integer advanceMinutes;

    /**
     * 是否重复
     */
    private Boolean isRecurring;

    /**
     * 重复类型
     */
    private RepeatTypeEnum repeatType;

    /**
     * 重复规则（iCalendar RRULE格式）
     */
    private String recurrenceRule;

    /**
     * 重复结束时间
     */
    private transient LocalDateTime recurrenceEndTime;

    /**
     * 重复次数
     */
    private Integer recurrenceTimes;

    /**
     * 主要接收人ID
     */
    private Long receiverId;

    /**
     * 主要接收人姓名
     */
    private String receiverName;

    /**
     * 抄送人IDs（逗号分隔）
     */
    private String ccReceiverIds;

    /**
     * 抄送人姓名（逗号分隔）
     */
    private String ccReceiverNames;

    /**
     * 提醒方式（1:系统消息, 2:邮件, 3:短信, 4:多种方式）
     */
    private Integer reminderMethod;

    /**
     * 是否已发送
     */
    private Boolean isSent;

    /**
     * 发送时间
     */
    private transient LocalDateTime sentTime;

    /**
     * 发送次数
     */
    private Integer sentTimes;

    /**
     * 是否已确认
     */
    private Boolean isConfirmed;

    /**
     * 确认时间
     */
    private transient LocalDateTime confirmTime;

    /**
     * 确认备注
     */
    private String confirmRemarks;

    /**
     * 关联事项类型（1:任务, 2:事件, 3:文档, 4:期限, 5:其他）
     */
    private Integer relatedItemType;

    /**
     * 关联事项ID
     */
    private Long relatedItemId;

    /**
     * 关联事项名称
     */
    private String relatedItemName;

    /**
     * 重要程度（1:普通, 2:重要, 3:紧急）
     */
    private Integer importance;

    /**
     * 创建人ID
     */
    private Long creatorId;

    /**
     * 创建人姓名
     */
    private String creatorName;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 获取提醒方式名称
     */
    public String getReminderMethodName() {
        if (this.reminderMethod == null) {
            return "未知";
        }
        
        switch (this.reminderMethod) {
            case 1:
                return "系统消息";
            case 2:
                return "邮件";
            case 3:
                return "短信";
            case 4:
                return "多种方式";
            default:
                return "未知";
        }
    }

    /**
     * 获取关联事项类型名称
     */
    public String getRelatedItemTypeName() {
        if (this.relatedItemType == null) {
            return "未知";
        }
        
        switch (this.relatedItemType) {
            case 1:
                return "任务";
            case 2:
                return "事件";
            case 3:
                return "文档";
            case 4:
                return "期限";
            case 5:
                return "其他";
            default:
                return "未知";
        }
    }

    /**
     * 获取重要程度名称
     */
    public String getImportanceName() {
        if (this.importance == null) {
            return "普通";
        }
        
        switch (this.importance) {
            case 1:
                return "普通";
            case 2:
                return "重要";
            case 3:
                return "紧急";
            default:
                return "普通";
        }
    }

    /**
     * 获取抄送人ID数组
     */
    public Long[] getCcReceiverIdArray() {
        if (this.ccReceiverIds == null || this.ccReceiverIds.isEmpty()) {
            return new Long[0];
        }
        
        String[] idStrings = this.ccReceiverIds.split(",");
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
     * 获取抄送人姓名数组
     */
    public String[] getCcReceiverNameArray() {
        if (this.ccReceiverNames == null || this.ccReceiverNames.isEmpty()) {
            return new String[0];
        }
        
        return this.ccReceiverNames.split(",");
    }

    /**
     * 添加抄送人
     */
    public CaseReminderVO addCcReceiver(Long id, String name) {
        if (id == null) {
            return this;
        }
        
        // 处理抄送人ID
        if (this.ccReceiverIds == null || this.ccReceiverIds.isEmpty()) {
            this.ccReceiverIds = id.toString();
        } else if (!this.ccReceiverIds.contains(id.toString())) {
            this.ccReceiverIds += "," + id;
        }
        
        // 处理抄送人姓名
        if (name != null && !name.isEmpty()) {
            if (this.ccReceiverNames == null || this.ccReceiverNames.isEmpty()) {
                this.ccReceiverNames = name;
            } else if (!this.ccReceiverNames.contains(name)) {
                this.ccReceiverNames += "," + name;
            }
        }
        
        return this;
    }

    /**
     * 移除抄送人
     */
    public CaseReminderVO removeCcReceiver(Long id) {
        if (id == null || this.ccReceiverIds == null || this.ccReceiverIds.isEmpty()) {
            return this;
        }
        
        Long[] ids = getCcReceiverIdArray();
        String[] names = getCcReceiverNameArray();
        
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
        
        this.ccReceiverIds = newIds.toString();
        this.ccReceiverNames = newNames.toString();
        
        return this;
    }

    /**
     * 判断是否已过期
     */
    public boolean isExpired() {
        return this.reminderTime != null && 
               this.reminderTime.isBefore(LocalDateTime.now()) && 
               !Boolean.TRUE.equals(this.isConfirmed);
    }

    /**
     * 判断是否即将到期
     */
    public boolean isUpcoming() {
        if (this.reminderTime == null || Boolean.TRUE.equals(this.isConfirmed)) {
            return false;
        }
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime upcoming = now.plusHours(24);
        
        return this.reminderTime.isAfter(now) && this.reminderTime.isBefore(upcoming);
    }

    /**
     * 判断是否需要立即提醒
     */
    public boolean needRemindNow() {
        if (this.reminderTime == null || Boolean.TRUE.equals(this.isSent) || Boolean.TRUE.equals(this.isConfirmed)) {
            return false;
        }
        
        LocalDateTime now = LocalDateTime.now();
        
        if (this.advanceMinutes != null && this.advanceMinutes > 0) {
            LocalDateTime advanceTime = this.reminderTime.minusMinutes(this.advanceMinutes);
            return now.isAfter(advanceTime) || now.isEqual(advanceTime);
        } else {
            return now.isAfter(this.reminderTime) || now.isEqual(this.reminderTime);
        }
    }
} 