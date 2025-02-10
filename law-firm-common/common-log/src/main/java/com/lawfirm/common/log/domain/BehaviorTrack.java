package com.lawfirm.common.log.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.core.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 行为追踪实体
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("behavior_track")
public class BehaviorTrack extends BaseEntity {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    /**
     * IP地址
     */
    private String ipAddress;

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
     * 执行时长(毫秒)
     */
    private Long executionTime;

    /**
     * 操作结果
     */
    private String result;
} 