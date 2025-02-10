package com.lawfirm.model.document.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.model.document.enums.DocumentSecurityLevelEnum;
import com.lawfirm.model.document.enums.DocumentStatusEnum;
import com.lawfirm.model.document.enums.DocumentTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentQueryDTO extends BaseDTO {
    
    private String documentNumber;
    private String documentName;
    private DocumentTypeEnum documentType;
    private DocumentSecurityLevelEnum securityLevel;
    private DocumentStatusEnum documentStatus;
    
    private Long lawFirmId;
    private Long caseId;
    
    private String keywords;
    private LocalDateTime createTimeStart;
    private LocalDateTime createTimeEnd;
    private String createdBy;
    
    private Boolean includeContent = false;
    private Boolean includeVersions = false;

    // 文件相关查询条件
    private String fileType;
    private Long minFileSize;
    private Long maxFileSize;
} 