package com.lawfirm.model.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统升级日志VO
 */
@Data
@Schema(description = "升级日志VO")
public class UpgradeLogVO {
    
    @Schema(description = "主键ID")
    private Long id;
    
    @Schema(description = "升级包ID")
    private Long packageId;
    
    @Schema(description = "升级包名称")
    private String packageName;
    
    @Schema(description = "升级包版本")
    private String version;
    
    @Schema(description = "升级状态")
    private Integer status;
    
    @Schema(description = "升级结果")
    private String result;
    
    @Schema(description = "升级时间")
    private String upgradeTime;
    
    @Schema(description = "操作类型")
    private String operation;
    
    @Schema(description = "详细信息")
    private String detail;
    
    @Schema(description = "是否成功")
    private Boolean success;
    
    @Schema(description = "错误信息")
    private String errorMessage;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
} 