package com.lawfirm.model.system.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.common.core.enums.StatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;

/**
 * 系统模板DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
public class SysTemplateDTO extends BaseDTO {

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板编码
     */
    private String code;

    /**
     * 模板类型
     */
    private String type;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 模板参数
     */
    private String params;

    @Override
    public StatusEnum getStatus() {
        return super.getStatus();
    }

    @Override
    public SysTemplateDTO setStatus(StatusEnum status) {
        super.setStatus(status);
        return this;
    }

    @Override
    public SysTemplateDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }

    @Override
    public SysTemplateDTO setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public SysTemplateDTO setSort(Integer sort) {
        super.setSort(sort);
        return this;
    }
} 