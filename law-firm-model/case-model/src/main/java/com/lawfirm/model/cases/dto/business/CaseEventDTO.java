package com.lawfirm.model.cases.dto.business;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件事件数据传输对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseEventDTO extends BaseDTO {

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
     * 事件类型
     */
    private Integer eventType;

    /**
     * 事件标题
     */
    private String eventTitle;

    /**
     * 事件内容
     */
    private String eventContent;

    /**
     * 事件状态
     */
    private Integer eventStatus;

    /**
     * 事件优先级
     */
    private Integer eventPriority;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 地点
     */
    private String location;

    /**
     * 负责人ID
     */
    private Long ownerId;

    /**
     * 负责人姓名
     */
    private String ownerName;

    /**
     * 参与人ID列表
     */
    private transient List<Long> participantIds;

    /**
     * 参与人姓名列表
     */
    private transient List<String> participantNames;

    /**
     * 附件ID列表
     */
    private transient List<Long> attachmentIds;

    /**
     * 关联任务ID列表
     */
    private transient List<Long> relatedTaskIds;

    /**
     * 是否需要提醒
     */
    private Boolean needReminder;

    /**
     * 提醒时间
     */
    private LocalDateTime reminderTime;

    /**
     * 提醒方式
     */
    private Integer reminderType;

    /**
     * 是否已取消
     */
    private Boolean isCancelled;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 完成说明
     */
    private String completionNote;

    /**
     * 备注
     */
    private String remark;
} 