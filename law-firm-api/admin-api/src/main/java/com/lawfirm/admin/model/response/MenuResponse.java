package com.lawfirm.admin.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "菜单信息响应")
public class MenuResponse {
    
    @Schema(description = "菜单ID")
    private Long id;
    
    @Schema(description = "菜单名称")
    private String menuName;
    
    @Schema(description = "父菜单ID")
    private Long parentId;
    
    @Schema(description = "显示顺序")
    private Integer orderNum;
    
    @Schema(description = "路由地址")
    private String path;
    
    @Schema(description = "组件路径")
    private String component;
    
    @Schema(description = "菜单类型（M目录 C菜单 F按钮）")
    private String menuType;
    
    @Schema(description = "权限标识")
    private String permission;
    
    @Schema(description = "菜单图标")
    private String icon;
    
    @Schema(description = "是否显示（0不显示 1显示）")
    private Integer visible;
    
    @Schema(description = "是否缓存（0不缓存 1缓存）")
    private Integer keepAlive;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
    
    @Schema(description = "备注")
    private String remark;
    
    @Schema(description = "子菜单")
    private List<MenuResponse> children;
} 