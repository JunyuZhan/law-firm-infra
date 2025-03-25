package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.cases.core.audit.CaseAuditProvider;
import com.lawfirm.cases.core.message.CaseMessageManager;
import com.lawfirm.cases.core.workflow.CaseTaskAssigner;
import com.lawfirm.model.cases.mapper.team.CaseAssignmentMapper;
import com.lawfirm.model.cases.mapper.team.CaseParticipantMapper;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.cases.dto.team.CaseAssignmentDTO;
import com.lawfirm.model.cases.dto.team.CaseParticipantDTO;
import com.lawfirm.model.cases.entity.team.CaseAssignment;
import com.lawfirm.model.cases.entity.team.CaseParticipant;
import com.lawfirm.model.cases.entity.team.CaseTeamMember;
import com.lawfirm.model.cases.service.team.CaseAssignmentService;
import com.lawfirm.model.cases.service.team.CaseParticipantService;
import com.lawfirm.model.cases.service.team.CaseTeamService;
import com.lawfirm.model.cases.vo.team.CaseAssignmentVO;
import com.lawfirm.model.cases.vo.team.CaseParticipantVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 团队服务实现类
 * 整合了案件参与方和案件分配两个服务
 */
@Slf4j
@Service("caseTeamServiceImpl")
@RequiredArgsConstructor
public class TeamServiceImpl implements CaseTeamService {

    private final CaseParticipantMapper participantMapper;
    private final CaseAssignmentMapper assignmentMapper;
    private final CaseAuditProvider auditProvider;
    private final CaseMessageManager messageManager;
    private final CaseTaskAssigner taskAssigner;

    /**
     * 获取案件团队成员列表
     *
     * @param caseId 案件ID
     * @return 团队成员列表
     */
    @Override
    public List<CaseTeamMember> getCaseTeamMembers(Long caseId) {
        if (caseId == null) {
            log.warn("获取团队成员时案件ID为空");
            return Collections.emptyList();
        }
        
        log.info("获取案件团队成员, caseId={}", caseId);
        // 从数据库获取团队成员信息并转换为CaseTeamMember对象
        // 这里简化为直接返回空列表，实际实现需要查询数据库
        return Collections.emptyList();
    }

    //=========================== 案件参与方服务实现 ===========================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addParticipant(CaseParticipantDTO participantDTO) {
        log.info("添加案件参与方: 案件ID={}, 参与方类型={}", 
                participantDTO.getCaseId(), participantDTO.getParticipantType());

        // 检查是否已存在
        if (checkParticipantExists(
                participantDTO.getCaseId(), 
                participantDTO.getParticipantType().getValue(), 
                participantDTO.getParticipantName())) {
            throw new RuntimeException("该参与方已存在");
        }

        // 创建参与方实体
        CaseParticipant participant = new CaseParticipant();
        BeanUtils.copyProperties(participantDTO, participant);
        participant.setCreateTime(LocalDateTime.now());
        participant.setUpdateTime(LocalDateTime.now());

        // 保存参与方
        participantMapper.insert(participant);
        Long participantId = participant.getId();

        // 记录审计
        Map<String, Object> teamChanges = Collections.singletonMap(
                "change", Map.of(
                        "type", "ADD_PARTICIPANT",
                        "participantId", participantId,
                        "participantType", participantDTO.getParticipantType(),
                        "participantName", participantDTO.getParticipantName()
                )
        );
        // 从上下文或当前用户会话获取操作人ID
        Long operatorId = 0L; // 临时使用，实际应从上下文获取
        auditProvider.auditTeamMemberChange(
                participantDTO.getCaseId(),
                operatorId,
                "ADD",
                null,
                "参与方"
        );

        // 发送团队变更消息
        List<Map<String, Object>> changes = Collections.singletonList(
                Map.of(
                        "changeType", "ADD_PARTICIPANT",
                        "participantId", participantId,
                        "participantType", participantDTO.getParticipantType(),
                        "participantName", participantDTO.getParticipantName()
                )
        );
        messageManager.sendCaseTeamChangeMessage(
                participantDTO.getCaseId(),
                changes,
                operatorId
        );

        log.info("添加案件参与方成功, ID: {}", participantId);
        return participantId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchAddParticipants(List<CaseParticipantDTO> participantDTOs) {
        log.info("批量添加案件参与方: 数量={}", participantDTOs.size());

        for (CaseParticipantDTO dto : participantDTOs) {
            addParticipant(dto);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateParticipant(CaseParticipantDTO participantDTO) {
        log.info("更新案件参与方: ID={}", participantDTO.getId());

        // 获取原参与方数据
        CaseParticipant oldParticipant = participantMapper.selectById(participantDTO.getId());
        if (oldParticipant == null) {
            throw new RuntimeException("参与方不存在: " + participantDTO.getId());
        }

        // 更新参与方
        CaseParticipant participant = new CaseParticipant();
        BeanUtils.copyProperties(participantDTO, participant);
        participant.setUpdateTime(LocalDateTime.now());

        int result = participantMapper.updateById(participant);

        // 记录审计
        Map<String, Object> teamChanges = Collections.singletonMap(
                "change", Map.of(
                        "type", "UPDATE_PARTICIPANT",
                        "participantId", participant.getId(),
                        "oldData", oldParticipant,
                        "newData", participant
                )
        );
        // 从上下文或当前用户会话获取操作人ID
        Long operatorId = 0L; // 临时使用，实际应从上下文获取
        auditProvider.auditTeamMemberChange(
                participantDTO.getCaseId(),
                operatorId,
                "CHANGE_ROLE",
                null,
                "参与方"
        );

        log.info("更新案件参与方成功, ID: {}", participantDTO.getId());
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteParticipant(Long participantId) {
        log.info("删除案件参与方: ID={}", participantId);

        // 获取参与方信息
        CaseParticipant participant = participantMapper.selectById(participantId);
        if (participant == null) {
            throw new RuntimeException("参与方不存在: " + participantId);
        }

        // 删除参与方
        int result = participantMapper.deleteById(participantId);

        // 记录审计
        Map<String, Object> teamChanges = Collections.singletonMap(
                "change", Map.of(
                        "type", "DELETE_PARTICIPANT",
                        "participantId", participantId,
                        "participantType", participant.getParticipantType(),
                        "participantName", participant.getParticipantName()
                )
        );
        // 审计记录中应该包含操作人信息，这里暂时留空
        Long operatorId = 0L; // 实际应用中应从上下文或参数获取
        auditProvider.auditTeamMemberChange(
                participant.getCaseId(),
                operatorId,
                "REMOVE",
                null,
                "参与方"
        );

        log.info("删除案件参与方成功, ID: {}", participantId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteParticipants(List<Long> participantIds) {
        log.info("批量删除案件参与方: 数量={}", participantIds.size());

        for (Long id : participantIds) {
            deleteParticipant(id);
        }

        return true;
    }

    @Override
    public CaseParticipantVO getParticipantDetail(Long participantId) {
        log.info("获取参与方详情: ID={}", participantId);

        CaseParticipant participant = participantMapper.selectById(participantId);
        if (participant == null) {
            throw new RuntimeException("参与方不存在: " + participantId);
        }

        CaseParticipantVO vo = new CaseParticipantVO();
        BeanUtils.copyProperties(participant, vo);

        return vo;
    }

    @Override
    public List<CaseParticipantVO> listCaseParticipants(Long caseId) {
        log.info("获取案件所有参与方: 案件ID={}", caseId);

        LambdaQueryWrapper<CaseParticipant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseParticipant::getCaseId, caseId);
        List<CaseParticipant> participants = participantMapper.selectList(wrapper);

        return participants.stream().map(entity -> {
            CaseParticipantVO vo = new CaseParticipantVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public IPage<CaseParticipantVO> pageParticipants(Long caseId, Integer participantType, Integer pageNum, Integer pageSize) {
        log.info("分页查询参与方: 案件ID={}, 参与方类型={}, 页码={}, 每页大小={}", 
                caseId, participantType, pageNum, pageSize);

        Page<CaseParticipant> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CaseParticipant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseParticipant::getCaseId, caseId);
        if (participantType != null) {
            wrapper.eq(CaseParticipant::getParticipantType, participantType);
        }
        
        // 排序
        wrapper.orderByDesc(CaseParticipant::getCreateTime);

        // 执行查询
        IPage<CaseParticipant> resultPage = participantMapper.selectPage(page, wrapper);

        // 转换为VO
        return resultPage.convert(entity -> {
            CaseParticipantVO vo = new CaseParticipantVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    public boolean checkParticipantExists(Long caseId, Integer participantType, String participantName) {
        log.info("检查参与方是否存在: 案件ID={}, 参与方类型={}, 参与方名称={}", 
                caseId, participantType, participantName);

        LambdaQueryWrapper<CaseParticipant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseParticipant::getCaseId, caseId)
                .eq(CaseParticipant::getParticipantType, participantType)
                .eq(CaseParticipant::getParticipantName, participantName);
                
        return participantMapper.selectCount(wrapper) > 0;
    }

    @Override
    public int countParticipants(Long caseId, Integer participantType) {
        log.info("统计案件参与方数量: 案件ID={}, 参与方类型={}", caseId, participantType);

        LambdaQueryWrapper<CaseParticipant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseParticipant::getCaseId, caseId);
        if (participantType != null) {
            wrapper.eq(CaseParticipant::getParticipantType, participantType);
        }
                
        return participantMapper.selectCount(wrapper).intValue();
    }

    //=========================== 案件分配服务实现 ===========================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long assignCase(CaseAssignmentDTO assignmentDTO) {
        log.info("分配案件: 案件ID={}, 受理人={}", 
                assignmentDTO.getCaseId(), assignmentDTO.getAssignedLawyerId());

        // 检查律师是否可用
        if (!checkLawyerAvailable(assignmentDTO.getAssignedLawyerId(), assignmentDTO.getCaseId())) {
            throw new RuntimeException("指定律师不可用");
        }

        // 创建分配实体
        CaseAssignment assignment = new CaseAssignment();
        BeanUtils.copyProperties(assignmentDTO, assignment);
        assignment.setAssignmentStatus(1); // 初始状态：待接受
        assignment.setCreateTime(LocalDateTime.now());
        assignment.setUpdateTime(LocalDateTime.now());

        // 保存分配
        assignmentMapper.insert(assignment);
        Long assignmentId = assignment.getId();

        // 分配工作流任务 (简化处理，实际实现中应该有任务ID和其他信息)
        // taskAssigner.assignTask(任务ID, 受理人ID, 任务说明);

        // 记录审计
        auditProvider.auditTeamMemberChange(
                assignmentDTO.getCaseId(),
                assignmentDTO.getOriginalLawyerId(), // 使用原律师ID作为操作人
                "ADD",
                assignmentDTO.getAssignedLawyerId(),
                assignmentDTO.getAssignmentType().toString()  // 将Integer转为String
        );

        // 发送分配消息
        messageManager.sendCaseAssignmentMessage(
                assignmentDTO.getCaseId(),
                assignmentDTO.getAssignedLawyerId(),
                assignmentDTO.getOriginalLawyerId(),
                "NEW" // 新分配
        );

        log.info("分配案件成功, ID: {}", assignmentId);
        return assignmentId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchAssignCases(List<CaseAssignmentDTO> assignmentDTOs) {
        log.info("批量分配案件: 数量={}", assignmentDTOs.size());

        for (CaseAssignmentDTO dto : assignmentDTOs) {
            assignCase(dto);
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAssignment(CaseAssignmentDTO assignmentDTO) {
        log.info("更新分配信息: ID={}", assignmentDTO.getId());

        // 获取原分配数据
        CaseAssignment oldAssignment = assignmentMapper.selectById(assignmentDTO.getId());
        if (oldAssignment == null) {
            throw new RuntimeException("分配记录不存在: " + assignmentDTO.getId());
        }

        // 更新分配
        CaseAssignment assignment = new CaseAssignment();
        BeanUtils.copyProperties(assignmentDTO, assignment);
        assignment.setUpdateTime(LocalDateTime.now());

        int result = assignmentMapper.updateById(assignment);

        // 记录审计
        auditProvider.auditTeamMemberChange(
                assignmentDTO.getCaseId(),
                assignmentDTO.getOriginalLawyerId(),
                "CHANGE_ROLE",
                assignmentDTO.getAssignedLawyerId(),
                assignmentDTO.getAssignmentType().toString()
        );

        log.info("更新分配信息成功, ID: {}", assignmentDTO.getId());
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelAssignment(Long assignmentId) {
        log.info("取消分配: ID={}", assignmentId);

        // 获取分配信息
        CaseAssignment assignment = assignmentMapper.selectById(assignmentId);
        if (assignment == null) {
            throw new RuntimeException("分配记录不存在: " + assignmentId);
        }

        // 更新状态为已取消
        assignment.setAssignmentStatus(9); // 已取消
        assignment.setUpdateTime(LocalDateTime.now());
        int result = assignmentMapper.updateById(assignment);

        // 记录审计
        auditProvider.auditTeamMemberChange(
                assignment.getCaseId(),
                assignment.getOriginalLawyerId(),
                "REMOVE",
                assignment.getAssignedLawyerId(),
                assignment.getAssignmentType().toString()
        );

        // 发送取消消息
        messageManager.sendCaseAssignmentMessage(
                assignment.getCaseId(),
                assignment.getAssignedLawyerId(),
                assignment.getOriginalLawyerId(),
                "CANCEL" // 取消分配
        );

        log.info("取消分配成功, ID: {}", assignmentId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchCancelAssignments(List<Long> assignmentIds) {
        log.info("批量取消分配: 数量={}", assignmentIds.size());

        for (Long id : assignmentIds) {
            cancelAssignment(id);
        }

        return true;
    }

    @Override
    public CaseAssignmentVO getAssignmentDetail(Long assignmentId) {
        log.info("获取分配详情: ID={}", assignmentId);

        CaseAssignment assignment = assignmentMapper.selectById(assignmentId);
        if (assignment == null) {
            throw new RuntimeException("分配记录不存在: " + assignmentId);
        }

        CaseAssignmentVO vo = new CaseAssignmentVO();
        BeanUtils.copyProperties(assignment, vo);

        return vo;
    }

    @Override
    public List<CaseAssignmentVO> listCaseAssignments(Long caseId) {
        log.info("获取案件的所有分配记录: 案件ID={}", caseId);

        LambdaQueryWrapper<CaseAssignment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseAssignment::getCaseId, caseId);
        List<CaseAssignment> assignments = assignmentMapper.selectList(wrapper);

        return assignments.stream().map(entity -> {
            CaseAssignmentVO vo = new CaseAssignmentVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public IPage<CaseAssignmentVO> pageAssignments(Long caseId, Integer assignmentType, Integer pageNum, Integer pageSize) {
        log.info("分页查询分配记录: 案件ID={}, 分配类型={}, 页码={}, 每页大小={}", 
                caseId, assignmentType, pageNum, pageSize);

        Page<CaseAssignment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CaseAssignment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseAssignment::getCaseId, caseId);
        if (assignmentType != null) {
            wrapper.eq(CaseAssignment::getAssignmentType, assignmentType);
        }
        
        // 排序
        wrapper.orderByDesc(CaseAssignment::getCreateTime);

        // 执行查询
        IPage<CaseAssignment> resultPage = assignmentMapper.selectPage(page, wrapper);

        // 转换为VO
        return resultPage.convert(entity -> {
            CaseAssignmentVO vo = new CaseAssignmentVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean acceptAssignment(Long assignmentId) {
        log.info("接受分配: ID={}", assignmentId);

        // 获取分配信息
        CaseAssignment assignment = assignmentMapper.selectById(assignmentId);
        if (assignment == null) {
            throw new RuntimeException("分配记录不存在: " + assignmentId);
        }

        // 更新状态为已接受
        assignment.setAssignmentStatus(2); // 已接受
        // 设置接受时间 (具体的字段名要看实际实体类定义)
        assignment.setActualHandoverTime(LocalDateTime.now());
        assignment.setUpdateTime(LocalDateTime.now());
        int result = assignmentMapper.updateById(assignment);

        // 认领工作流任务 (简化处理，实际实现中应该有任务ID)
        // taskAssigner.claimTask(任务ID, 受理人ID);

        log.info("接受分配成功, ID: {}", assignmentId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rejectAssignment(Long assignmentId, String reason) {
        log.info("拒绝分配: ID={}, 原因={}", assignmentId, reason);

        // 获取分配信息
        CaseAssignment assignment = assignmentMapper.selectById(assignmentId);
        if (assignment == null) {
            throw new RuntimeException("分配记录不存在: " + assignmentId);
        }

        // 更新状态为已拒绝
        assignment.setAssignmentStatus(3); // 已拒绝
        // 设置拒绝原因和时间 (具体的字段名要看实际实体类定义)
        assignment.setApprovalOpinion(reason);
        assignment.setApprovalTime(LocalDateTime.now());
        assignment.setUpdateTime(LocalDateTime.now());
        int result = assignmentMapper.updateById(assignment);

        // 归还工作流任务 (简化处理，实际实现中应该有任务ID)
        // taskAssigner.returnTask(任务ID, 受理人ID, 原因);

        // 发送拒绝消息
        messageManager.sendCaseAssignmentMessage(
                assignment.getCaseId(),
                assignment.getAssignedLawyerId(),
                assignment.getOriginalLawyerId(),
                "REJECT" // 拒绝分配
        );

        log.info("拒绝分配成功, ID: {}", assignmentId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean completeAssignment(Long assignmentId) {
        log.info("完成分配: ID={}", assignmentId);

        // 获取分配信息
        CaseAssignment assignment = assignmentMapper.selectById(assignmentId);
        if (assignment == null) {
            throw new RuntimeException("分配记录不存在: " + assignmentId);
        }

        // 更新状态为已完成
        assignment.setAssignmentStatus(4); // 已完成
        // 设置完成时间 (具体的字段名要看实际实体类定义)
        assignment.setActualHandoverTime(LocalDateTime.now());
        assignment.setUpdateTime(LocalDateTime.now());
        int result = assignmentMapper.updateById(assignment);

        log.info("完成分配成功, ID: {}", assignmentId);
        return result > 0;
    }

    @Override
    public boolean checkLawyerAvailable(Long lawyerId, Long caseId) {
        log.info("检查律师是否可用: 律师ID={}, 案件ID={}", lawyerId, caseId);
        
        // 在实际应用中，需要检查律师的工作负荷、利益冲突等
        // 此处仅为示例，返回可用
        
        return true;
    }

    @Override
    public int countAssignments(Long caseId, Integer assignmentType) {
        log.info("统计分配记录数量: 案件ID={}, 分配类型={}", caseId, assignmentType);

        LambdaQueryWrapper<CaseAssignment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseAssignment::getCaseId, caseId);
        if (assignmentType != null) {
            wrapper.eq(CaseAssignment::getAssignmentType, assignmentType.toString());
        }
                
        return assignmentMapper.selectCount(wrapper).intValue();
    }
}
