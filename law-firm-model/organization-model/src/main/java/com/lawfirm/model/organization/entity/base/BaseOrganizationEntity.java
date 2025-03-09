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
    private Integer sortOrder;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 父级组织ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 组织路径（格式如：/1/2/3/）
     */
    @TableField(value = "path")
    private String path;

    /**
     * 组织层级深度
     */
    @TableField(value = "level")
    private Integer level;

    /**
     * 生效日期
     */
    @TableField(value = "effective_start_date")
    private java.time.LocalDate effectiveStartDate;

    /**
     * 失效日期
     */
    @TableField(value = "effective_end_date")
    private java.time.LocalDate effectiveEndDate;

    /**
     * 是否临时组织
     */
    @TableField(value = "is_temporary")
    private Boolean isTemporary;
} 
