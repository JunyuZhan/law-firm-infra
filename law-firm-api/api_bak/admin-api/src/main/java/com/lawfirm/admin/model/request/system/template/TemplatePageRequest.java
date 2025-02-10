// 路径: law-firm-api/admin-api/src/main/java/com/lawfirm/admin/model/request/system/template/TemplatePageRequest.java
package com.lawfirm.admin.model.request.system.template;

import com.lawfirm.common.core.model.page.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "模板分页请求")
public class TemplatePageRequest extends BasePageRequest {

    @Schema(description = "模板名称")
    private String templateName;

    @Schema(description = "模板类型")
    private String templateType;

    @Schema(description = "状态（0正常 1停用）")
    private Integer status;
}