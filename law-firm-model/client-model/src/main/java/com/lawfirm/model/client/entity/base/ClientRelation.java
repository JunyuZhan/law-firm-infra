package com.lawfirm.model.client.entity.base;

import com.lawfirm.model.base.BaseModel;
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
public class ClientRelation extends BaseModel {

    /**
     * 关系类型
     */
    private String relationType;

    /**
     * 源客户ID
     */
    private Long sourceClientId;

    /**
     * 目标客户ID
     */
    private Long targetClientId;

    /**
     * 关系描述
     */
    private String relationDesc;

    /**
     * 生效时间
     */
    private LocalDateTime effectiveTime;

    /**
     * 失效时间
     */
    private LocalDateTime expiryTime;

    /**
     * 关系状态 0-待生效 1-生效中 2-已失效
     */
    private Integer status;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 关系属性（JSON格式）
     */
    private String attributes;
} 