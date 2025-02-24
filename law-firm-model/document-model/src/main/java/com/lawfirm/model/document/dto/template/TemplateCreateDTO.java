package com.lawfirm.model.document.dto.template;

import com.lawfirm.model.base.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 模板创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class TemplateCreateDTO extends BaseDTO {

    /**
     * 模板编码
     */
    @NotBlank(message = "模板编码不能为空")
    @Size(max = 50, message = "模板编码长度不能超过50个字符")
    private String templateCode;

    /**
     * 模板名称
     */
    @NotBlank(message = "模板名称不能为空")
    @Size(max = 100, message = "模板名称长度不能超过100个字符")
    private String templateName;

    /**
     * 模板类型
     */
    @NotBlank(message = "模板类型不能为空")
    private String templateType;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 适用范围
     */
    private String scope;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 模板参数（JSON格式）
     */
    private String parameters;

    /**
     * 是否系统内置
     */
    private Boolean isSystem;

    /**
     * 是否默认模板
     */
    private Boolean isDefault;

    /**
     * 模板描述
     */
    @Size(max = 500, message = "模板描述长度不能超过500个字符")
    private String description;

    /**
     * 状态（0-启用 1-禁用）
     */
    private Integer status;
} 