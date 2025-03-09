package com.lawfirm.model.system.vo.upgrade;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统升级VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UpgradeVO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 升级ID
     */
    private Long id;

    /**
     * 升级版本号
     */
    private String version;

    /**
     * 升级名称
     */
    private String name;
    
    /**
     * 升级描述
     */
    private String description;
    
    /**
     * 适用环境
     */
    private String environment;
    
    /**
     * 计划升级时间
     */
    private LocalDateTime plannedTime;
    
    /**
     * 实际升级时间
     */
    private LocalDateTime actualTime;
    
    /**
     * 升级状态
     */
    private String status;
    
    /**
     * 升级状态名称
     */
    private String statusName;
    
    /**
     * 是否强制升级
     */
    private Boolean forceUpgrade;
    
    /**
     * 升级结果
     */
    private String result;
    
    /**
     * 创建者
     */
    private String createBy;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新者
     */
    private String updateBy;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 补丁列表
     */
    private ArrayList<PatchVO> patches = new ArrayList<>();
} 