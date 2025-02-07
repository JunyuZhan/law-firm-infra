package com.lawfirm.admin.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "路由信息响应")
public class RouterResponse {
    
    @Schema(description = "路由名称")
    private String name;
    
    @Schema(description = "路由地址")
    private String path;
    
    @Schema(description = "是否隐藏")
    private Boolean hidden;
    
    @Schema(description = "重定向地址")
    private String redirect;
    
    @Schema(description = "组件地址")
    private String component;
    
    @Schema(description = "路由参数")
    private String query;
    
    @Schema(description = "是否一直显示")
    private Boolean alwaysShow;
    
    @Schema(description = "元信息")
    private MetaVo meta;
    
    @Schema(description = "子路由")
    private List<RouterResponse> children;
    
    @Data
    @Schema(description = "路由元信息")
    public static class MetaVo {
        
        @Schema(description = "标题")
        private String title;
        
        @Schema(description = "图标")
        private String icon;
        
        @Schema(description = "是否缓存")
        private Boolean noCache;
        
        @Schema(description = "链接")
        private String link;
    }
} 