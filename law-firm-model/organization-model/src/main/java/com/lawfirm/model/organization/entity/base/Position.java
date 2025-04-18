package com.lawfirm.model.organization.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.organization.constants.OrganizationFieldConstants;
import com.lawfirm.model.organization.enums.PositionTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 职位实体
 */
@Data
@TableName("org_position")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Position extends ModelBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 职位编码
     */
    @NotBlank(message = "职位编码不能为空")
    @Size(max = 32, message = "职位编码长度不能超过32个字符")
    @TableField("code")
    private String code;
    
    /**
     * 职位名称
     */
    @NotBlank(message = "职位名称不能为空")
    @Size(max = 64, message = "职位名称长度不能超过64个字符")
    @TableField("name")
    private String name;
    
    /**
     * 职位类型
     */
    @NotNull(message = "职位类型不能为空")
    @TableField("type")
    private PositionTypeEnum type;
    
    /**
     * 职位等级
     */
    @TableField("level")
    private Integer level;
    
    /**
     * 所属部门ID
     */
    @NotNull(message = "所属部门不能为空")
    @TableField("department_id")
    private Long departmentId;
    
    /**
     * 职位描述
     */
    @Size(max = 500, message = "职位描述长度不能超过500个字符")
    @TableField("description")
    private String description;
    
    /**
     * 状态（0-禁用 1-启用）
     */
    @TableField("status")
    private Integer status;
    
    /**
     * 排序号
     */
    @TableField("sort")
    private Integer sort;
    
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
} 