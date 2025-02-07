package com.lawfirm.model.system.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 部门DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
     * 状态（0正常 1停用）
     */
    private Integer status;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 子部门列表
     */
    private List<SysDeptDTO> children;
}