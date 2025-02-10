package com.lawfirm.model.system.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统模板实体类
 */
@Data
@Entity
@Table(name = "sys_template")
@EqualsAndHashCode(callSuper = true)
public class SysTemplate extends ModelBaseEntity<SysTemplate> {

    /**
     * 模板名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 模板编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 模板内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 模板类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 是否启用
     */
    @Column(name = "enabled")
    private Boolean enabled;
} 