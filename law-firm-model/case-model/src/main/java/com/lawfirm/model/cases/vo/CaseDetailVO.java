package com.lawfirm.model.cases.vo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.model.cases.entity.CaseDocument;
import com.lawfirm.model.cases.entity.CaseFile;
import com.lawfirm.model.cases.entity.CaseTeam;
import com.lawfirm.model.cases.enums.CaseDifficultyEnum;
import com.lawfirm.model.cases.enums.CaseFeeTypeEnum;
import com.lawfirm.model.cases.enums.CaseHandleTypeEnum;
import com.lawfirm.model.cases.enums.CaseImportanceEnum;
import com.lawfirm.model.cases.enums.CasePriorityEnum;
import com.lawfirm.model.cases.enums.CaseProgressEnum;
import com.lawfirm.model.cases.enums.CaseSourceEnum;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CaseDetailVO extends BaseDTO {

    private Long id;
    private String caseNumber;
    private String caseName;
    private String description;
    private CaseTypeEnum caseType;
    private CaseStatusEnum caseStatus;
    private CaseProgressEnum caseProgress;
    private CaseHandleTypeEnum caseHandleType;
    private CaseDifficultyEnum caseDifficulty;
    private CaseImportanceEnum caseImportance;
    private CasePriorityEnum casePriority;
    private CaseFeeTypeEnum caseFeeType;
    private CaseSourceEnum caseSource;
    private String lawyerName;
    private String operator;
    private Long clientId;
    private Long lawFirmId;
    private Long branchId;
    private Long departmentId;
    private LocalDateTime filingTime;
    private LocalDateTime closingTime;
    private BigDecimal fee;
    private String courtName;
    private String judgeName;
    private String courtCaseNumber;
    private Boolean isMajor;
    private Boolean hasConflict;
    private String conflictReason;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private List<CaseStatusVO> statusHistory;
    private List<CaseFile> files;

    // 参与方信息
    private ClientInfo client;
    private LawyerInfo lawyer;
    private LawFirmInfo lawFirm;
    private List<CaseTeam> teamMembers;  // 团队成员

    // 案件文档
    private List<CaseDocument> documents;  // 案件相关文档

    // 财务信息
    private BigDecimal feeAmount;  // 收费金额

    // 法院信息
    private String remark;  // 备注信息

    @Override
    public CaseDetailVO setId(Long id) {
        super.setId(id);
        return this;
    }
    
    @Override
    public CaseDetailVO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }
    
    @Override
    public CaseDetailVO setCreateBy(String createBy) {
        super.setCreateBy(createBy);
        return this;
    }
    
    @Override
    public CaseDetailVO setUpdateBy(String updateBy) {
        super.setUpdateBy(updateBy);
        return this;
    }

    @Data
    public static class ClientInfo {
        private Long id;
        private String name;
        private String phone;
        private String email;
    }

    @Data
    public static class LawyerInfo {
        private Long id;
        private String name;
        private String phone;
        private String email;
        private String title;
    }

    @Data
    public static class LawFirmInfo {
        private Long id;
        private String name;
        private String address;
        private String phone;
    }
} 