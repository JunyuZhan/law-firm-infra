package com.lawfirm.staff.model.request.clerk.document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "文书分页查询请求")
public class DocumentPageRequest {
    
    @Schema(description = "文书标题")
    private String title;
    
    @Schema(description = "文书类型")
    private String type;
    
    @Schema(description = "创建人")
    private String creator;
    
    @Schema(description = "状态")
    private String status;
    
    @Schema(description = "页码")
    private Integer pageNum = 1;
    
    @Schema(description = "每页条数")
    private Integer pageSize = 10;
} 