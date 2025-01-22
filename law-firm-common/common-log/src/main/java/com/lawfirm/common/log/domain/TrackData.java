package com.lawfirm.common.log.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lawfirm.common.core.model.BaseEntity;

import java.time.LocalDateTime;

/**
 * 用户行为跟踪数据实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_track_data")
public class TrackData extends BaseEntity {
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * IP地址
     */
    private String ip;
    
    /**
     * 地理位置
     */
    private String location;
    
    /**
     * 请求URL
     */
    private String requestUrl;
    
    /**
     * 请求方法
     */
    private String requestMethod;
    
    /**
     * 请求参数
     */
    private String requestParams;
    
    /**
     * 响应状态
     */
    private Integer responseStatus;
    
    /**
     * 响应时间（毫秒）
     */
    private Long responseTime;
    
    /**
     * 事件类型
     */
    private String eventType;
    
    /**
     * 事件详情
     */
    private String eventDetails;
    
    /**
     * 设备信息
     */
    private String deviceInfo;
    
    /**
     * 浏览器信息
     */
    private String browserInfo;
    
    /**
     * 操作系统
     */
    private String osInfo;
    
    /**
     * 操作时间
     */
    private LocalDateTime operationTime;
    
    /**
     * 停留时长(秒)
     */
    private Long duration;
    
    /**
     * 额外数据(JSON格式)
     */
    private String extraData;
} 