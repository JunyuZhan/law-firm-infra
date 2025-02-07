package com.lawfirm.admin.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "部门信息响应")
public class DeptResponse {
    
    @Schema(description = "部门ID")
    private Long id;
    
    @Schema(description = "部门名称")
    private String deptName;
    
    @Schema(description = "父部门ID")
    private Long parentId;
    
    @Schema(description = "显示顺序")
    private Integer orderNum;
    
    @Schema(description = "负责人")
    private String leader;
    
    @Schema(description = "联系电话")
    private String phone;
    
    @Schema(description = "邮箱")
    private String email;
    
    @Schema(description = "状态：0-禁用，1-启用")
    private Integer status;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
    @Schema(description = "子部门")
    private List<DeptResponse> children;
} 