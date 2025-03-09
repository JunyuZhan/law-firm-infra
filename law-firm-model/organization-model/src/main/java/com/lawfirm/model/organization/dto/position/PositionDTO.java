package com.lawfirm.model.organization.dto.position;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.organization.enums.PositionTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 职位数据传输对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PositionDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 职位编码
     */
    @NotBlank(message = "职位编码不能为空")
    @Size(max = 32, message = "职位编码长度不能超过32个字符")
    private String code;

    /**
     * 职位名称
     */
    @NotBlank(message = "职位名称不能为空")
    @Size(max = 64, message = "职位名称长度不能超过64个字符")
    private String name;

    /**
     * 职位类型
     */
    @NotNull(message = "职位类型不能为空")
    private PositionTypeEnum type;

    /**
     * 职位等级
     */
    private Integer level;

    /**
     * 所属部门ID
     */
    @NotNull(message = "所属部门不能为空")
    private Long departmentId;

    /**
     * 职位描述
     */
    @Size(max = 500, message = "职位描述长度不能超过500个字符")
    private String description;

    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;
} 