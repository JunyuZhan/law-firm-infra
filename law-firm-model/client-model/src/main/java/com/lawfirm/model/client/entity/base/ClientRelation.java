package com.lawfirm.model.client.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 客户关系基类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("client_relation")
public class ClientRelation extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 关系类型
     */
    @TableField("relation_type")
    private String relationType;

    /**
     * 源客户ID
     */
    @TableField("source_client_id")
    private Long sourceClientId;

    /**
     * 目标客户ID
     */
    @TableField("target_client_id")
    private Long targetClientId;

    /**
     * 关系描述
     */
    @TableField("relation_desc")
    private String relationDesc;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 状态（0正常 1停用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 生效时间
     */
    private LocalDateTime effectiveTime;

    /**
     * 失效时间
     */
    private LocalDateTime expiryTime;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 关系属性（JSON格式）
     */
    private String attributes;
} 
