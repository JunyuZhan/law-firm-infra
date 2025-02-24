package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.doc.DocumentSecurityLevelEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class CaseDocumentVO extends BaseVO {
    private Long caseId;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String contentType;
    private String fileMd5;
    private String description;
    private DocumentSecurityLevelEnum securityLevel;
    private Boolean encrypted;
    private Boolean allowDownload;
    private Boolean allowPrint;
    private Boolean needApproval;
    private Boolean approved;
    private String approver;
    private LocalDateTime approvalTime;
    private String approvalComment;
    private LocalDateTime expiryTime;
    private String version;
    private String uploader;
    private LocalDateTime uploadTime;
    private String category;
    private String tags;
    private Boolean temporary;
    private LocalDateTime temporaryExpireTime;
} 