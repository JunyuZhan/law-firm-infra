package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.event.TimelineEventTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CaseTimelineVO extends BaseVO {
    private Long caseId;
    private TimelineEventTypeEnum type;
    private String title;
    private String description;
    private LocalDateTime eventTime;
    private String operatorId;
    private String operatorName;
    private String objectType;
    private String objectId;
    private String objectName;
    private String oldValue;
    private String newValue;
    private List<String> attachments;
} 