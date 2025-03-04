package com.lawfirm.model.log.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.log.enums.LogLevelEnum;
import com.lawfirm.model.log.enums.LogTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 日志基础DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BaseLogDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 日志类型
     */
    private LogTypeEnum logType;

    /**
     * 日志级别
     */
    private LogLevelEnum logLevel;

    /**
     * 操作IP地址
     */
    private String ipAddress;

    /**
     * 操作地点
     */
    private String location;

    /**
     * 操作浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 响应结果
     */
    private String responseResult;

    /**
     * 执行时间(毫秒)
     */
    private Long executionTime;

    /**
     * 异常信息
     */
    private String exceptionInfo;
} 