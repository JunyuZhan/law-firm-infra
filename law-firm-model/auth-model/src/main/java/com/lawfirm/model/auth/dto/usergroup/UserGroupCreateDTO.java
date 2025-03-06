package com.lawfirm.model.auth.dto.usergroup;

import com.lawfirm.model.base.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户组创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserGroupCreateDTO extends BaseDTO {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 用户组名称
     */
    @NotBlank(message = "用户组名称不能为空")
    @Size(max = 50, message = "用户组名称长度不能超过50个字符")
    private String name;
    
    /**
     * 用户组编码
     */
    @NotBlank(message = "用户组编码不能为空")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "用户组编码只能包含字母、数字和下划线")
    @Size(max = 50, message = "用户组编码长度不能超过50个字符")
    private String code;
    
    /**
     * 父级ID
     */
    private Long parentId;
    
    /**
     * 显示顺序
     */
    private Integer sort;
    
    /**
     * 状态（0-正常，1-禁用）
     */
    private Integer status;
    
    /**
     * 备注
     */
    @Size(max = 255, message = "备注长度不能超过255个字符")
    private String remark;
    
    /**
     * 用户ID列表
     */
    private transient List<Long> userIds;
} 