package com.lawfirm.model.document.query;

import com.lawfirm.common.data.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentQuery extends BaseQuery {
    
    private String documentNumber;
    private String documentName;
    private String fileName;
    private String fileType;
    private String version;
    private Integer status;
    private LocalDateTime uploadTimeStart;
    private LocalDateTime uploadTimeEnd;
    private String uploadBy;
    private LocalDateTime auditTimeStart;
    private LocalDateTime auditTimeEnd;
    private String auditBy;
    private String documentType;
    private String tags;
} 