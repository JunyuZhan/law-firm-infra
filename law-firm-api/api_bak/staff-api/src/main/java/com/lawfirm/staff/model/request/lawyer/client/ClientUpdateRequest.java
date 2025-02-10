package com.lawfirm.staff.model.request.lawyer.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "修改客户请求")
public class ClientUpdateRequest {

    @NotNull(message = "ID不能为空")
    @Schema(description = "ID")
    private Long id;

    @Schema(description = "客户名称")
    private String name;

    @Schema(description = "客户类型")
    private Integer type;

    @Schema(description = "联系人")
    private String contact;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "备注")
    private String remark;
} 