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
 * 系统配置DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
public class SysConfigDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 配置名称
     */
    private String name;

    /**
     * 配置键名
     */
    private String configKey;

    /**
     * 配置键值
     */
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    private String configType;

    /**
     * 配置分组
     */
    private String configGroup;

    /**
     * 备注
     */
    private String remark;

    @Override
    public StatusEnum getStatus() {
        return super.getStatus();
    }

    @Override
    public SysConfigDTO setStatus(StatusEnum status) {
        super.setStatus(status);
        return this;
    }

    @Override
    public SysConfigDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }

    @Override
    public SysConfigDTO setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public SysConfigDTO setSort(Integer sort) {
        super.setSort(sort);
        return this;
    }
} 