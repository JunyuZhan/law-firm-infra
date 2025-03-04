package com.lawfirm.core.workflow.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * 流程视图对象
 *
 * @author JunyuZhan
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
     * 租户ID
     */
    private Long tenantId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 创建�?     */
    private String createBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 更新�?     */
    private String updateBy;

    /**
     * 状态（0正常 1停用�?     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 流程名称
     */
    private String processName;
    
    /**
     * 流程Key
     */
    private String processKey;
    
    /**
     * 流程分类
     */
    private String category;
    
    /**
     * 流程描述
     */
    private String description;
    
    /**
     * 当前节点
     */
    private String currentNode;
    
    /**
     * 当前处理人ID
     */
    private Long currentHandlerId;
    
    /**
     * 当前处理人名�?     */
    private String currentHandlerName;
    
    /**
     * 开始时�?     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    
    /**
     * 是否挂起
     */
    private Boolean suspended;
    
    /**
     * 业务数据ID
     */
    private Long businessId;
    
    /**
     * 业务类型
     */
    private String businessType;
    
    /**
     * 流程变量
     */
    @JsonIgnore
    private HashMap<String, Serializable> variables;
} 
