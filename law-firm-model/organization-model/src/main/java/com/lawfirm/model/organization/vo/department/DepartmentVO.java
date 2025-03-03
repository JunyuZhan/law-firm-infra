package com.lawfirm.model.organization.vo.department;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.organization.enums.DepartmentTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 部门视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DepartmentVO extends BaseVO {

    private static final long serialVersionUID = 1L;


    /**
     * 部门编码
     */
    private String code;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 所属律所ID
     */
    private Long firmId;

    /**
     * 所属律所名称
     */
    private String firmName;

    /**
     * 父级部门ID
     */
    private Long parentId;

    /**
     * 父级部门名称
     */
    private String parentName;

    /**
     * 部门类型
     */
    private DepartmentTypeEnum type;

    /**
     * 部门类型描述
     */
    private String typeDesc;

    /**
     * 负责人ID
     */
    private Long managerId;

    /**
     * 负责人姓名
     */
    private String managerName;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 联系邮箱
     */
    private String email;

    /**
     * 职能描述
     */
    private String functionDesc;

    /**
     * 办公地点
     */
    private String officeLocation;

    /**
     * 业务领域（仅业务部门）
     */
    private String businessDomain;

    /**
     * 案件类型列表（仅业务部门）
     */
    private transient List<String> caseTypes;

    /**
     * 职能类型（仅职能部门）
     */
    private String functionalType;

    /**
     * 服务范围（仅职能部门）
     */
    private String serviceScope;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 层级路径
     */
    private String path;

    /**
     * 是否叶子节点
     */
    private Boolean leaf;

    /**
     * 子部门列表
     */
    private transient List<DepartmentVO> children;

    /**
     * 团队数量
     */
    private Integer teamCount;

    /**
     * 人员数量
     */
    private Integer memberCount;
} 
