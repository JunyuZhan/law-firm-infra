package com.lawfirm.model.log.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.log.entity.base.AuditableLog;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 系统日志实体
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_system_log")
public class SystemLog extends AuditableLog {

    private static final long serialVersionUID = 1L;

    /**
     * 系统模块
     */
    @TableField("system_module")
    private String systemModule;

    /**
     * 功能名称
     */
    @TableField("function_name")
    private String functionName;

    /**
     * 方法名称
     */
    @TableField("method_name")
    private String methodName;

    /**
     * 请求方式
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 请求URL
     */
    @TableField("request_url")
    private String requestUrl;

    /**
     * 请求参数
     */
    @TableField("request_params")
    private String requestParams;

    /**
     * 返回参数
     */
    @TableField("response_params")
    private String responseParams;

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