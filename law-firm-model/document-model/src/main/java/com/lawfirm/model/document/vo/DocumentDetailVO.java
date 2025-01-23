package com.lawfirm.model.document.vo;

import com.lawfirm.model.document.enums.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DocumentDetailVO {

    private Long id;
    private String documentNumber;
    private String documentName;
    private DocumentTypeEnum documentType;
    private DocumentSecurityLevelEnum securityLevel;
    private DocumentStatusEnum status;

    // 关联信息
    private LawFirmVO lawFirm;  // 律所信息
    private CaseVO caseInfo;    // 案件信息

    // 文件信息
    private String filePath;
    private Long fileSize;
    private String fileType;
    private String fileHash;

    // 内容信息
    private String keywords;
    private String summary;

    // 版本信息
    private Integer version;
    private List<VersionInfo> versionHistory;  // 版本历史

    // 时间信息
    private LocalDateTime createTime;
    private String createBy;
    private LocalDateTime lastModifiedTime;
    private String lastModifiedBy;

    private String remark;

    @Data
    public static class LawFirmVO {
        private Long id;
        private String name;
    }

    @Data
    public static class CaseVO {
        private Long id;
        private String caseNumber;
        private String caseName;
    }

    @Data
    public static class VersionInfo {
        private Integer version;
        private String filePath;
        private Long fileSize;
        private String fileHash;
        private String changeLog;
        private LocalDateTime modifiedTime;
        private String modifiedBy;
    }
} 