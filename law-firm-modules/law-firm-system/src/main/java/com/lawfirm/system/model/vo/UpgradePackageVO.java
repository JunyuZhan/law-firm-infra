package com.lawfirm.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统升级包VO
 */
@Data
@ApiModel("系统升级包VO")
public class UpgradePackageVO {
    
    @ApiModelProperty("ID")
    private Long id;
    
    @ApiModelProperty("升级包名称")
    private String packageName;
    
    @ApiModelProperty("目标版本号")
    private String version;
    
    @ApiModelProperty("升级说明")
    private String description;
    
    @ApiModelProperty("文件大小(字节)")
    private Long fileSize;
    
    @ApiModelProperty("状态(0:待升级 1:升级中 2:升级成功 3:升级失败)")
    private Integer status;
    
    @ApiModelProperty("状态描述")
    private String statusDesc;
    
    @ApiModelProperty("错误信息")
    private String errorMessage;
    
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
    
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
} 