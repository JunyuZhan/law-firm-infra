package com.lawfirm.model.log.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.log.enums.LogLevelEnum;
import com.lawfirm.model.log.enums.LogTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 日志基类
 * 继承自ModelBaseEntity，复用通用层的基础字段和功能
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class BaseLog extends ModelBaseEntity {

    /**
     * 日志标题
     */
    @TableField("title")
    private String title;

    /**
     * 日志内容
     */
    @TableField("content")
    private String content;

    /**
     * 日志类型
     */
    @TableField("log_type")
    private LogTypeEnum logType;

    /**
     * 日志级别
     */
    @TableField("log_level")
    private LogLevelEnum logLevel;

    /**
     * 操作IP地址
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 操作地点
     */
    @TableField("location")
    private String location;

    /**
     * 操作浏览器
     */
    @TableField("browser")
    private String browser;

    /**
     * 操作系统
     */
    @TableField("os")
    private String os;

    /**
     * 请求URI
     */
    @TableField("request_uri")
    private String requestUri;

    /**
     * 请求方式
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 请求参数
     */
    @TableField("request_params")
    private String requestParams;

    /**
     * 响应结果
     */
    @TableField("response_result")
    private String responseResult;

    /**
     * 执行时间(毫秒)
     */
    @TableField("execution_time")
    private Long executionTime;

    /**
     * 异常信息
     */
    @TableField("exception_info")
    private String exceptionInfo;
} 