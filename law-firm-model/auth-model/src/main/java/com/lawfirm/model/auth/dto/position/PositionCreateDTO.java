package com.lawfirm.model.auth.dto.position;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 职位创建DTO
 */
@Data
public class PositionCreateDTO {
    
    /**
     * 职位名称
     */
    @NotBlank(message = "职位名称不能为空")
    @Size(max = 50, message = "职位名称长度不能超过50个字符")
    private String name;
    
    /**
     * 职位编码
     */
    @NotBlank(message = "职位编码不能为空")
    @Size(max = 30, message = "职位编码长度不能超过30个字符")
    private String code;
    
    /**
     * 显示顺序
     */
    private Integer orderNum;
    
    /**
     * 职位状态（0正常 1停用）
     */
    private Integer status;
    
    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
} 