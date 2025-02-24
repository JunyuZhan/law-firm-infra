package com.lawfirm.model.cases.vo.business;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 合同模板VO
 */
@Data
public class ContractTemplateVO {
    
    private Long id;
    
    private String templateName;
    
    private String templateCode;
    
    private String category;
    
    private String description;
    
    private String content;
    
    private String variables;
    
    private String attachments;
    
    private Boolean enabled;
    
    private Integer version;
    
    private String remark;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 