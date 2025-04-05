package com.lawfirm.model.schedule.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 * 会议室数据传输对象
 */
@Data
@Accessors(chain = true)
public class MeetingRoomDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 会议室ID
     */
    private Long id;
    
    /**
     * 会议室名称
     */
    @NotBlank(message = "会议室名称不能为空")
    @Size(max = 50, message = "会议室名称不能超过50个字符")
    private String name;
    
    /**
     * 会议室地址
     */
    @NotBlank(message = "会议室地址不能为空")
    @Size(max = 100, message = "会议室地址不能超过100个字符")
    private String address;
    
    /**
     * 会议室容量
     */
    @NotNull(message = "会议室容量不能为空")
    private Integer capacity;
    
    /**
     * 会议室设备描述
     */
    @Size(max = 500, message = "会议室设备描述不能超过500个字符")
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
     * 管理员ID
     */
    private Long managerId;
    
    /**
     * 备注
     */
    @Size(max = 255, message = "备注不能超过255个字符")
    private String remarks;
} 