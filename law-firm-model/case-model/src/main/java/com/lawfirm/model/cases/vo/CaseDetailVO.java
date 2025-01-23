package com.lawfirm.model.cases.vo;

import com.lawfirm.model.cases.entity.CaseDocument;
import com.lawfirm.model.cases.entity.CaseTeam;
import com.lawfirm.model.cases.enums.CaseStatusEnum;
import com.lawfirm.model.cases.enums.CaseTypeEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CaseDetailVO {

    private Long id;
    private String caseNumber;
    private String caseName;
    private String description;
    private CaseTypeEnum caseType;
    private CaseStatusEnum status;

    // 参与方信息
    private ClientVO client;        // 委托人信息
    private LawyerVO leadLawyer;    // 主办律师信息
    private LawFirmVO lawFirm;      // 律所信息
    private List<CaseTeam> teamMembers;  // 团队成员

    // 时间信息
    private LocalDateTime filingTime;   // 立案时间
    private LocalDateTime closingTime;  // 结案时间
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间

    // 案件文档
    private List<CaseDocument> documents;  // 案件相关文档

    // 财务信息
    private BigDecimal fee;  // 收费金额

    // 法院信息
    private String courtName;       // 法院名称
    private String judgeName;       // 法官姓名
    private String courtCaseNumber; // 法院案号

    private String remark;  // 备注信息

    @Data
    public static class ClientVO {
        private Long id;
        private String name;
        private String phone;
        private String email;
    }

    @Data
    public static class LawyerVO {
        private Long id;
        private String name;
        private String phone;
        private String email;
        private String title;      // 职称
        private String specialty;  // 专长领域
    }

    @Data
    public static class LawFirmVO {
        private Long id;
        private String name;
        private String address;
        private String phone;
    }
} 