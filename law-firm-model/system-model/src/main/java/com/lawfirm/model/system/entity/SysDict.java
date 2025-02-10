package com.lawfirm.model.system.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统字典实体类
 */
@Data
@Entity
@Table(name = "sys_dict")
@EqualsAndHashCode(callSuper = true)
public class SysDict extends ModelBaseEntity<SysDict> {
    
    /**
     * 字典编码
     */
    private String dictCode;
    
    /**
     * 字典名称
     */
    private String dictName;
    
    /**
     * 字典类型
     */
    private String dictType;
    
    /**
     * 字典值
     */
    private String dictValue;
} 