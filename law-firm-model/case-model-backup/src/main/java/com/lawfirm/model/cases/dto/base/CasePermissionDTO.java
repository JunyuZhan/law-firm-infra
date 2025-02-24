package com.lawfirm.model.cases.dto.base;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 案件权限DTO
 */
@Data
public class CasePermissionDTO {
    
    private Long id;
    
    private Long caseId;
    
    private String userId;
    
    private String userName;
    
    private String permissionType;
    
    private Boolean canView;
    
    private Boolean canEdit;
    
    private Boolean canDelete;
    
    private Boolean canShare;
    
    private Boolean canAssign;
    
    private Boolean canApprove;
    
    private LocalDateTime expireTime;
    
    private String remark;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 