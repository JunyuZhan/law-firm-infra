package com.lawfirm.model.system.dto.dict;

import com.lawfirm.model.base.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 字典创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DictCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    @Size(max = 50, message = "字典名称长度不能超过50个字符")
    private String dictName;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    @Size(max = 100, message = "字典编码长度不能超过100个字符")
    private String dictCode;

    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    private String dictType;

    /**
     * 字典描述
     */
    @Size(max = 500, message = "字典描述长度不能超过500个字符")
    private String description;
} 