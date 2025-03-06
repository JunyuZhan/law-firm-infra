package com.lawfirm.model.auth.dto.user;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.ToString;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class UserUpdateDTO extends BaseUserDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 手机号（重写基类字段增强校验）
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;
    
    /**
     * 角色ID列表
     */
    private Long[] roleIds;
}
