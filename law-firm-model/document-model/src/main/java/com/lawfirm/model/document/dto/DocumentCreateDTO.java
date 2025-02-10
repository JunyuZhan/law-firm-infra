package com.lawfirm.model.document.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.model.document.enums.DocumentSecurityLevelEnum;
import com.lawfirm.model.document.enums.DocumentTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class DocumentCreateDTO extends BaseDTO {

    @NotBlank(message = "文档名称不能为空")
    @Size(max = 200, message = "文档名称长度不能超过200个字符")
    private String documentName;

    @NotNull(message = "文档类型不能为空")
    private DocumentTypeEnum documentType;

    @NotNull(message = "文档密级不能为空")
    private DocumentSecurityLevelEnum securityLevel;

    private Long lawFirmId;
    private Long caseId;

    // 文件信息
    @Size(max = 500, message = "文件路径长度不能超过500个字符")
    private String filePath;

    private Long fileSize;

    @Size(max = 100, message = "文件类型长度不能超过100个字符")
    private String fileType;

    @Size(max = 100, message = "文件Hash长度不能超过100个字符")
    private String fileHash;

    @Size(max = 500, message = "关键词长度不能超过500个字符")
    private String keywords;

    @Size(max = 1000, message = "文档摘要长度不能超过1000个字符")
    private String summary;

    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;

    @Override
    public BaseDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }
    
    @Override
    public BaseDTO setId(Long id) {
        super.setId(id);
        return this;
    }
} 