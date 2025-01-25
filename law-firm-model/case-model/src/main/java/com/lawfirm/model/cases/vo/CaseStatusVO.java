package com.lawfirm.model.cases.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class CaseStatusVO extends BaseVO {
    private Long id;
    private Long caseId;
    private CaseStatusEnum fromStatus;
    private CaseStatusEnum toStatus;
    private String reason;
    private String operator;
    private LocalDateTime operateTime;
} 