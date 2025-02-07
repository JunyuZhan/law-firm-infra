package com.lawfirm.model.organization.vo;

import com.lawfirm.common.data.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 部门VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentVO extends BaseVO {

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门编码
     */
    private String code;

    /**
     * 部门负责人
     */
    private String manager;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 部门类型
     */
    private String type;

    /**
     * 所属律所ID
     */
    private Long lawFirmId;

    /**
     * 描述
     */
    private String description;

    /**
     * 子部门列表
     */
    private List<DepartmentVO> children;
} 