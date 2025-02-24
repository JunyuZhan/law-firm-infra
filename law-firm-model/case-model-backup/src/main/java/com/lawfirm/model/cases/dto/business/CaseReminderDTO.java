package com.lawfirm.model.cases.dto.business;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.reminder.ReminderTypeEnum;
import com.lawfirm.model.cases.enums.reminder.RepeatTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件提醒DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseReminderDTO extends BaseDTO {

    /**
     * 案件ID
     */
    @NotNull(message = "案件ID不能为空")
    private Long caseId;
    
    /**
     * 提醒类型
     */
    @NotNull(message = "提醒类型不能为空")
    private ReminderTypeEnum type;
    
    /**
     * 提醒标题
     */
    @NotBlank(message = "提醒标题不能为空")
    private String title;
    
    /**
     * 提醒内容
     */
    @NotBlank(message = "提醒内容不能为空")
    private String content;
    
    /**
     * 提醒时间
     */
    @NotNull(message = "提醒时间不能为空")
    private LocalDateTime reminderTime;
    
    private Integer advanceTime = 30;
    
    /**
     * 是否重复提醒
     */
    private Boolean repeat = false;
    
    private RepeatTypeEnum repeatType;
    
    private String repeatRule;
    
    private LocalDateTime repeatEndTime;
    
    /**
     * 接收人ID
     */
    @NotNull(message = "接收人ID不能为空")
    private Long receiverId;
    
    private String receiverName;
    
    private List<String> ccList;
    
    private Boolean needConfirm = true;
    
    /**
     * 提醒优先级
     */
    private Integer priority = 2;
    
    private String relatedItemType;
    
    private String relatedItemId;
    
    private String createBy;
    
    private LocalDateTime createTime;
    
    private String updateBy;
    
    private LocalDateTime updateTime;
} 