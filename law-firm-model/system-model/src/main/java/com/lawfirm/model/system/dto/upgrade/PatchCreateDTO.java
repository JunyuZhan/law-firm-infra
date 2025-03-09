package com.lawfirm.model.system.dto.upgrade;

import com.lawfirm.model.base.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统补丁创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PatchCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 所属升级ID
     */
    @NotNull(message = "所属升级ID不能为空")
    private Long upgradeId;

    /**
     * 补丁名称
     */
    @NotBlank(message = "补丁名称不能为空")
    @Size(max = 100, message = "补丁名称长度不能超过100个字符")
    private String name;
    
    /**
     * 补丁编号
     */
    @NotBlank(message = "补丁编号不能为空")
    @Size(max = 50, message = "补丁编号长度不能超过50个字符")
    private String code;
    
    /**
     * 补丁描述
     */
    @Size(max = 500, message = "补丁描述长度不能超过500个字符")
    private String description;
    
    /**
     * 补丁文件路径
     */
    private String filePath;
    
    /**
     * 补丁类型
     */
    private String type;
    
    /**
     * 补丁优先级
     */
    private Integer priority;
    
    /**
     * 是否必须安装
     */
    private Boolean required;
} 