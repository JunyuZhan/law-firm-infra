package com.lawfirm.model.organization.vo.position;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.organization.enums.PositionTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 职位视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PositionVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 职位编码
     */
    private String code;

    /**
     * 职位名称
     */
    private String name;

    /**
     * 职位类型
     */
    private PositionTypeEnum type;

    /**
     * 职位类型描述
     */
    private String typeDescription;

    /**
     * 职位等级
     */
    private Integer level;

    /**
     * 职位等级描述
     */
    private String levelDescription;

    /**
     * 所属部门ID
     */
    private Long departmentId;

    /**
     * 所属部门名称
     */
    private String departmentName;

    /**
     * 职位描述
     */
    private String description;

    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusDescription;

    /**
     * 排序号
     */
    private Integer sortOrder;

    /**
     * 备注
     */
    private String remark;

    /**
     * 在职人数
     */
    private Integer employeeCount;
} 