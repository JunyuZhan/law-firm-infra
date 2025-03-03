package com.lawfirm.model.cases.dto.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 案件更新数据传输对象
 * 
 * 继承自CaseBaseDTO，包含更新案件时需要的额外属性，如变更原因等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseUpdateDTO extends CaseBaseDTO {

    private static final long serialVersionUID = 1L;
    
    /**
     * 变更原因
     */
    private String changeReason;

    /**
     * 是否需要审批
     */
    private Boolean needApproval;

    /**
     * 审批人ID
     */
    private Long approverId;

    /**
     * 审批人姓名
     */
    private String approverName;

    /**
     * 是否需要通知团队
     */
    private Boolean notifyTeam;

    /**
     * 是否需要通知客户
     */
    private Boolean notifyClient;

    /**
     * 通知内容
     */
    private String notificationContent;

    /**
     * 更新人ID
     */
    private Long updaterId;

    /**
     * 更新人姓名
     */
    private String updaterName;

    /**
     * 是否仅更新指定字段
     */
    private Boolean updateSpecifiedFieldsOnly;

    /**
     * 需要更新的字段名称，多个字段以逗号分隔
     */
    private String fieldsToUpdate;
} 