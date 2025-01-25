package com.lawfirm.contract.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 合同条款实体类
 */
@Data
@TableName("contract_clause")
public class ContractClause {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 合同ID
     */
    private Long contractId;
    
    /**
     * 条款标题
     */
    private String title;
    
    /**
     * 条款内容
     */
    private String content;
    
    /**
     * 条款序号
     */
    private Integer orderNum;
    
    /**
     * 条款类型
     */
    private Integer type;
    
    /**
     * 是否必要条款
     */
    private Boolean required;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 创建人
     */
    private String createBy;
    
    /**
     * 更新人
     */
    private String updateBy;
    
    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;
} 