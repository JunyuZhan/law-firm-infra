package com.lawfirm.model.log.dto;

import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 审计日志查询条件
 */
@Data
public class AuditLogQuery {

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人名称
     */
    private String operatorName;

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
     * 操作状态（0正常 1异常）
     */
    private Integer status;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页大小
     */
    private Integer pageSize = 10;
} 