package com.lawfirm.model.cases.dto.base;

import com.lawfirm.model.cases.enums.base.CaseTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 案件更新数据传输对象
 * 
 * 继承自CaseBaseDTO，包含更新案件时需要的额外属性，如变更原因等
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "案件更新DTO")
public class CaseUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "案件ID")
    private Long id;

    @Schema(description = "案件名称")
    private String name;

    @Schema(description = "案件类型")
    private CaseTypeEnum caseType;

    @Schema(description = "案件描述")
    private String description;

    @Schema(description = "案件标签")
    private transient List<String> tags;

    @Schema(description = "主办律师ID")
    private Long leaderId;

    @Schema(description = "团队成员ID列表")
    private transient List<Long> teamMemberIds;

    @Schema(description = "对方当事人")
    private transient List<String> oppositeParties;

    @Schema(description = "备注")
    private String remark;

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

    // 转换为CaseBaseDTO
    public CaseBaseDTO toBaseDTO() {
        CaseBaseDTO baseDTO = new CaseBaseDTO();
        baseDTO.setId(this.id);
        baseDTO.setCaseType(this.caseType);
        baseDTO.setCaseName(this.name);
        baseDTO.setCaseDescription(this.description);
        baseDTO.setLawyerId(this.leaderId);
        baseDTO.setRemarks(this.remark);
        if (this.tags != null && !this.tags.isEmpty()) {
            baseDTO.setCaseTags(String.join(",", this.tags));
        }
        if (this.oppositeParties != null && !this.oppositeParties.isEmpty()) {
            baseDTO.setOpposingParty(String.join(",", this.oppositeParties));
        }
        return baseDTO;
    }
} 