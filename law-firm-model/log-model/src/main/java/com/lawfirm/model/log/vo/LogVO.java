package com.lawfirm.model.log.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.LogLevelEnum;
import com.lawfirm.model.log.enums.LogTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 日志视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LogVO extends BaseVO {

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
     * 操作类型
     */
    private OperateTypeEnum operateType;

    /**
     * 业务类型
     */
    private BusinessTypeEnum businessType;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人名称
     */
    private String operatorName;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

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
     * 错误消息
     */
    private String errorMsg;

    /**
     * 执行时长(毫秒)
     */
    private Long costTime;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operateTime;
} 