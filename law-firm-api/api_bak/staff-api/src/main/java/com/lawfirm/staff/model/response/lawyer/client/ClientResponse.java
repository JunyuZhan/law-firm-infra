package com.lawfirm.staff.model.response.lawyer.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "客户响应")
public class ClientResponse {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "客户名称")
    private String name;

    @Schema(description = "客户类型")
    private Integer type;

    @Schema(description = "客户类型名称")
    private String typeName;

    @Schema(description = "联系人")
    private String contact;

    @Schema(description = "联系电话")
    private String phone;

    @Schema(description = "电子邮箱")
    private String email;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "状态名称")
    private String statusName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private String createTime;

    @Schema(description = "更新时间")
    private String updateTime;
} 