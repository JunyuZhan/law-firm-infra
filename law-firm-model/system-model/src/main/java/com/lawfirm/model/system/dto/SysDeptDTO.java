package com.lawfirm.model.system.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.common.core.enums.StatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 部门DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
public class SysDeptDTO extends BaseDTO {
    
    /**
     * 部门名称
     */
    private String name;
    
    /**
     * 父部门ID
     */
    private Long parentId;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 负责人
     */
    private String leader;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 子部门列表
     */
    private List<SysDeptDTO> children;

    @Override
    public StatusEnum getStatus() {
        return super.getStatus();
    }

    @Override
    public SysDeptDTO setStatus(StatusEnum status) {
        super.setStatus(status);
        return this;
    }

    @Override
    public SysDeptDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }

    @Override
    public SysDeptDTO setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public SysDeptDTO setSort(Integer sort) {
        super.setSort(sort);
        return this;
    }
}