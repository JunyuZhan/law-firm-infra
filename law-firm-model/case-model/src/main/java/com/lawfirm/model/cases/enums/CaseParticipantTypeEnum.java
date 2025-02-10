package com.lawfirm.model.cases.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件参与人类型枚举
 */
@Getter
public enum CaseParticipantTypeEnum implements BaseEnum<String> {
    
    // 当事人
    PLAINTIFF("原告", ParticipantGroup.PARTIES, true),
    DEFENDANT("被告", ParticipantGroup.PARTIES, true),
    THIRD_PARTY("第三人", ParticipantGroup.PARTIES, true),
    APPELLANT("上诉人", ParticipantGroup.PARTIES, true),
    APPELLEE("被上诉人", ParticipantGroup.PARTIES, true),
    APPLICANT("申请人", ParticipantGroup.PARTIES, true),
    RESPONDENT("被申请人", ParticipantGroup.PARTIES, true),
    INTERESTED_PARTY("利害关系人", ParticipantGroup.PARTIES, true),
    
    // 代理人
    LAWYER("律师", ParticipantGroup.AGENTS, true),
    AUTHORIZED_REPRESENTATIVE("委托代理人", ParticipantGroup.AGENTS, true),
    LEGAL_REPRESENTATIVE("法定代理人", ParticipantGroup.AGENTS, true),
    
    // 司法人员
    JUDGE("法官", ParticipantGroup.JUDICIAL_PERSONNEL, false),
    PROSECUTOR("检察官", ParticipantGroup.JUDICIAL_PERSONNEL, false),
    COURT_CLERK("书记员", ParticipantGroup.JUDICIAL_PERSONNEL, false),
    ARBITRATOR("仲裁员", ParticipantGroup.JUDICIAL_PERSONNEL, false),
    MEDIATOR("调解员", ParticipantGroup.JUDICIAL_PERSONNEL, false),
    
    // 证人
    WITNESS("证人", ParticipantGroup.WITNESSES, true),
    EXPERT_WITNESS("鉴定人", ParticipantGroup.WITNESSES, true),
    TRANSLATOR("翻译人员", ParticipantGroup.WITNESSES, true),
    
    // 其他参与人
    OBSERVER("旁听人员", ParticipantGroup.OTHER, false),
    OTHER("其他", ParticipantGroup.OTHER, false);

    private final String description;
    private final ParticipantGroup participantGroup;
    private final boolean needsIdentification;  // 是否需要身份认证

    CaseParticipantTypeEnum(String description, ParticipantGroup participantGroup, boolean needsIdentification) {
        this.description = description;
        this.participantGroup = participantGroup;
        this.needsIdentification = needsIdentification;
    }

    @Override
    public String getValue() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * 获取参与人分组
     * @return 参与人分组
     */
    public ParticipantGroup getParticipantGroup() {
        return this.participantGroup;
    }

    /**
     * 判断是否需要身份认证
     * @return 是否需要身份认证
     */
    public boolean needsIdentification() {
        return this.needsIdentification;
    }

    /**
     * 判断是否为当事人
     * @return 是否当事人
     */
    public boolean isParty() {
        return this.participantGroup == ParticipantGroup.PARTIES;
    }

    /**
     * 判断是否为代理人
     * @return 是否代理人
     */
    public boolean isAgent() {
        return this.participantGroup == ParticipantGroup.AGENTS;
    }

    /**
     * 判断是否为司法人员
     * @return 是否司法人员
     */
    public boolean isJudicialPersonnel() {
        return this.participantGroup == ParticipantGroup.JUDICIAL_PERSONNEL;
    }

    /**
     * 判断是否为证人
     * @return 是否证人
     */
    public boolean isWitness() {
        return this.participantGroup == ParticipantGroup.WITNESSES;
    }

    /**
     * 获取参与人显示文本
     * @return 完整的参与人显示文本
     */
    public String getParticipantDisplay() {
        StringBuilder display = new StringBuilder(this.description);
        if (this.needsIdentification) {
            display.append("[需要身份认证]");
        }
        return display.toString();
    }

    /**
     * 参与人分组
     */
    @Getter
    public enum ParticipantGroup {
        PARTIES("当事人"),
        AGENTS("代理人"),
        JUDICIAL_PERSONNEL("司法人员"),
        WITNESSES("证人"),
        OTHER("其他");

        private final String description;

        ParticipantGroup(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }
} 