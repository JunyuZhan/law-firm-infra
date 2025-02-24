package com.lawfirm.model.log.dto;

import com.lawfirm.model.base.query.BaseQuery;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.LogLevelEnum;
import com.lawfirm.model.log.enums.LogTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 日志查询DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LogQueryDTO extends BaseQuery {

    /**
     * 日志标题
     */
    private String title;

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
} 