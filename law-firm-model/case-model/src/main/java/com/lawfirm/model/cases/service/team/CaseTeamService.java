package com.lawfirm.model.cases.service.team;

import com.lawfirm.model.cases.entity.team.CaseTeamMember;

import java.util.List;

/**
 * 案例团队服务
 * 整合案件团队相关功能
 */
public interface CaseTeamService extends CaseParticipantService, CaseAssignmentService {
    
    /**
     * 获取案件团队成员列表
     *
     * @param caseId 案件ID
     * @return 团队成员列表
     */
    List<CaseTeamMember> getCaseTeamMembers(Long caseId);
} 