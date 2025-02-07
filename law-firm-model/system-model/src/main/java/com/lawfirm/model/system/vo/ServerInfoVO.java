package com.lawfirm.model.system.vo;

import com.lawfirm.common.data.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务器信息视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServerInfoVO extends BaseVO {
    
    /**
     * 服务器名称
     */
    private String serverName;
    
    /**
     * 操作系统
     */
    private String osName;
    
    /**
     * 系统架构
     */
    private String osArch;
    
    /**
     * CPU核心数
     */
    private Integer cpuNum;
    
    /**
     * 总内存
     */
    private Long totalMemory;
    
    /**
     * 已用内存
     */
    private Long usedMemory;
    
    /**
     * 剩余内存
     */
    private Long freeMemory;
    
    /**
     * 系统负载
     */
    private Double systemLoad;
} 