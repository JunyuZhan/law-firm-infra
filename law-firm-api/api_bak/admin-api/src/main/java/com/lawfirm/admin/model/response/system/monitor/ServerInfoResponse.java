package com.lawfirm.admin.model.response.system.monitor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "服务器信息响应")
public class ServerInfoResponse {
    
    @Schema(description = "操作系统")
    private String os;
    
    @Schema(description = "服务器名称")
    private String computerName;
    
    @Schema(description = "服务器IP")
    private String computerIp;
    
    @Schema(description = "项目路径")
    private String userDir;
    
    @Schema(description = "操作系统架构")
    private String osArch;
    
    @Schema(description = "处理器数量")
    private Integer processors;
    
    @Schema(description = "总内存")
    private String totalMemory;
    
    @Schema(description = "剩余内存")
    private String freeMemory;
    
    @Schema(description = "使用率")
    private Double usageRate;
    
    @Schema(description = "启动时间")
    private String startTime;
    
    @Schema(description = "运行时间")
    private String runTime;
} 