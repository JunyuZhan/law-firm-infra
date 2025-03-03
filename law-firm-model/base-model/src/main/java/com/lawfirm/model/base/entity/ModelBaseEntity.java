package com.lawfirm.model.base.entity;

import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lawfirm.common.data.entity.DataEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 业务模型基础实体类
 * 继承DataEntity，添加业务字段
 */
@Getter
@Setter
@Accessors(chain = true)
public abstract class ModelBaseEntity extends DataEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 版本号
     */
    @Version
    @TableField("version")
    private Integer version;

    /**
     * 状态（0-启用，1-禁用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 排序号
     */
    @TableField("sort")
    private Integer sort = 0;

    @Override
    public void preInsert() {
        LocalDateTime now = LocalDateTime.now();
        this.setCreateTime(now);
        this.setUpdateTime(now);
    }

    @Override
    public void preUpdate() {
        this.setUpdateTime(LocalDateTime.now());
        if (this.version != null) {
            this.version++;
        }
    }

    public Integer getVersion() {
        return version;
    }

    public ModelBaseEntity setVersion(Integer version) {
        this.version = version;
        return this;
    }
} 