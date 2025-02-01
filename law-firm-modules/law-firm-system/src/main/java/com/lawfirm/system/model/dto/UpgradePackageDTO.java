package com.lawfirm.system.model.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 系统升级包DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统升级包DTO")
public class UpgradePackageDTO extends BaseDTO {
    
    @ApiModelProperty("升级包名称")
    @NotBlank(message = "升级包名称不能为空")
    @Size(max = 100, message = "升级包名称长度不能超过100")
    private String packageName;
    
    @ApiModelProperty("目标版本号")
    @NotBlank(message = "目标版本号不能为空")
    @Size(max = 20, message = "目标版本号长度不能超过20")
    private String version;
    
    @ApiModelProperty("升级说明")
    @Size(max = 500, message = "升级说明长度不能超过500")
    private String description;
    
    /**
     * 是否需要备份
     */
    private Boolean needBackup;
} 