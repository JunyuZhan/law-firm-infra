package com.lawfirm.model.cases.vo.team;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.team.ParticipantTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CaseParticipantVO extends BaseVO {

    private Long caseId;
    
    private String participantId;
    
    private String participantName;
    
    private ParticipantTypeEnum participantType;
    
    private String contactInfo;
    
    private String address;
    
    private String idNumber;
    
    private String position;
    
    private String organization;
}