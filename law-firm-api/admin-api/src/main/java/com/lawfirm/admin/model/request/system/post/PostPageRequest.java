// 路径: law-firm-api/admin-api/src/main/java/com/lawfirm/admin/model/request/system/post/PostPageRequest.java
package com.lawfirm.admin.model.request.system.post;

import com.lawfirm.common.core.model.page.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "岗位分页请求")
public class PostPageRequest extends BasePageRequest {

    @Schema(description = "岗位名称")
    private String postName;

    @Schema(description = "岗位编码")
    private String postCode;

    @Schema(description = "状态（0正常 1停用）")
    private Integer status;
}