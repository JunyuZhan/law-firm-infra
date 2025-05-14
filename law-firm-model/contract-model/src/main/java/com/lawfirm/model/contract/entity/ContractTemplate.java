package com.lawfirm.model.contract.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 合同模板实体类
 * 用于管理预设的合同模板
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("contract_template")
@Schema(description = "合同模板实体类，用于管理预设的合同模板")
public class ContractTemplate extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "模板名称")
    @TableField("template_name")
    private String templateName;   // 模板名称
    
    @Schema(description = "模板编码")
    @TableField("template_code")
    private String templateCode;   // 模板编码
    
    @Schema(description = "模板类型(诉讼代理、非诉讼法律服务、法律顾问等)")
    @TableField("template_type")
    private String templateType;   // 模板类型(诉讼代理、非诉讼法律服务、法律顾问等)
    
    @Schema(description = "模板内容")
    @TableField("content")
    private String content;        // 模板内容
    
    @Schema(description = "状态(0-禁用，1-启用)")
    @TableField("status")
    private Integer status;        // 状态(0-禁用，1-启用)
    
    @Schema(description = "模板版本号")
    @TableField("template_version")
    private String templateVersion; // 模板版本号
    
    @Schema(description = "是否默认模板")
    @TableField("is_default")
    private Boolean isDefault;     // 是否默认模板
    
    @Schema(description = "分类")
    @TableField("category")
    private String category;       // 分类
    
    @Schema(description = "所属部门ID")
    @TableField("department_id")
    private Long departmentId;     // 所属部门ID
    
    @Schema(description = "创建人ID")
    @TableField("creator_id")
    private Long creatorId;        // 创建人ID
    
    @Schema(description = "审核人ID")
    @TableField("reviewer_id")
    private Long reviewerId;       // 审核人ID
    
    @Schema(description = "变量列表，JSON格式")
    @TableField("variables")
    private String variables;      // 变量列表，JSON格式
    
    @Schema(description = "模板描述")
    @TableField("description")
    private String description;    // 模板描述
    
    @Schema(description = "使用次数")
    @TableField("usage_count")
    private Integer usageCount;    // 使用次数
} 