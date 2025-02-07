package com.lawfirm.model.organization.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("org_department")
public class Department extends BaseEntity {
    
    /**
     * 部门名称
     */
    private String name;
    
    /**
     * 部门编码
     */
    private String code;
    
    /**
     * 部门负责人
     */
    private String manager;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 父部门ID
     */
    private Long parentId;
    
    /**
     * 排序号
     */
    private Integer sortOrder;
    
    /**
     * 是否启用
     */
    private Boolean enabled;
    
    /**
     * 部门类型
     */
    private String type;
    
    /**
     * 所属律所ID
     */
    private Long lawFirmId;
    
    /**
     * 描述
     */
    private String description;
} 