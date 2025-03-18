package com.lawfirm.cases.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.base.CaseCreateDTO;
import com.lawfirm.model.cases.dto.base.CaseQueryDTO;
import com.lawfirm.model.cases.dto.base.CaseUpdateDTO;
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
 * 案件基本控制器实现
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/cases")
@RequiredArgsConstructor
@Tag(name = "案件管理", description = "案件基本信息管理接口")
public class CaseControllerImpl {

    private final CaseService caseService;

    @PostMapping
    @Operation(summary = "创建案件")
    public Long createCase(@RequestBody @Validated CaseCreateDTO createDTO) {
        log.info("创建案件: {}", createDTO);
        return caseService.createCase(createDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新案件")
    public boolean updateCase(@PathVariable("id") Long id, 
                            @RequestBody @Validated CaseUpdateDTO updateDTO) {
        log.info("更新案件: id={}, {}", id, updateDTO);
        updateDTO.setId(id);
        return caseService.updateCase(updateDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除案件")
    public boolean deleteCase(@PathVariable("id") Long id) {
        log.info("删除案件: {}", id);
        return caseService.deleteCase(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取案件详情")
    public CaseDetailVO getCaseDetail(@PathVariable("id") Long id) {
        log.info("获取案件详情: {}", id);
        return caseService.getCaseDetail(id);
    }

    @GetMapping
    @Operation(summary = "分页查询案件")
    public IPage<CaseQueryVO> pageCases(@Validated CaseQueryDTO queryDTO) {
        log.info("分页查询案件: {}", queryDTO);
        return caseService.pageCases(queryDTO);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "变更案件状态")
    public boolean changeStatus(@PathVariable("id") Long id,
                              @Parameter(description = "目标状态") @RequestParam Integer targetStatus,
                              @Parameter(description = "变更原因") @RequestParam(required = false) String reason) {
        log.info("变更案件状态: id={}, targetStatus={}, reason={}", id, targetStatus, reason);
        return caseService.changeStatus(id, targetStatus, reason);
    }

    @PutMapping("/{id}/status/approve")
    @Operation(summary = "审批案件状态变更")
    public boolean approveStatusChange(@PathVariable("id") Long id,
                                     @Parameter(description = "是否通过") @RequestParam boolean approved,
                                     @Parameter(description = "审批意见") @RequestParam(required = false) String opinion) {
        log.info("审批案件状态变更: id={}, approved={}, opinion={}", id, approved, opinion);
        return caseService.approveStatusChange(id, approved, opinion);
    }

    @PutMapping("/{id}/archive")
    @Operation(summary = "归档案件")
    public boolean archiveCase(@PathVariable("id") Long id) {
        log.info("归档案件: {}", id);
        return caseService.archiveCase(id);
    }

    @PutMapping("/{id}/reactivate")
    @Operation(summary = "重新激活案件")
    public boolean reactivateCase(@PathVariable("id") Long id,
                                @Parameter(description = "激活原因") @RequestParam String reason) {
        log.info("重新激活案件: id={}, reason={}", id, reason);
        return caseService.reactivateCase(id, reason);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户相关案件")
    public List<CaseQueryVO> getUserCases(@PathVariable("userId") Long userId) {
        log.info("获取用户相关案件: userId={}", userId);
        return caseService.getUserCases(userId).stream()
                .map(entity -> {
                    CaseQueryVO vo = new CaseQueryVO();
                    BeanUtils.copyProperties(entity, vo);
                    return vo;
                })
                .collect(Collectors.toList());
    }
} 