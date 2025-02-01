package com.lawfirm.system.model.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统日志DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLogDTO extends BaseDTO {
    
    /**
     * 操作模块
     */
    private String module;
    
    /**
     * 操作类型
     */
    private String type;
    
    /**
     * 操作描述
     */
    private String description;
    
    /**
     * 请求方法
     */
    private String method;
    
    /**
     * 请求参数
     */
    private String params;
    
    /**
     * 返回结果
     */
    private String result;
    
    /**
     * 执行时间(ms)
     */
    private Long time;
    
    /**
     * IP地址
     */
    private String ip;
    
    /**
     * 地理位置
     */
    private String location;
    
    /**
     * 浏览器
     */
    private String browser;
    
    /**
     * 操作系统
     */
    private String os;
    
    /**
     * 异常信息
     */
    private String exception;
} 