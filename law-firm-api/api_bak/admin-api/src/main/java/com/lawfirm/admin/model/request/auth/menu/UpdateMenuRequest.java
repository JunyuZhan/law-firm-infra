// 路径: law-firm-api/admin-api/src/main/java/com/lawfirm/admin/model/request/auth/menu/UpdateMenuRequest.java
package com.lawfirm.admin.model.request.auth.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "更新菜单请求")
public class UpdateMenuRequest {

    @NotNull(message = "菜单ID不能为空")
    @Schema(description = "菜单ID")
    private Long id;

    @NotBlank(message = "菜单名称不能为空")
    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "父菜单ID")
    private Long parentId;

    @NotNull(message = "显示顺序不能为空")
    @Schema(description = "显示顺序")
    private Integer orderNum;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "是否为外链（0是 1否）")
    private Integer isFrame;

    @Schema(description = "是否缓存（0缓存 1不缓存）")
    private Integer isCache;

    @NotBlank(message = "菜单类型不能为空")
    @Schema(description = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;

    @Schema(description = "菜单状态（0显示 1隐藏）")
    private Integer visible;

    @Schema(description = "菜单状态（0正常 1停用）")
    private Integer status;

    @Schema(description = "权限标识")
    private String perms;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "备注")
    private String remark;
import com.lawfirm.model.base.enums.BaseEnum  
