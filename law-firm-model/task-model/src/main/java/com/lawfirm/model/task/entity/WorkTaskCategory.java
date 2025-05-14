package com.lawfirm.model.task.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 任务分类实体类
 */
@Getter
@Accessors(chain = true)
@TableName("work_task_category")
@Schema(description = "任务分类实体类")
public class WorkTaskCategory extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    @TableField("name")
    private String name;
    
    /**
     * 分类编码
     */
    @Schema(description = "分类编码")
    @TableField("code")
    private String code;
    
    /**
     * 分类描述
     */
    @Schema(description = "分类描述")
    @TableField("description")
    private String description;
    
    /**
     * 分类类型：0-系统分类，1-自定义分类
     */
    @Schema(description = "分类类型：0-系统分类，1-自定义分类")
    @TableField("type")
    private Integer type;
    
    /**
     * 父分类ID
     */
    @Schema(description = "父分类ID")
    @TableField("parent_id")
    private Long parentId;
    
    /**
     * 排序号
     */
    @Schema(description = "排序号")
    @TableField("sort")
    private Integer sort;
    
    /**
     * 是否启用：0-禁用，1-启用
     */
    @Schema(description = "是否启用：0-禁用，1-启用")
    @TableField("enabled")
    private Integer enabled;
    
    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    @TableField("tenant_id")
    private Long tenantId;
    
    public WorkTaskCategory setName(String name) {
        this.name = name;
        return this;
    }
    
    public WorkTaskCategory setCode(String code) {
        this.code = code;
        return this;
    }
    
    public WorkTaskCategory setDescription(String description) {
        this.description = description;
        return this;
    }
    
    public WorkTaskCategory setType(Integer type) {
        this.type = type;
        return this;
    }
    
    public WorkTaskCategory setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }
    
    public WorkTaskCategory setEnabled(Integer enabled) {
        this.enabled = enabled;
        return this;
    }
    
    public WorkTaskCategory setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }
} 