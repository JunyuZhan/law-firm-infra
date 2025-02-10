package com.lawfirm.model.contract.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.contract.constants.ContractConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 合同条款实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = ContractConstants.TableNames.CONTRACT_CLAUSE)
public class ContractClause extends ModelBaseEntity<ContractClause> {

    @NotNull(message = "合同ID不能为空")
    private Long contractId;  // 合同ID

    @NotBlank(message = "条款标题不能为空")
    @Size(max = ContractConstants.FieldLength.CLAUSE_TITLE, message = "条款标题长度不能超过{max}个字符")
    @Column(nullable = false, length = ContractConstants.FieldLength.CLAUSE_TITLE)
    private String title;  // 条款标题

    @NotBlank(message = "条款内容不能为空")
    @Size(max = ContractConstants.FieldLength.CLAUSE_CONTENT, message = "条款内容长度不能超过{max}个字符")
    @Column(nullable = false, length = ContractConstants.FieldLength.CLAUSE_CONTENT)
    private String content;  // 条款内容

    private Integer clauseNumber;  // 条款序号
    private String clauseType;     // 条款类型
    private Boolean isRequired;    // 是否必要条款
    private Boolean isStandard;    // 是否标准条款
    private Boolean isNegotiable;  // 是否可协商

    @Size(max = ContractConstants.FieldLength.REMARK, message = "备注长度不能超过{max}个字符")
    @Column(length = ContractConstants.FieldLength.REMARK)
    private String remark;  // 备注

    // 版本控制
    private Integer version;        // 版本号
    private String changeHistory;   // 修改历史
    private Long lastModifierId;    // 最后修改人ID
    private String lastModifierName;// 最后修改人姓名
}

