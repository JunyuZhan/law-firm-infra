package com.lawfirm.model.organization.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.organization.constants.OrganizationFieldConstants;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 组织实体基类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BaseOrganizationEntity extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 编码
     */
    @TableField(value = "code")
    @Pattern(regexp = OrganizationFieldConstants.Code.PATTERN, message = "编码格式不正确")
    @Size(min = OrganizationFieldConstants.Code.MIN_LENGTH, max = OrganizationFieldConstants.Code.MAX_LENGTH, message = "编码长度必须在{min}到{max}之间")
    private String code;

    /**
     * 名称
     */
    @TableField(value = "name")
    @Size(min = OrganizationFieldConstants.Name.MIN_LENGTH, max = OrganizationFieldConstants.Name.MAX_LENGTH, message = "名称长度必须在{min}到{max}之间")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "description")
    @Size(max = OrganizationFieldConstants.Description.MAX_LENGTH, message = "描述长度不能超过{max}")
    private String description;

    /**
     * 状态（0-禁用 1-启用）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 排序号
     */
    @TableField(value = "sort")
    private Integer sort;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;
} 
