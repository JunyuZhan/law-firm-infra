package com.lawfirm.model.system.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 系统升级包DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统升级包DTO")
@Builder
public class UpgradePackageDTO extends BaseDTO {
    
    @Schema(description = "升级包名称")
    @NotBlank(message = "升级包名称不能为空")
    @Size(max = 100, message = "升级包名称长度不能超过100")
    private String name;
    
    @Schema(description = "目标版本号")
    @NotBlank(message = "目标版本号不能为空")
    @Size(max = 20, message = "目标版本号长度不能超过20")
    private String version;
    
    @Schema(description = "升级说明")
    @Size(max = 500, message = "升级说明长度不能超过500")
    private String description;
    
    /**
     * 是否需要备份
     */
    @Schema(description = "是否需要备份")
    private Boolean needBackup;

    /**
     * 升级包内容（Base64编码）
     */
    private String content;
} 