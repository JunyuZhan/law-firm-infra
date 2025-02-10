package com.lawfirm.model.document.vo;

import com.lawfirm.common.core.enums.StatusEnum;
import com.lawfirm.common.data.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class DocumentVO extends BaseDTO {
    
    private String documentNumber;
    private String documentName;
    private String fileName;
    private Long fileSize;
    private String fileType;
    private String version;
    private StatusEnum status;
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

    @Override
    public DocumentVO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }

    @Override
    public DocumentVO setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public DocumentVO setCreateBy(String createBy) {
        super.setCreateBy(createBy);
        return this;
    }

    @Override
    public DocumentVO setUpdateBy(String updateBy) {
        super.setUpdateBy(updateBy);
        return this;
    }

    @Override
    public DocumentVO setStatus(StatusEnum status) {
        super.setStatus(status);
        return this;
    }
} 