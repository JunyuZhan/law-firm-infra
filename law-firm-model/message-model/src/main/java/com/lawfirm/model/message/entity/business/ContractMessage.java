package com.lawfirm.model.message.entity.business;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.message.entity.base.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 合同消息
 */
@Data
@TableName("contract_message")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ContractMessage extends BaseMessage {

    private static final long serialVersionUID = 1L;

    /**
     * 合同ID
     */
    @TableField("contract_id")
    private Long contractId;

    /**
     * 合同编号
     */
    @TableField("contract_no")
    private String contractNo;

    /**
     * 合同名称
     */
    @TableField("contract_name")
    private String contractName;

    /**
     * 合同类型
     */
    @TableField("contract_type")
    private Integer contractType;

    /**
     * 合同状态
     */
    @TableField("contract_status")
    private Integer contractStatus;

    /**
     * 签约方IDs
     */
    @TableField("party_ids")
    private String partyIds;

    /**
     * 签约方名称
     */
    @TableField("party_names")
    private String partyNames;

    /**
     * 相关文件IDs
     */
    @TableField("file_ids")
    private String fileIds;

    /**
     * 相关任务IDs
     */
    private String taskIds;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 操作描述
     */
    private String operationDesc;
} 