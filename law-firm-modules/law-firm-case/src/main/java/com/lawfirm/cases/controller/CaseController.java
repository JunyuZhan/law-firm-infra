package com.lawfirm.cases.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.cases.constant.CaseBusinessConstants;
import com.lawfirm.model.cases.dto.base.CaseCreateDTO;
import com.lawfirm.model.cases.dto.base.CaseQueryDTO;
import com.lawfirm.model.cases.dto.base.CaseUpdateDTO;
import com.lawfirm.model.cases.entity.base.Case;
import com.lawfirm.model.cases.service.base.CaseService;
import com.lawfirm.model.cases.vo.base.CaseDetailVO;
import com.lawfirm.model.cases.vo.base.CaseQueryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 案件基本控制器
 */
@Slf4j
@Tag(name = "案件管理", description = "案件管理接口")
@RestController("caseController")
@RequestMapping(CaseBusinessConstants.Controller.API_PREFIX)
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    @Operation(
        summary = "创建案件",
        description = "创建新的案件记录，包括案件基本信息（案件名称、案由、受理法院等）、当事人信息（原告、被告等）、代理律师、收费信息等"
    )
    @PostMapping
    public Long createCase(
            @Parameter(description = "案件创建参数，包括：\n" +
                    "1. 案件基本信息：案件名称、案由、受理法院、案件类型等\n" +
                    "2. 当事人信息：原告、被告的基本信息和联系方式\n" +
                    "3. 代理信息：代理律师、协办律师等\n" +
                    "4. 收费信息：收费方式、预计收费等") 
            @RequestBody @Validated CaseCreateDTO createDTO) {
        log.info("创建案件: {}", createDTO);
        return caseService.createCase(createDTO);
    }

    @Operation(
        summary = "更新案件",
        description = "更新已有案件的基本信息，包括案件进展情况、当事人信息变更、代理律师调整等，注意已归档的案件不能更新"
    )
    @PutMapping("/{id}")
    public boolean updateCase(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id, 
            @Parameter(description = "案件更新参数，包括：\n" +
                    "1. 案件基本信息：案件进展、最新状态等\n" +
                    "2. 当事人信息：联系方式变更等\n" +
                    "3. 代理信息：代理律师变更等") 
            @RequestBody @Validated CaseUpdateDTO updateDTO) {
        log.info("更新案件: id={}, {}", id, updateDTO);
        updateDTO.setId(id);
        return caseService.updateCase(updateDTO);
    }

    @Operation(
        summary = "删除案件",
        description = "删除指定的案件记录。注意：\n" +
                "1. 已关联文档、费用等数据的案件不能删除\n" +
                "2. 已开始处理的案件不能删除\n" +
                "3. 已归档的案件不能删除"
    )
    @DeleteMapping("/{id}")
    public boolean deleteCase(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id) {
        log.info("删除案件: {}", id);
        return caseService.deleteCase(id);
    }

    @Operation(
        summary = "获取案件详情",
        description = "获取案件的详细信息，包括：\n" +
                "1. 案件基本信息：案件名称、案由、受理法院等\n" +
                "2. 当事人信息：原告、被告信息\n" +
                "3. 代理信息：代理律师、协办律师\n" +
                "4. 案件进度：当前状态、重要节点\n" +
                "5. 相关统计：文档数量、费用总额等"
    )
    @GetMapping("/{id}")
    public CaseDetailVO getCaseDetail(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id) {
        log.info("获取案件详情: {}", id);
        return caseService.getCaseDetail(id);
    }

    @Operation(
        summary = "分页查询案件",
        description = "根据条件分页查询案件列表，支持多个维度的筛选条件：\n" +
                "1. 案件信息：案件名称、案由、案件类型等\n" +
                "2. 当事人信息：当事人名称等\n" +
                "3. 代理信息：代理律师等\n" +
                "4. 案件状态：在办、归档等\n" +
                "5. 时间范围：立案时间、归档时间等"
    )
    @GetMapping
    public IPage<CaseQueryVO> pageCases(
            @Parameter(description = "查询参数，包括：\n" +
                    "1. 分页参数：页码、每页大小\n" +
                    "2. 排序参数：排序字段、排序方式\n" +
                    "3. 筛选条件：案件类型、状态、时间范围等") 
            @Validated CaseQueryDTO queryDTO) {
        log.info("分页查询案件: {}", queryDTO);
        return caseService.pageCases(queryDTO);
    }

    @Operation(
        summary = "变更案件状态",
        description = "变更案件的状态，需要提供充分的变更原因。各状态说明：\n" +
                "1. 新建：案件刚创建，未开始处理\n" +
                "2. 进行中：案件正在积极处理中\n" +
                "3. 暂停：因特殊原因暂时停止处理\n" +
                "4. 终止：案件非正常结束\n" +
                "5. 完成：案件已正常办结"
    )
    @PutMapping("/{id}/status")
    public boolean changeStatus(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id,
            @Parameter(description = "目标状态：1-新建，2-进行中，3-暂停，4-终止，5-完成") 
            @RequestParam Integer targetStatus,
            @Parameter(description = "变更原因，需要详细说明状态变更的具体原因") 
            @RequestParam(required = false) String reason) {
        log.info("变更案件状态: id={}, targetStatus={}, reason={}", id, targetStatus, reason);
        return caseService.changeStatus(id, targetStatus, reason);
    }

    @Operation(
        summary = "审批案件状态变更",
        description = "对案件状态变更申请进行审批。注意：\n" +
                "1. 只有特定状态变更才需要审批\n" +
                "2. 审批时需要提供明确的审批意见\n" +
                "3. 审批通过后状态才会实际变更"
    )
    @PutMapping("/{id}/status/approve")
    public boolean approveStatusChange(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id,
            @Parameter(description = "是否通过：true-通过，false-拒绝") 
            @RequestParam boolean approved,
            @Parameter(description = "审批意见，需要详细说明通过或拒绝的具体理由") 
            @RequestParam(required = false) String opinion) {
        log.info("审批案件状态变更: id={}, approved={}, opinion={}", id, approved, opinion);
        return caseService.approveStatusChange(id, approved, opinion);
    }

    @Operation(
        summary = "归档案件",
        description = "将已完成的案件进行归档。归档要求：\n" +
                "1. 案件状态必须是'完成'\n" +
                "2. 所有必要文档都已上传\n" +
                "3. 所有费用都已结清\n" +
                "4. 归档后案件信息将锁定，不能修改"
    )
    @PutMapping("/{id}/archive")
    public boolean archiveCase(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id) {
        log.info("归档案件: {}", id);
        return caseService.archiveCase(id);
    }

    @Operation(
        summary = "重新激活案件",
        description = "重新激活已归档的案件。激活要求：\n" +
                "1. 案件必须是已归档状态\n" +
                "2. 需要提供充分的激活原因\n" +
                "3. 激活后案件状态将恢复为'进行中'"
    )
    @PutMapping("/{id}/reactivate")
    public boolean reactivateCase(
            @Parameter(description = "案件ID") 
            @PathVariable("id") Long id,
            @Parameter(description = "激活原因，需要详细说明重新激活案件的具体原因") 
            @RequestParam String reason) {
        log.info("重新激活案件: id={}, reason={}", id, reason);
        return caseService.reactivateCase(id, reason);
    }

    @Operation(
        summary = "获取用户相关案件",
        description = "获取指定用户参与的所有案件列表。包括用户作为：\n" +
                "1. 主办律师的案件\n" +
                "2. 协办律师的案件\n" +
                "3. 案件审批人的案件\n" +
                "4. 案件参与人的案件"
    )
    @GetMapping("/user/{userId}")
    public List<CaseQueryVO> getUserCases(
            @Parameter(description = "用户ID") 
            @PathVariable("userId") Long userId) {
        log.info("获取用户相关案件: userId={}", userId);
        List<Case> caseList = caseService.getUserCases(userId);
        // 转换为VO列表
        return caseList.stream()
                .map(this::convertToCaseQueryVO)
                .collect(Collectors.toList());
    }

    /**
     * 将Case实体转换为CaseQueryVO
     *
     * @param caseEntity 案件实体
     * @return CaseQueryVO
     */
    private CaseQueryVO convertToCaseQueryVO(Case caseEntity) {
        CaseQueryVO vo = new CaseQueryVO();
        BeanUtils.copyProperties(caseEntity, vo);
        return vo;
    }
} 