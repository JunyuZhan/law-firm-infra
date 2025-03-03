package com.lawfirm.model.cases.dto.team;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.cases.enums.team.CaseParticipantTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 案件参与方数据传输对象
 * 
 * 包含参与方的基本信息，如参与方类型、名称、联系方式等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseParticipantDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    private Long caseId;

    /**
     * 案件编号
     */
    private String caseNumber;

    /**
     * 参与方类型
     */
    private CaseParticipantTypeEnum participantType;

    /**
     * 参与方名称（个人姓名或组织名称）
     */
    private String participantName;

    /**
     * 参与方角色说明
     */
    private String roleDescription;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 联系地址
     */
    private String contactAddress;

    /**
     * 证件类型（1-身份证，2-护照，3-营业执照，4-其他）
     */
    private Integer idType;

    /**
     * 证件号码
     */
    private String idNumber;

    /**
     * 是否为组织/公司
     */
    private Boolean isOrganization;

    /**
     * 组织类型（企业、政府机构、社会组织等）
     */
    private String organizationType;

    /**
     * 法定代表人
     */
    private String legalRepresentative;

    /**
     * 统一社会信用代码
     */
    private String socialCreditCode;

    /**
     * 是否为主要参与方
     */
    private Boolean isPrimary;

    /**
     * 是否需要通知
     */
    private Boolean needNotification;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 获取证件信息
     */
    public String getIdInfo() {
        if (this.idNumber == null || this.idNumber.isEmpty()) {
            return "";
        }
        
        String idTypeName = "其他证件";
        if (this.idType != null) {
            switch (this.idType) {
                case 1:
                    idTypeName = "身份证";
                    break;
                case 2:
                    idTypeName = "护照";
                    break;
                case 3:
                    idTypeName = "营业执照";
                    break;
                default:
                    idTypeName = "其他证件";
            }
        }
        
        return idTypeName + ": " + this.idNumber;
    }

    /**
     * 获取完整联系信息
     */
    public String getFullContactInfo() {
        StringBuilder sb = new StringBuilder();
        
        if (this.contactPerson != null && !this.contactPerson.isEmpty()) {
            sb.append("联系人: ").append(this.contactPerson).append(", ");
        }
        
        if (this.contactPhone != null && !this.contactPhone.isEmpty()) {
            sb.append("电话: ").append(this.contactPhone).append(", ");
        }
        
        if (this.contactEmail != null && !this.contactEmail.isEmpty()) {
            sb.append("邮箱: ").append(this.contactEmail).append(", ");
        }
        
        if (this.contactAddress != null && !this.contactAddress.isEmpty()) {
            sb.append("地址: ").append(this.contactAddress);
        }
        
        return sb.toString();
    }
} 