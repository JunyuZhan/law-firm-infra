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
import com.lawfirm.cases.constant.CaseBusinessConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;

import java.util.List;

/**
 * 案件团队控制器
 */
@Slf4j
@RestController("teamController")
@RequestMapping(CaseBusinessConstants.Controller.API_TEAM_PREFIX)
@RequiredArgsConstructor
@Tag(name = "案件团队管理", description = "提供案件团队管理功能，包括团队成员的添加、移除、角色分配等操作")
public class TeamController {

    private final CaseTeamService caseTeamService;

    @Operation(
        summary = "添加参与方",
        description = "为案件添加新的参与方。注意事项：\n" +
                "1. 支持的参与方类型：原告、被告、第三人、其他\n" +
                "2. 需要提供完整的参与方信息，包括：\n" +
                "   - 基本信息：名称、类型、证件信息等\n" +
                "   - 联系方式：电话、地址、邮箱等\n" +
                "   - 其他信息：与案件的关系说明等\n" +
                "3. 同一案件中不能存在重复的参与方（相同类型和名称）"
    )
    @PostMapping("/participants")
    @PreAuthorize(CASE_EDIT)
    public Long addParticipant(
            @Parameter(description = "参与方信息，包括：\n" +
                    "1. 基本信息：参与方类型、名称、证件类型、证件号码等\n" +
                    "2. 联系信息：联系人、电话、地址、邮箱等\n" +
                    "3. 案件信息：案件ID、参与身份说明等") 
            @RequestBody @Validated CaseParticipantDTO participantDTO) {
        log.info("添加参与方: {}", participantDTO);
        return caseTeamService.addParticipant(participantDTO);
    }

    @Operation(
        summary = "批量添加参与方",
        description = "批量添加多个参与方，适用于：\n" +
                "1. 批量导入参与方数据\n" +
                "2. 关联案件需要同时添加多个参与方\n" +
                "3. 集体诉讼案件的参与方批量导入\n" +
                "注意：任一参与方信息有误都将导致整批次添加失败"
    )
    @PostMapping("/participants/batch")
    @PreAuthorize(CASE_EDIT)
    public boolean batchAddParticipants(
            @Parameter(description = "参与方信息列表，每个参与方信息需包含完整的基本信息和联系方式") 
            @RequestBody @Validated List<CaseParticipantDTO> participantDTOs) {
        log.info("批量添加参与方: {}", participantDTOs);
        return caseTeamService.batchAddParticipants(participantDTOs);
    }

    @Operation(
        summary = "更新参与方信息",
        description = "更新已有参与方的信息。注意事项：\n" +
                "1. 可更新的信息包括：\n" +
                "   - 基本信息：名称、证件信息等\n" +
                "   - 联系方式：电话、地址、邮箱等\n" +
                "   - 其他信息：与案件的关系说明等\n" +
                "2. 参与方类型一旦确定不可更改\n" +
                "3. 更新不能与已有参与方产生重复"
    )
    @PutMapping("/participants")
    @PreAuthorize(CASE_EDIT)
    public boolean updateParticipant(
            @Parameter(description = "更新的参与方信息，需要包含参与方ID和待更新的字段信息") 
            @RequestBody @Validated CaseParticipantDTO participantDTO) {
        log.info("更新参与方信息: {}", participantDTO);
        return caseTeamService.updateParticipant(participantDTO);
    }

    @Operation(
        summary = "删除参与方",
        description = "删除指定的参与方。删除限制：\n" +
                "1. 已关联案件文书的参与方不能删除\n" +
                "2. 案件审理过程中的必要参与方不能删除\n" +
                "3. 删除后相关的历史记录将被保留"
    )
    @DeleteMapping("/participants/{participantId}")
    @PreAuthorize(CASE_EDIT)
    public boolean deleteParticipant(
            @Parameter(description = "参与方ID") 
            @PathVariable("participantId") Long participantId) {
        log.info("删除参与方: {}", participantId);
        return caseTeamService.deleteParticipant(participantId);
    }

    @Operation(
        summary = "批量删除参与方",
        description = "批量删除多个参与方。注意事项：\n" +
                "1. 任一参与方不满足删除条件将导致整批次删除失败\n" +
                "2. 建议先检查每个参与方是否可以删除\n" +
                "3. 批量删除前应确认不会影响案件正常推进"
    )
    @DeleteMapping("/participants/batch")
    @PreAuthorize(CASE_EDIT)
    public boolean batchDeleteParticipants(
            @Parameter(description = "待删除的参与方ID列表") 
            @RequestBody List<Long> participantIds) {
        log.info("批量删除参与方: {}", participantIds);
        return caseTeamService.batchDeleteParticipants(participantIds);
    }

    @Operation(
        summary = "获取参与方详情",
        description = "获取指定参与方的详细信息，包括：\n" +
                "1. 基本信息：参与方类型、名称、证件信息等\n" +
                "2. 联系方式：联系人、电话、地址、邮箱等\n" +
                "3. 案件信息：参与的案件列表、身份说明等\n" +
                "4. 相关记录：文书送达记录、联系记录等"
    )
    @GetMapping("/participants/{participantId}")
    @PreAuthorize(CASE_VIEW)
    public CaseParticipantVO getParticipantDetail(
            @Parameter(description = "参与方ID") 
            @PathVariable("participantId") Long participantId) {
        log.info("获取参与方详情: {}", participantId);
        return caseTeamService.getParticipantDetail(participantId);
    }

    @Operation(
        summary = "获取案件的所有参与方",
        description = "获取指定案件的所有参与方列表。返回信息包括：\n" +
                "1. 各类参与方的基本信息和联系方式\n" +
                "2. 参与方在案件中的身份和作用\n" +
                "3. 参与方的重要联系记录\n" +
                "4. 参与方关联的文书信息"
    )
    @GetMapping("/cases/{caseId}/participants")
    @PreAuthorize(CASE_VIEW)
    public List<CaseParticipantVO> listCaseParticipants(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId) {
        log.info("获取案件的所有参与方: caseId={}", caseId);
        return caseTeamService.listCaseParticipants(caseId);
    }

    @Operation(
        summary = "分页查询参与方",
        description = "分页查询案件的参与方列表。查询特点：\n" +
                "1. 支持按参与方类型筛选\n" +
                "2. 结果按添加时间倒序排列\n" +
                "3. 支持自定义每页显示数量\n" +
                "4. 包含参与方的主要信息和状态"
    )
    @GetMapping("/cases/{caseId}/participants/page")
    @PreAuthorize(CASE_VIEW)
    public IPage<CaseParticipantVO> pageParticipants(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "参与方类型：1-原告，2-被告，3-第三人，4-其他") 
            @RequestParam(required = false) Integer participantType,
            @Parameter(description = "页码，从1开始") 
            @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页显示记录数，默认10条") 
            @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询参与方: caseId={}, participantType={}, pageNum={}, pageSize={}", 
                caseId, participantType, pageNum, pageSize);
        return caseTeamService.pageParticipants(caseId, participantType, pageNum, pageSize);
    }

    @Operation(
        summary = "检查参与方是否存在",
        description = "检查指定案件中是否已存在特定参与方。检查规则：\n" +
                "1. 按参与方类型和名称精确匹配\n" +
                "2. 不区分参与方证件信息\n" +
                "3. 用于添加参与方前的查重\n" +
                "4. 返回true表示已存在，false表示不存在"
    )
    @GetMapping("/cases/{caseId}/participants/exists")
    @PreAuthorize(CASE_VIEW)
    public boolean checkParticipantExists(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "参与方类型：1-原告，2-被告，3-第三人，4-其他") 
            @RequestParam Integer participantType,
            @Parameter(description = "参与方名称，精确匹配") 
            @RequestParam String participantName) {
        log.info("检查参与方是否存在: caseId={}, participantType={}, participantName={}", 
                caseId, participantType, participantName);
        return caseTeamService.checkParticipantExists(caseId, participantType, participantName);
    }

    @Operation(
        summary = "统计案件参与方数量",
        description = "统计指定案件的参与方数量。统计特点：\n" +
                "1. 可按参与方类型分别统计\n" +
                "2. 不指定类型则统计所有参与方\n" +
                "3. 包含已删除的参与方\n" +
                "4. 用于案件信息展示和统计分析"
    )
    @GetMapping("/cases/{caseId}/participants/count")
    @PreAuthorize(CASE_VIEW)
    public int countParticipants(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId,
            @Parameter(description = "参与方类型：1-原告，2-被告，3-第三人，4-其他，不传则统计所有类型") 
            @RequestParam(required = false) Integer participantType) {
        log.info("统计案件参与方数量: caseId={}, participantType={}", caseId, participantType);
        return caseTeamService.countParticipants(caseId, participantType);
    }

    @Operation(
        summary = "获取案件团队成员列表",
        description = "获取案件的所有团队成员信息。包含信息：\n" +
                "1. 团队成员基本信息：姓名、职位等\n" +
                "2. 在案件中的角色：主办律师、协办律师等\n" +
                "3. 加入团队的时间和状态\n" +
                "4. 负责的具体工作内容"
    )
    @GetMapping("/cases/{caseId}/members")
    @PreAuthorize(CASE_VIEW)
    public List<CaseTeamMember> getCaseTeamMembers(
            @Parameter(description = "案件ID") 
            @PathVariable("caseId") Long caseId) {
        log.info("获取案件团队成员列表: caseId={}", caseId);
        return caseTeamService.getCaseTeamMembers(caseId);
    }
} 