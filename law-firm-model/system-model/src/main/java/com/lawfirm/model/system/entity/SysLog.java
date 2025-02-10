package com.lawfirm.model.system.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统日志实体类
 */
@Data
@Entity
@Table(name = "sys_log")
@EqualsAndHashCode(callSuper = true)
public class SysLog extends ModelBaseEntity<SysLog> {
    
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