package com.lawfirm.model.message.entity.business;

import com.lawfirm.model.message.entity.base.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 合同消息
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ContractMessage extends BaseMessage {

    private static final long serialVersionUID = 1L;

    /**
     * 合同ID
     */
    private Long contractId;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 合同名称
     */
    private String contractName;

    /**
     * 合同类型
     */
    private Integer contractType;

    /**
     * 合同状态
     */
    private Integer contractStatus;

    /**
     * 签约方IDs
     */
    private String partyIds;

    /**
     * 签约方名称
     */
    private String partyNames;

    /**
     * 相关文件IDs
     */
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