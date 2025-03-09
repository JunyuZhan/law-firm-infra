package com.lawfirm.model.system.dto.upgrade;

import com.lawfirm.model.base.dto.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 系统升级创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UpgradeCreateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 升级版本号
     */
    @NotBlank(message = "升级版本号不能为空")
    @Size(max = 50, message = "升级版本号长度不能超过50个字符")
    private String version;

    /**
     * 升级名称
     */
    @NotBlank(message = "升级名称不能为空")
    @Size(max = 100, message = "升级名称长度不能超过100个字符")
    private String name;
    
    /**
     * 升级描述
     */
    @Size(max = 500, message = "升级描述长度不能超过500个字符")
    private String description;
    
    /**
     * 适用环境
     */
    @Size(max = 100, message = "适用环境长度不能超过100个字符")
    private String environment;
    
    /**
     * 计划升级时间
     */
    private LocalDateTime plannedTime;
    
    /**
     * 是否强制升级
     */
    private Boolean forceUpgrade;
} 