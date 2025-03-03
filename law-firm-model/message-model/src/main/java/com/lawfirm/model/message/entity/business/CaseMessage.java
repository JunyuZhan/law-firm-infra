package com.lawfirm.model.message.entity.business;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.message.entity.base.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 案件消息
 */
@Data
@TableName("case_message")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CaseMessage extends BaseMessage {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    @TableField("case_id")
    private Long caseId;

    /**
     * 案件编号
     */
    @TableField("case_no")
    private String caseNo;

    /**
     * 案件名称
     */
    @TableField("case_name")
    private String caseName;

    /**
     * 案件类型
     */
    @TableField("case_type")
    private Integer caseType;

    /**
     * 案件阶段
     */
    @TableField("case_stage")
    private Integer caseStage;

    /**
     * 案件状态
     */
    @TableField("case_status")
    private Integer caseStatus;

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