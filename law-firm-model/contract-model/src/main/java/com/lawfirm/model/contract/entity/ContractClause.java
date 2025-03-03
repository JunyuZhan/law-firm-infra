package com.lawfirm.model.contract.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 合同条款实体类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("contract_clause")
public class ContractClause extends ModelBaseEntity {
    private static final long serialVersionUID = 1L;
    
    @TableField("contract_id")
    private Long contractId;       // 合同ID
    
    @TableField("clause_content")
    private String clauseContent;   // 条款内容
    
    @TableField("clause_type")
    private String clauseType;      // 条款类型
    
    @TableField("sort_order")
    private Integer sortOrder;      // 排序号
} 