package com.lawfirm.admin.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "在线用户响应")
public class OnlineUserResponse {
    
    @Schema(description = "会话编号")
    private String tokenId;
    
    @Schema(description = "用户名")
    private String username;
    
    @Schema(description = "部门名称")
    private String deptName;
    
    @Schema(description = "登录IP")
    private String ipaddr;
    
    @Schema(description = "登录地点")
    private String loginLocation;
    
    @Schema(description = "浏览器")
    private String browser;
    
    @Schema(description = "操作系统")
    private String os;
    
    @Schema(description = "登录时间")
    private LocalDateTime loginTime;
} 