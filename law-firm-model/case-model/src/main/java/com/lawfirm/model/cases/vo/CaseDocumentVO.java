package com.lawfirm.model.cases.vo;

import com.lawfirm.model.cases.enums.DocumentSecurityLevelEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaseDocumentVO {

    private Long id;
    
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
    
    private String remark;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 