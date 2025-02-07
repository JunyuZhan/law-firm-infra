package com.lawfirm.staff.model.request.lawyer.client;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "创建客户请求")
public class ClientCreateRequest {

    @NotBlank(message = "客户名称不能为空")
    @Schema(description = "客户名称")
    private String name;

    @NotNull(message = "客户类型不能为空")
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