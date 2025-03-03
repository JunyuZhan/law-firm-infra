package com.lawfirm.model.finance.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 成本中心实体类
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("fin_cost_center")
public class CostCenter extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 成本中心编号
     */
    @NotBlank(message = "成本中心编号不能为空")
    @Size(max = 32, message = "成本中心编号长度不能超过32个字符")
    @TableField("cost_center_number")
    private String costCenterNumber;

    /**
     * 成本中心名称
     */
    @NotBlank(message = "成本中心名称不能为空")
    @Size(max = 100, message = "成本中心名称长度不能超过100个字符")
    @TableField("cost_center_name")
    private String costCenterName;

    /**
     * 上级成本中心ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 成本中心层级
     */
    @TableField("level")
    private Integer level;

    /**
     * 成本中心路径
     */
    @Size(max = 255, message = "成本中心路径长度不能超过255个字符")
    @TableField("path")
    private String path;

    /**
     * 负责人ID
     */
    @TableField("manager_id")
    private Long managerId;

    /**
     * 关联部门ID
     */
    @TableField("department_id")
    private Long departmentId;

    /**
     * 成本中心说明
     */
    @Size(max = 500, message = "成本中心说明长度不能超过500个字符")
    @TableField("description")
    private String description;

    /**
     * 租户ID
     */
    @TableField("tenant_id")
    private Long tenantId;

    @Override
    public void preInsert() {
        super.preInsert();
        if (this.level == null) {
            this.level = 1;
        }
    }
} 