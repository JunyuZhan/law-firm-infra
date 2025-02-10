package com.lawfirm.model.cases.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.model.cases.enums.DocumentSecurityLevelEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class CaseDocumentDTO extends BaseDTO {

    private Long id;
    
    @NotNull(message = "案件ID不能为空")
    private Long caseId;
    
    @NotBlank(message = "文档名称不能为空")
    @Size(max = 200, message = "文档名称长度不能超过200个字符")
    private String documentName;
    
    private String documentPath;
    
    @NotNull(message = "文档类型不能为空")
    private String documentType;
    
    @Size(max = 500, message = "文档描述长度不能超过500个字符")
    private String description;
    
    @NotNull(message = "文件大小不能为空")
    private Long fileSize;
    
    @NotBlank(message = "文件类型不能为空")
    private String fileType;
    
    @NotNull(message = "安全级别不能为空")
    private DocumentSecurityLevelEnum securityLevel;
    
    private Boolean encrypted = false;
    
    private String version;
    
    private LocalDateTime expiryTime;
    
    private Boolean needApproval = false;
    
    private Boolean approved = false;
    
    private String approver;
    
    private LocalDateTime approvalTime;
    
    private String approvalComment;
    
    private Boolean allowDownload = true;
    
    private Boolean allowPrint = true;
    
    private String md5;
    
    private byte[] content;

    @Override
    public CaseDocumentDTO setId(Long id) {
        super.setId(id);
        return this;
    }
    
    @Override
    public CaseDocumentDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }
    
    @Override
    public CaseDocumentDTO setCreateBy(String createBy) {
        super.setCreateBy(createBy);
        return this;
    }
    
    @Override
    public CaseDocumentDTO setUpdateBy(String updateBy) {
        super.setUpdateBy(updateBy);
        return this;
    }
} 