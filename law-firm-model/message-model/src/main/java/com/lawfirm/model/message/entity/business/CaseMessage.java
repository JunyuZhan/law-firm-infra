package com.lawfirm.model.message.entity.business;

import com.lawfirm.model.message.entity.base.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 案件消息
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CaseMessage extends BaseMessage {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 案件编号
     */
    private String caseNo;

    /**
     * 案件名称
     */
    private String caseName;

    /**
     * 案件类型
     */
    private Integer caseType;

    /**
     * 案件阶段
     */
    private Integer caseStage;

    /**
     * 案件状态
     */
    private Integer caseStatus;

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