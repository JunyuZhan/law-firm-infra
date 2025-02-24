// 路径: law-firm-api/admin-api/src/main/java/com/lawfirm/admin/model/request/auth/role/RolePageRequest.java
package com.lawfirm.admin.model.request.auth.role;

import com.lawfirm.common.core.model.page.BasePageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色分页请求")
public class RolePageRequest extends BasePageRequest {

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色编码")
    private String roleCode;

    @Schema(description = "状态（0正常 1停用）")
    private Integer status;

    @Schema(description = "创建时间开始")
    private String createTimeStart;

    @Schema(description = "创建时间结束")
    private String createTimeEnd;
import com.lawfirm.model.base.enums.BaseEnum  
