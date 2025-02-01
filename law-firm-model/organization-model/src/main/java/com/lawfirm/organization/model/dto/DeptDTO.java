package com.lawfirm.organization.model.dto;

import java.util.List;

import com.lawfirm.common.data.dto.BaseDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer orderNum;

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
     * 子部门
     */
    private List<DeptDTO> children;
} 