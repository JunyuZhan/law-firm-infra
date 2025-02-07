// 路径: law-firm-api/admin-api/src/main/java/com/lawfirm/admin/model/response/system/template/TemplateResponse.java
package com.lawfirm.admin.model.response.system.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "模板响应")
public class TemplateResponse {

    @Schema(description = "模板ID")
    private Long id;

    @Schema(description = "模板名称")
    private String templateName;

    @Schema(description = "模板类型")
    private String templateType;

    @Schema(description = "模板内容")
    private String content;

    @Schema(description = "显示顺序")
    private Integer orderNum;

    @Schema(description = "状态（0正常 1停用）")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}