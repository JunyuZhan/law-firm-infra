package com.lawfirm.model.auth.dto.user;

import com.lawfirm.model.base.dto.BaseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户更新DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserUpdateDTO extends BaseDTO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 昵称
     */
    @Size(max = 50, message = "昵称长度不能超过50个字符")
    private String nickname;
    
    /**
     * 真实姓名
     */
    @Size(max = 50, message = "真实姓名长度不能超过50个字符")
    private String realName;
    
    /**
     * 头像
     */
    private String avatar;
    
    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100个字符")
    private String email;
    
    /**
     * 手机号
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;
    
    /**
     * 性别（0-未知，1-男，2-女）
     */
    private Integer gender;
    
    /**
     * 部门ID
     */
    private Long departmentId;
    
    /**
     * 职位ID
     */
    private Long positionId;
    
    /**
     * 角色ID列表
     */
    private Long[] roleIds;
} 