package com.lawfirm.model.workflow.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lawfirm.model.workflow.enums.ProcessStatusEnum;
import com.lawfirm.model.workflow.enums.ProcessTypeEnum;
import com.lawfirm.model.workflow.enums.TaskPriorityEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * 流程视图对象
 *
 * @author claude
 */
@Data
@Accessors(chain = true)
public class ProcessVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 流程编号
     */
    private String processNo;
    
    /**
     * 流程名称
     */
    private String processName;
    
    /**
     * 流程类型
     * @see ProcessTypeEnum
     */
    private ProcessTypeEnum processType;
    
    /**
     * 流程状态
     * @see ProcessStatusEnum
     */
    private ProcessStatusEnum status;
    
    /**
     * 流程描述
     */
    private String description;
    
    /**
     * 业务ID
     */
    private Long businessId;
    
    /**
     * 业务类型
     */
    private String businessType;
    
    /**
     * 发起人ID
     */
    private Long initiatorId;
    
    /**
     * 发起人名称
     */
    private String initiatorName;
    
    /**
     * 发起时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    
    /**
     * 当前处理人ID
     */
    private Long currentHandlerId;
    
    /**
     * 当前处理人名称
     */
    private String currentHandlerName;
    
    /**
     * 优先级
     * @see TaskPriorityEnum
     */
    private TaskPriorityEnum priority;
    
    /**
     * 是否允许撤回
     * true: 允许
     * false: 不允许
     */
    private Boolean allowRevoke;
    
    /**
     * 是否允许转办
     * true: 允许
     * false: 不允许
     */
    private Boolean allowTransfer;
    
    /**
     * 流程配置（JSON格式）
     */
    private String processConfig;
    
    /**
     * 流程变量
     */
    @JsonIgnore
    private HashMap<String, Serializable> variables;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 租户ID
     */
    private Long tenantId;
}