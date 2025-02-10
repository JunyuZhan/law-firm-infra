package com.lawfirm.model.document.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DocumentVersionDTO extends BaseDTO {

    @NotNull(message = "文档ID不能为空")
    private Long documentId;

    @NotNull(message = "版本号不能为空")
    private Integer version;

    @Size(max = 500, message = "变更说明长度不能超过500个字符")
    private String changeLog;

    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;

    private String modifiedBy;

    @Override
    public DocumentVersionDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }

    @Override
    public DocumentVersionDTO setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public DocumentVersionDTO setCreateBy(String createBy) {
        super.setCreateBy(createBy);
        return this;
    }

    @Override
    public DocumentVersionDTO setUpdateBy(String updateBy) {
        super.setUpdateBy(updateBy);
        return this;
    }
} 