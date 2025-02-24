package com.lawfirm.model.auth.dto.role;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RoleUpdateDTO extends BaseDTO {
    
    @NotNull(message = "角色ID不能为空")
    private Long id;
    
    @NotBlank(message = "角色名称不能为空")
    private String roleName;
    
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;
    
    private String description;
    
    private Integer status;
    
    private List<Long> permissionIds;
    
    private Integer sortOrder;
} 