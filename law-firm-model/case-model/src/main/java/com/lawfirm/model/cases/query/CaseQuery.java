package com.lawfirm.model.cases.query;

import com.lawfirm.model.base.query.PageQuery;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class CaseQuery extends PageQuery {

    private String caseNumber;  // 案件编号

    private String caseName;    // 案件名称

    private CaseTypeEnum caseType;  // 案件类型

    private CaseStatusEnum status;  // 案件状态

    private Long clientId;      // 委托人ID

    private Long lawyerId;      // 主办律师ID

    private Long lawFirmId;     // 律所ID

    private LocalDateTime filingTimeStart;  // 立案时间起始

    private LocalDateTime filingTimeEnd;    // 立案时间结束

    private LocalDateTime closingTimeStart; // 结案时间起始

    private LocalDateTime closingTimeEnd;   // 结案时间结束

    private String courtName;   // 法院名称

    private String judgeName;   // 法官姓名

    private String courtCaseNumber;  // 法院案号
} 