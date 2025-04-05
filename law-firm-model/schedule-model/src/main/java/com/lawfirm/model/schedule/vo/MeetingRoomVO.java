package com.lawfirm.model.schedule.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 会议室视图对象
 */
@Data
@Accessors(chain = true)
public class MeetingRoomVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 会议室ID
     */
    private Long id;
    
    /**
     * 会议室名称
     */
    private String name;
    
    /**
     * 会议室地址
     */
    private String address;
    
    /**
     * 会议室容量
     */
    private Integer capacity;
    
    /**
     * 会议室设备描述
     */
    private String equipments;
    
    /**
     * 会议室状态
     * 0: 未启用
     * 1: 启用
     * 2: 维护中
     * 3: 已废弃
     */
    private Integer status;
    
    /**
     * 状态名称
     */
    private String statusName;
    
    /**
     * 管理员ID
     */
    private Long managerId;
    
    /**
     * 管理员姓名
     */
    private String managerName;
    
    /**
     * 备注
     */
    private String remarks;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 当前是否可用（仅在查询可用会议室时返回）
     */
    private Boolean available;
} 