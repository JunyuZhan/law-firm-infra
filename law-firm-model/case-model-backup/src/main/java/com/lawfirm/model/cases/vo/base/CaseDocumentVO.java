package com.lawfirm.model.cases.vo.base;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CaseDocumentVO {
    
    private Long id;
    
    private Long caseId;
    
    private String fileName;
    
    private String fileType;
    
    private String fileSize;
    
    private String filePath;
    
    private String uploaderId;
    
    private String uploaderName;
    
    private String description;
    
    private String category;
    
    private String tags;
    
    private LocalDateTime createTime;
    
    private String createBy;
    
    private LocalDateTime updateTime;
    
    private String updateBy;
} 