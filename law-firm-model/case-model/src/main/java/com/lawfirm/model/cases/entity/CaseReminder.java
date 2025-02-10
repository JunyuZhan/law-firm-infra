package com.lawfirm.model.cases.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

/**
 * 案件提醒实体类
 */
@Data
@TableName("case_reminder")
public class CaseReminder {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 案件ID
     */
    @Comment("案件ID")
    private Long caseId;

    /**
     * 提醒类型（1-开庭 2-截止日期 3-重要节点）
     */
    @Comment("提醒类型")
    private ReminderType type;

    /**
     * 提醒标题
     */
    @Comment("提醒标题")
    private String title;

    /**
     * 提醒内容
     */
    @Comment("提醒内容")
    private String content;

    /**
     * 提醒时间
     */
    @Comment("提醒时间")
    private LocalDateTime reminderTime;

    /**
     * 提前提醒时间(分钟)
     */
    @Comment("提前提醒时间(分钟)")
    private Integer advanceTime = 30;

    /**
     * 是否重复提醒
     */
    @Comment("是否重复提醒")
    private Boolean repeat = false;

    /**
     * 重复类型（每天、每周、每月、每年）
     */
    @Comment("重复类型（每天、每周、每月、每年）")
    private String repeatType;

    /**
     * 重复规则
     */
    @Comment("重复规则")
    private String repeatRule;

    /**
     * 重复结束时间
     */
    @Comment("重复结束时间")
    private LocalDateTime repeatEndTime;

    /**
     * 接收人ID
     */
    @Comment("接收人ID")
    private Long receiverId;

    /**
     * 接收人姓名
     */
    @Comment("接收人姓名")
    private String receiverName;

    /**
     * 抄送人ID列表，逗号分隔
     */
    @Comment("抄送人ID列表，逗号分隔")
    private String ccList;

    /**
     * 提醒状态（0-未提醒 1-已提醒 2-已确认）
     */
    @Comment("提醒状态")
    private ReminderStatus status = ReminderStatus.PENDING;

    /**
     * 确认时间
     */
    @Comment("确认时间")
    private LocalDateTime confirmTime;

    /**
     * 确认备注
     */
    @Comment("确认备注")
    private String confirmRemark;

    /**
     * 是否需要确认
     */
    @Comment("是否需要确认")
    private Boolean needConfirm = true;

    /**
     * 是否已发送提醒
     */
    @Comment("是否已发送提醒")
    private Boolean sent = false;

    /**
     * 最后发送时间
     */
    @Comment("最后发送时间")
    private LocalDateTime lastSentTime;

    /**
     * 发送次数
     */
    @Comment("发送次数")
    private Integer sentCount = 0;

    /**
     * 优先级（1-低，2-中，3-高）
     */
    @Comment("优先级（1-低，2-中，3-高）")
    private Integer priority = 2;

    /**
     * 关联事项类型
     */
    @Comment("关联事项类型")
    private String relatedItemType;

    /**
     * 关联事项ID
     */
    @Comment("关联事项ID")
    private String relatedItemId;

    /**
     * 是否已过期
     */
    @Comment("是否已过期")
    private Boolean expired = false;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;

    public enum ReminderType {
        COURT_DATE("开庭日期"),
        DEADLINE("截止日期"),
        MILESTONE("重要节点"),
        DOCUMENT("文书提交"),
        MEETING("会议安排"),
        TASK("任务提醒"),
        PAYMENT("费用提醒"),
        OTHER("其他");

        private final String description;

        ReminderType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public enum ReminderStatus {
        PENDING("待提醒"),
        SENT("已提醒"),
        CONFIRMED("已确认"),
        CANCELLED("已取消"),
        EXPIRED("已过期");

        private final String description;

        ReminderStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
} 