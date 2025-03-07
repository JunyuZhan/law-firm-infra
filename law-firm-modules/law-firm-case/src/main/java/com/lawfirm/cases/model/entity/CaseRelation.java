package com.lawfirm.cases.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.model.cases.entity.Case;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 案件关联实体
 */
@Data
@TableName("case_relation")
@EqualsAndHashCode(callSuper = true)
public class CaseRelation extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 源案件ID
     */
    @TableField("source_case_id")
    private Long sourceCaseId;

    /**
     * 目标案件ID
     */
    @TableField("target_case_id")
    private Long targetCaseId;

    /**
     * 关联类型(PARENT-父案件/CHILD-子案件/RELATED-关联案件/CONFLICT-冲突案件)
     */
    @TableField("relation_type")
    private String relationType;

    /**
     * 关联说明
     */
    @TableField("description")
    private String description;

    /**
     * 是否双向关联
     */
    @TableField("is_bidirectional")
    private Boolean isBidirectional = false;

    /**
     * 关联优先级(1-最高/5-最低)
     */
    @TableField("priority")
    private Integer priority = 3;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 源案件对象
     */
    @TableField(exist = false)
    private Case sourceCase;

    /**
     * 目标案件对象
     */
    @TableField(exist = false)
    private Case targetCase;
} 