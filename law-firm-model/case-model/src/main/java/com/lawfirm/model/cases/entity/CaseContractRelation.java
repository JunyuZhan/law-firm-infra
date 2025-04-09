package com.lawfirm.model.cases.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 案件合同关联实体
 * 表示案件与合同之间的多对多关系
 */
@Data
@TableName("case_contract_relation")
public class CaseContractRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 合同ID
     */
    private Long contractId;

    /**
     * 关联类型（委托合同、服务合同等）
     */
    private Integer relationType;

    /**
     * 关联描述
     */
    private String relationDesc;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 更新人ID
     */
    private Long updateBy;
} 