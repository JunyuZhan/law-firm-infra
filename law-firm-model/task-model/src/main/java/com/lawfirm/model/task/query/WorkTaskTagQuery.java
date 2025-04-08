package com.lawfirm.model.task.query;

import com.lawfirm.model.base.query.BaseQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 工作任务标签查询条件
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Schema(description = "工作任务标签查询参数")
public class WorkTaskTagQuery extends BaseQuery {
    
    /**
     * 标签名称，支持模糊查询
     */
    @Schema(description = "标签名称，支持模糊查询")
    private String name;
    
    /**
     * 标签描述
     */
    private String description;
    
    /**
     * 标签颜色
     */
    @Schema(description = "标签颜色")
    private String color;
    
    /**
     * 租户ID
     */
    private Long tenantId;
    
    /**
     * 创建者ID
     */
    @Schema(description = "创建者ID")
    private Long creatorId;
    
    /**
     * 是否按使用次数排序
     */
    @Schema(description = "是否按使用次数排序")
    private Boolean orderByUsage;
} 