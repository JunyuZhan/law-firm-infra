package com.lawfirm.system.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统升级日志VO
 */
@Data
@ApiModel("系统升级日志VO")
public class UpgradeLogVO {
    
    @ApiModelProperty("ID")
    private Long id;
    
    @ApiModelProperty("升级包ID")
    private Long packageId;
    
    @ApiModelProperty("操作类型")
    private String operation;
    
    @ApiModelProperty("详细信息")
    private String detail;
    
    @ApiModelProperty("是否成功")
    private Boolean success;
    
    @ApiModelProperty("错误信息")
    private String errorMessage;
    
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
} 