package com.lawfirm.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import java.util.List;
import lombok.experimental.Accessors;

/**
 * 部门实体类
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dept")
@Accessors(chain = true)
public class SysDept extends ModelBaseEntity<SysDept> {
    
    /**
     * 部门名称
     */
    @TableField("name")
    private String name;
    
    /**
     * 父部门ID
     */
    @TableField("parent_id")
    private Long parentId;
    
    /**
     * 排序
     */
    @TableField("ancestors")
    private String ancestors;
    
    /**
     * 负责人
     */
    @TableField("leader")
    private String leader;
    
    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;
    
    /**
     * 邮箱
     */
    @TableField("email")
    private String email;
    
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    
    @TableField(exist = false)
    private List<SysDept> children;
} 