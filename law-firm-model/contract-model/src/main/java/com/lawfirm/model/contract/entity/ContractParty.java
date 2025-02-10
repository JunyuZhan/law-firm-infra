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
 * 合同参与方实体
 */
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = ContractConstants.TableNames.CONTRACT_PARTY)
public class ContractParty extends ModelBaseEntity<ContractParty> {

    @NotNull(message = "合同ID不能为空")
    private Long contractId;  // 合同ID

    @NotBlank(message = "参与方名称不能为空")
    @Size(max = ContractConstants.FieldLength.PARTY_NAME, message = "参与方名称长度不能超过{max}个字符")
    @Column(nullable = false, length = ContractConstants.FieldLength.PARTY_NAME)
    private String partyName;  // 参与方名称

    @NotBlank(message = "参与方类型不能为空")
    @Column(nullable = false, length = 20)
    private String partyType;  // 参与方类型（甲方、乙方、丙方等）

    private Long partyId;      // 参与方ID（关联到客户、律所等）
    private String partyRole;  // 参与方角色（签约方、见证方等）

    @Size(max = 50, message = "联系人长度不能超过50个字符")
    @Column(length = 50)
    private String contactPerson;  // 联系人

    @Size(max = 50, message = "联系电话长度不能超过50个字符")
    @Column(length = 50)
    private String contactPhone;   // 联系电话

    @Size(max = 100, message = "电子邮箱长度不能超过100个字符")
    @Column(length = 100)
    private String email;         // 电子邮箱

    @Size(max = 200, message = "地址长度不能超过200个字符")
    @Column(length = 200)
    private String address;       // 地址

    @Size(max = 50, message = "统一社会信用代码长度不能超过50个字符")
    @Column(length = 50)
    private String socialCreditCode;  // 统一社会信用代码

    @Size(max = 50, message = "法定代表人长度不能超过50个字符")
    @Column(length = 50)
    private String legalRepresentative;  // 法定代表人

    private Boolean isSignatory;  // 是否签约方
    private Integer signOrder;    // 签约顺序

    @Size(max = ContractConstants.FieldLength.REMARK, message = "备注长度不能超过{max}个字符")
    @Column(length = ContractConstants.FieldLength.REMARK)
    private String remark;  // 备注
}

