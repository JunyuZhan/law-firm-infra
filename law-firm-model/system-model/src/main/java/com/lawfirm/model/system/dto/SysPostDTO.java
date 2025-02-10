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
 * 系统岗位DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
public class SysPostDTO extends BaseDTO {

    /**
     * 岗位编码
     */
    private String code;

    /**
     * 岗位名称
     */
    private String name;

    /**
     * 显示顺序
     */
    private Integer sort;

    @Override
    public StatusEnum getStatus() {
        return super.getStatus();
    }

    @Override
    public SysPostDTO setStatus(StatusEnum status) {
        super.setStatus(status);
        return this;
    }

    @Override
    public SysPostDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }

    @Override
    public SysPostDTO setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public SysPostDTO setSort(Integer sort) {
        super.setSort(sort);
        return this;
    }
} 