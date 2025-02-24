package com.lawfirm.common.core.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体类
 * 提供最基本的实体属性，不包含任何特定技术实现
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 备注
     */
    private String remark;

    /**
     * 插入前处理
     */
    public void preInsert() {
        if (this.createTime == null) {
            this.createTime = LocalDateTime.now();
        }
        if (this.updateTime == null) {
            this.updateTime = this.createTime;
        }
    }

    /**
     * 更新前处理
     */
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }
} 