package com.lawfirm.admin.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "角色信息响应")
public class RoleResponse {
    
    @Schema(description = "角色ID")
    private Long id;
    
    @Schema(description = "角色名称")
    private String roleName;
    
    @Schema(description = "角色编码")
    private String roleCode;
    
    @Schema(description = "状态：0-禁用，1-启用")
    private Integer status;
    
    @Schema(description = "排序号")
    private Integer sort;
    
    @Schema(description = "菜单ID列表")
    private List<Long> menuIds;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
    @Schema(description = "备注")
    private String remark;
} 