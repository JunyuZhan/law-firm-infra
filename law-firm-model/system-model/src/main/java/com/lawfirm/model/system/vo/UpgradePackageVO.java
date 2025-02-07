package com.lawfirm.model.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统升级包VO
 */
@Data
@Schema(description = "系统升级包VO")
public class UpgradePackageVO {
    
    @Schema(description = "主键ID")
    private Long id;
    
    @Schema(description = "升级包名称")
    private String packageName;
    
    @Schema(description = "目标版本号")
    private String version;
    
    @Schema(description = "升级说明")
    private String description;
    
    @Schema(description = "文件大小(字节)")
    private Long fileSize;
    
    @Schema(description = "状态(0:待升级 1:升级中 2:升级成功 3:升级失败)")
    private Integer status;
    
    @Schema(description = "状态描述")
    private String statusDesc;
    
    @Schema(description = "错误信息")
    private String errorMessage;
    
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
} 