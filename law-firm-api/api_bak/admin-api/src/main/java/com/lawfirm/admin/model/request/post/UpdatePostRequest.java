package com.lawfirm.admin.model.request.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Size;

@Data
@Schema(description = "更新岗位请求")
public class UpdatePostRequest {
    
    @Schema(description = "岗位编码")
    @Size(max = 64, message = "岗位编码长度不能超过64个字符")
    private String postCode;
    
    @Schema(description = "岗位名称")
    @Size(max = 50, message = "岗位名称长度不能超过50个字符")
    private String postName;
    
    @Schema(description = "显示顺序")
    private Integer orderNum;
    
    @Schema(description = "状态：0-禁用，1-启用")
    private Integer status;
    
    @Schema(description = "备注")
    private String remark;
} 