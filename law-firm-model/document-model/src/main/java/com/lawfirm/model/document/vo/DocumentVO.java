package com.lawfirm.model.document.vo;

import com.lawfirm.common.data.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentVO extends BaseVO {
    
    private String documentNumber;
    private String documentName;
    private String fileName;
    private Long fileSize;
    private String fileType;
    private String version;
    private Integer status;
    private String statusName;
    private LocalDateTime uploadTime;
    private String uploadBy;
    private LocalDateTime auditTime;
    private String auditBy;
    private String auditComment;
    private String description;
    private String tags;
    private String documentType;
    private Integer previewable;
    private String previewUrl;
    
    // 扩展字段
    private String uploadByName;
    private String auditByName;
} 