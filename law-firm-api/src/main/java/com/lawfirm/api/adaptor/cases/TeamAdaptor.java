package com.lawfirm.api.adaptor.cases;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.cases.dto.team.CaseParticipantDTO;
import com.lawfirm.model.cases.entity.team.CaseTeamMember;
import com.lawfirm.model.cases.service.team.CaseTeamService;
import com.lawfirm.model.cases.vo.team.CaseParticipantVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 案件团队适配器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TeamAdaptor extends BaseAdaptor {

    private final CaseTeamService caseTeamService;

    /**
     * 添加参与方
     */
    public Long addParticipant(CaseParticipantDTO participantDTO) {
        log.info("添加参与方: {}", participantDTO);
        return caseTeamService.addParticipant(participantDTO);
    }

    /**
     * 批量添加参与方
     */
    public boolean batchAddParticipants(List<CaseParticipantDTO> participantDTOs) {
        log.info("批量添加参与方: {}", participantDTOs);
        return caseTeamService.batchAddParticipants(participantDTOs);
    }

    /**
     * 更新参与方信息
     */
    public boolean updateParticipant(CaseParticipantDTO participantDTO) {
        log.info("更新参与方信息: {}", participantDTO);
        return caseTeamService.updateParticipant(participantDTO);
    }

    /**
     * 删除参与方
     */
    public boolean deleteParticipant(Long participantId) {
        log.info("删除参与方: {}", participantId);
        return caseTeamService.deleteParticipant(participantId);
    }

    /**
     * 批量删除参与方
     */
    public boolean batchDeleteParticipants(List<Long> participantIds) {
        log.info("批量删除参与方: {}", participantIds);
        return caseTeamService.batchDeleteParticipants(participantIds);
    }

    /**
     * 获取参与方详情
     */
    public CaseParticipantVO getParticipantDetail(Long participantId) {
        log.info("获取参与方详情: {}", participantId);
        return caseTeamService.getParticipantDetail(participantId);
    }

    /**
     * 获取案件的所有参与方
     */
    public List<CaseParticipantVO> listCaseParticipants(Long caseId) {
        log.info("获取案件的所有参与方: caseId={}", caseId);
        return caseTeamService.listCaseParticipants(caseId);
    }

    /**
     * 分页查询参与方
     */
    public IPage<CaseParticipantVO> pageParticipants(Long caseId, Integer participantType, Integer pageNum, Integer pageSize) {
        log.info("分页查询参与方: caseId={}, participantType={}, pageNum={}, pageSize={}", 
                caseId, participantType, pageNum, pageSize);
        return caseTeamService.pageParticipants(caseId, participantType, pageNum, pageSize);
    }

    /**
     * 检查参与方是否存在
     */
    public boolean checkParticipantExists(Long caseId, Integer participantType, String participantName) {
        log.info("检查参与方是否存在: caseId={}, participantType={}, participantName={}", 
                caseId, participantType, participantName);
        return caseTeamService.checkParticipantExists(caseId, participantType, participantName);
    }

    /**
     * 统计案件参与方数量
     */
    public int countParticipants(Long caseId, Integer participantType) {
        log.info("统计案件参与方数量: caseId={}, participantType={}", caseId, participantType);
        return caseTeamService.countParticipants(caseId, participantType);
    }

    /**
     * 获取案件团队成员列表
     */
    public List<CaseTeamMember> getCaseTeamMembers(Long caseId) {
        log.info("获取案件团队成员列表: caseId={}", caseId);
        return caseTeamService.getCaseTeamMembers(caseId);
    }
} 