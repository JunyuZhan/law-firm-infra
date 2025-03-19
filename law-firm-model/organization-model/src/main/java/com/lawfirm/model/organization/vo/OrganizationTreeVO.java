package com.lawfirm.model.organization.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 组织树视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class OrganizationTreeVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 组织编码
     */
    private String code;

    /**
     * 组织名称
     */
    private String name;

    /**
     * 组织类型
     */
    private Integer type;

    /**
     * 组织类型描述
     */
    private String typeDescription;

    /**
     * 父组织ID
     */
    private Long parentId;

    /**
     * 父组织名称
     */
    private String parentName;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusDescription;

    /**
     * 备注
     */
    private String remark;

    /**
     * 子组织列表
     */
    private List<OrganizationTreeVO> children;

    /**
     * 员工数量
     */
    private Integer employeeCount;

    /**
     * 职位数量
     */
    private Integer positionCount;

    /**
     * 是否有子组织
     */
    private Boolean hasChildren;

    /**
     * 是否叶子节点
     */
    private Boolean isLeaf;

    /**
     * 组织路径
     */
    private String path;

    /**
     * 组织全称
     */
    private String fullName;
} 