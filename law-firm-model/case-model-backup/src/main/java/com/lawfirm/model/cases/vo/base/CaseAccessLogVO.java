package com.lawfirm.model.cases.vo.base;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 案件访问日志VO
 */
@Data
public class CaseAccessLogVO {
    
    private Long id;
    
    private Long caseId;
    
    private String accessType;
    
    private String accessUser;
    
    private String accessUserId;
    
    private String accessIp;
    
    private String accessDevice;
    
    private String accessLocation;
    
    private String accessResult;
    
    private String accessDetails;
    
    private LocalDateTime accessTime;
    
    private String remark;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 