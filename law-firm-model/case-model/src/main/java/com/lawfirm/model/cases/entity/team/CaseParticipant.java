package com.lawfirm.model.cases.entity.team;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import com.lawfirm.model.cases.enums.team.CaseParticipantTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 案件参与方实体类
 * 
 * 案件参与方记录了与案件相关的各类参与者信息，包括委托人、对方当事人、
 * 证人、法官等，记录其基本信息、身份信息、联系方式等。
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("case_participant")
public class CaseParticipant extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 案件ID
     */
    @TableField("case_id")
    private Long caseId;

    /**
     * 案件编号
     */
    @TableField("case_number")
    private String caseNumber;

    /**
     * 参与方类型
     */
    @TableField("participant_type")
    private Integer participantType;

    /**
     * 参与方名称（个人姓名或组织名称）
     */
    @TableField("participant_name")
    private String participantName;

    /**
     * 参与方角色说明
     */
    @TableField("role_description")
    private String roleDescription;

    /**
     * 联系人
     */
    @TableField("contact_person")
    private String contactPerson;

    /**
     * 联系电话
     */
    @TableField("contact_phone")
    private String contactPhone;

    /**
     * 联系邮箱
     */
    @TableField("contact_email")
    private String contactEmail;

    /**
     * 联系地址
     */
    @TableField("contact_address")
    private String contactAddress;

    /**
     * 证件类型（1-身份证，2-护照，3-营业执照，4-其他）
     */
    @TableField("id_type")
    private Integer idType;

    /**
     * 证件号码
     */
    @TableField("id_number")
    private String idNumber;

    /**
     * 是否为组织/公司
     */
    @TableField("is_organization")
    private Boolean isOrganization;

    /**
     * 组织类型（企业、政府机构、社会组织等）
     */
    @TableField("organization_type")
    private String organizationType;

    /**
     * 法定代表人
     */
    @TableField("legal_representative")
    private String legalRepresentative;

    /**
     * 统一社会信用代码
     */
    @TableField("social_credit_code")
    private String socialCreditCode;

    /**
     * 是否为主要参与方
     */
    @TableField("is_primary")
    private Boolean isPrimary;

    /**
     * 是否需要通知
     */
    @TableField("need_notification")
    private Boolean needNotification;

    /**
     * 备注
     */
    @TableField("remarks")
    private String remarks;

    /**
     * 获取参与方类型枚举
     */
    public CaseParticipantTypeEnum getParticipantTypeEnum() {
        return CaseParticipantTypeEnum.valueOf(this.participantType);
    }

    /**
     * 设置参与方类型
     */
    public CaseParticipant setParticipantTypeEnum(CaseParticipantTypeEnum participantTypeEnum) {
        this.participantType = participantTypeEnum != null ? participantTypeEnum.getValue() : null;
        return this;
    }

    /**
     * 判断是否为委托人
     */
    public boolean isClient() {
        return this.participantType != null && 
               this.getParticipantTypeEnum() == CaseParticipantTypeEnum.CLIENT;
    }

    /**
     * 判断是否为对方当事人
     */
    public boolean isOpposingParty() {
        return this.participantType != null && 
               this.getParticipantTypeEnum() == CaseParticipantTypeEnum.OPPOSING_PARTY;
    }

    /**
     * 判断是否为证人
     */
    public boolean isWitness() {
        return this.participantType != null && 
               this.getParticipantTypeEnum() == CaseParticipantTypeEnum.WITNESS;
    }

    /**
     * 判断是否为法官
     */
    public boolean isJudge() {
        return this.participantType != null && 
               this.getParticipantTypeEnum() == CaseParticipantTypeEnum.JUDGE;
    }

    /**
     * 判断是否为鉴定人
     */
    public boolean isExpert() {
        return this.participantType != null && 
               this.getParticipantTypeEnum() == CaseParticipantTypeEnum.EXPERT;
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
} 