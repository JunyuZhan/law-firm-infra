package com.lawfirm.model.log.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.LogLevelEnum;
import com.lawfirm.model.log.enums.LogTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 日志创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LogCreateDTO extends BaseDTO {

    /**
     * 日志标题
     */
    @NotBlank(message = "日志标题不能为空")
    private String title;

    /**
     * 日志内容
     */
    private String content;

    /**
     * 日志类型
     */
    @NotNull(message = "日志类型不能为空")
    private LogTypeEnum logType;

    /**
     * 日志级别
     */
    @NotNull(message = "日志级别不能为空")
    private LogLevelEnum logLevel;

    /**
     * 操作类型
     */
    private OperateTypeEnum operateType;

    /**
     * 业务类型
     */
    private BusinessTypeEnum businessType;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作描述
     */
    private String description;

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
} 