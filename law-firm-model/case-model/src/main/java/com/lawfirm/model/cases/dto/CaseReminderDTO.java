package com.lawfirm.model.cases.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.model.cases.enums.ReminderTypeEnum;
import com.lawfirm.model.cases.enums.RepeatTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class CaseReminderDTO extends BaseDTO {

    private Long id;
    
    @NotNull(message = "案件ID不能为空")
    private Long caseId;
    
    @NotNull(message = "提醒类型不能为空")
    private ReminderTypeEnum type;
    
    @NotBlank(message = "提醒标题不能为空")
    private String title;
    
    private String content;
    
    @NotNull(message = "提醒时间不能为空")
    private LocalDateTime reminderTime;
    
    private Integer advanceTime = 30;
    
    private Boolean repeat = false;
    
    private RepeatTypeEnum repeatType;
    
    private String repeatRule;
    
    private LocalDateTime repeatEndTime;
    
    @NotNull(message = "接收人ID不能为空")
    private Long receiverId;
    
    private String receiverName;
    
    private List<String> ccList;
    
    private Boolean needConfirm = true;
    
    private Integer priority = 2;
    
    private String relatedItemType;
    
    private String relatedItemId;
    
    private String remark;

    @Override
    public CaseReminderDTO setId(Long id) {
        super.setId(id);
        return this;
    }
    
    @Override
    public CaseReminderDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }
    
    @Override
    public CaseReminderDTO setCreateBy(String createBy) {
        super.setCreateBy(createBy);
        return this;
    }
    
    @Override
    public CaseReminderDTO setUpdateBy(String updateBy) {
        super.setUpdateBy(updateBy);
        return this;
    }
} 