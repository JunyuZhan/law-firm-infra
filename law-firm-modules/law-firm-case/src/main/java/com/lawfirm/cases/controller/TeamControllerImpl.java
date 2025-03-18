package com.lawfirm.cases.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.team.CaseTeamDTO;
import com.lawfirm.model.cases.dto.team.CaseParticipantDTO;
import com.lawfirm.model.cases.entity.team.CaseTeamMember;
import com.lawfirm.model.cases.service.team.CaseTeamService;
import com.lawfirm.model.cases.vo.team.CaseParticipantVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 案件团队管理控制器实现
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/cases/teams")
@RequiredArgsConstructor
@Tag(name = "案件团队管理", description = "案件团队管理相关接口")
public class TeamControllerImpl {

    private final CaseTeamService caseTeamService;

    @PostMapping("/participants")
    @Operation(summary = "添加参与方")
    public Long addParticipant(@RequestBody @Validated CaseParticipantDTO participantDTO) {
        log.info("添加参与方: {}", participantDTO);
        return caseTeamService.addParticipant(participantDTO);
    }

    @PostMapping("/participants/batch")
    @Operation(summary = "批量添加参与方")
    public boolean batchAddParticipants(@RequestBody @Validated List<CaseParticipantDTO> participantDTOs) {
        log.info("批量添加参与方: {}", participantDTOs);
        return caseTeamService.batchAddParticipants(participantDTOs);
    }

    @PutMapping("/participants")
    @Operation(summary = "更新参与方信息")
    public boolean updateParticipant(@RequestBody @Validated CaseParticipantDTO participantDTO) {
        log.info("更新参与方信息: {}", participantDTO);
        return caseTeamService.updateParticipant(participantDTO);
    }

    @DeleteMapping("/participants/{participantId}")
    @Operation(summary = "删除参与方")
    public boolean deleteParticipant(@PathVariable("participantId") Long participantId) {
        log.info("删除参与方: {}", participantId);
        return caseTeamService.deleteParticipant(participantId);
    }

    @DeleteMapping("/participants/batch")
    @Operation(summary = "批量删除参与方")
    public boolean batchDeleteParticipants(@RequestBody List<Long> participantIds) {
        log.info("批量删除参与方: {}", participantIds);
        return caseTeamService.batchDeleteParticipants(participantIds);
    }

    @GetMapping("/participants/{participantId}")
    @Operation(summary = "获取参与方详情")
    public CaseParticipantVO getParticipantDetail(@PathVariable("participantId") Long participantId) {
        log.info("获取参与方详情: {}", participantId);
        return caseTeamService.getParticipantDetail(participantId);
    }

    @GetMapping("/cases/{caseId}/participants")
    @Operation(summary = "获取案件的所有参与方")
    public List<CaseParticipantVO> listCaseParticipants(@PathVariable("caseId") Long caseId) {
        log.info("获取案件的所有参与方: caseId={}", caseId);
        return caseTeamService.listCaseParticipants(caseId);
    }

    @GetMapping("/cases/{caseId}/participants/page")
    @Operation(summary = "分页查询参与方")
    public IPage<CaseParticipantVO> pageParticipants(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "参与方类型") @RequestParam(required = false) Integer participantType,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询参与方: caseId={}, participantType={}, pageNum={}, pageSize={}", 
                caseId, participantType, pageNum, pageSize);
        return caseTeamService.pageParticipants(caseId, participantType, pageNum, pageSize);
    }

    @GetMapping("/cases/{caseId}/participants/exists")
    @Operation(summary = "检查参与方是否存在")
    public boolean checkParticipantExists(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "参与方类型") @RequestParam Integer participantType,
            @Parameter(description = "参与方名称") @RequestParam String participantName) {
        log.info("检查参与方是否存在: caseId={}, participantType={}, participantName={}", 
                caseId, participantType, participantName);
        return caseTeamService.checkParticipantExists(caseId, participantType, participantName);
    }

    @GetMapping("/cases/{caseId}/participants/count")
    @Operation(summary = "统计案件参与方数量")
    public int countParticipants(
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "参与方类型") @RequestParam(required = false) Integer participantType) {
        log.info("统计案件参与方数量: caseId={}, participantType={}", caseId, participantType);
        return caseTeamService.countParticipants(caseId, participantType);
    }

    @GetMapping("/cases/{caseId}/members")
    @Operation(summary = "获取案件团队成员列表")
    public List<CaseTeamMember> getCaseTeamMembers(@PathVariable("caseId") Long caseId) {
        log.info("获取案件团队成员列表: caseId={}", caseId);
        return caseTeamService.getCaseTeamMembers(caseId);
    }
} 