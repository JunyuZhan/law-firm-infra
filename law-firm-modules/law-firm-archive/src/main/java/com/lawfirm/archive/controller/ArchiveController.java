package com.lawfirm.archive.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.archive.dto.CaseArchiveDTO;
import com.lawfirm.model.archive.entity.CaseArchive;
import com.lawfirm.model.archive.entity.ArchiveMain;
import com.lawfirm.model.archive.vo.ArchiveListVO;
import com.lawfirm.model.archive.vo.CaseArchiveDetailVO;
import com.lawfirm.model.archive.service.ArchiveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 档案管理控制器
 */
@RestController
@RequestMapping("/api/archive")
@RequiredArgsConstructor
@Tag(name = "档案管理", description = "档案管理相关接口")
@Slf4j
public class ArchiveController {

    private final ArchiveService archiveService;

    /**
     * 案件归档
     */
    @PostMapping("/case/archive")
    @Operation(summary = "案件归档", description = "接收案件管理模块归档请求")
    public CommonResult<String> archiveCase(@RequestBody @Validated CaseArchiveDTO archiveDTO) {
        log.info("接收案件归档请求，caseId={}", archiveDTO.getCaseId());
        String archiveId = archiveService.createCaseArchive(archiveDTO);
        return CommonResult.success(archiveId);
    }

    /**
     * 获取档案列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取档案列表", description = "根据条件查询档案列表")
    public CommonResult<String> getArchiveList() {
        // ArchiveService没有getArchiveList方法，建议前端直接用分页接口
        return CommonResult.success("请使用分页查询接口");
    }

    /**
     * 获取档案详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取档案详情", description = "根据档案ID获取详情")
    public CommonResult<CaseArchiveDTO> getArchiveDetail(@PathVariable String id) {
        // ArchiveService没有getArchiveDetail方法，建议用getCaseArchiveDetail
        CaseArchiveDTO detail = archiveService.getCaseArchiveDetail(id);
        return CommonResult.success(detail);
    }

    /**
     * 根据档案号获取档案详情
     */
    @GetMapping("/no/{archiveNo}")
    @Operation(summary = "根据档案号获取详情", description = "根据档案编号获取详情")
    public CommonResult<ArchiveMain> getArchiveByNo(@PathVariable String archiveNo) {
        ArchiveMain archive = archiveService.getArchiveByNo(archiveNo);
        if (archive == null) {
            return CommonResult.error("未找到该档案编号对应的档案");
        }
        return CommonResult.success(archive);
    }
    
    /**
     * 更新档案状态
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新档案状态", description = "更新档案状态")
    public CommonResult<Boolean> updateArchiveStatus(
            @PathVariable String id, 
            @RequestParam com.lawfirm.model.archive.enums.ArchiveStatusEnum status) {
        boolean result = archiveService.updateArchiveStatus(id, status);
        return CommonResult.success(result);
    }
    
    /**
     * 设置档案关键词
     */
    // @PutMapping("/{id}/keywords")
    // @Operation(summary = "设置档案关键词", description = "设置档案关键词")
    // public CommonResult<Boolean> setArchiveKeywords(
    //         @PathVariable Long id, 
    //         @RequestParam String keywords) {
    //     // ArchiveService没有setArchiveKeywords方法
    //     return CommonResult.success(false);
    // }
    
    /**
     * 设置档案备注
     */
    // @PutMapping("/{id}/remark")
    // @Operation(summary = "设置档案备注", description = "设置档案备注信息")
    // public CommonResult<Boolean> setArchiveRemark(
    //         @PathVariable Long id, 
    //         @RequestParam String remark) {
    //     // ArchiveService没有setArchiveRemark方法
    //     return CommonResult.success(false);
    // }

    /**
     * 获取案件档案详情
     */
    @GetMapping("/case/{id}")
    @Operation(summary = "获取案件档案详情", description = "根据档案ID获取详情")
    public CommonResult<CaseArchiveDTO> getCaseArchiveDetail(@PathVariable String id) {
        CaseArchiveDTO dto = archiveService.getCaseArchiveDetail(id);
        return CommonResult.success(dto);
    }

    /**
     * 更新案件档案
     */
    @PutMapping("/case/update")
    @Operation(summary = "更新案件档案", description = "更新案件档案信息")
    public CommonResult<Boolean> updateCaseArchive(@RequestBody @Validated CaseArchiveDTO archiveDTO) {
        boolean result = archiveService.updateCaseArchive(archiveDTO);
        return CommonResult.success(result);
    }

    /**
     * 删除案件档案
     */
    @DeleteMapping("/case/{id}")
    @Operation(summary = "删除案件档案", description = "删除案件档案")
    public CommonResult<Boolean> deleteCaseArchive(@PathVariable String id) {
        boolean result = archiveService.deleteCaseArchive(id);
        return CommonResult.success(result);
    }

    /**
     * 分页查询案件档案
     */
    @GetMapping("/case/page")
    @Operation(summary = "分页查询案件档案", description = "分页查询案件档案")
    public CommonResult<Page<CaseArchiveDTO>> pageCaseArchive(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") long current,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") long size,
            @Parameter(description = "案件标题") @RequestParam(required = false) String caseTitle,
            @Parameter(description = "案件编号") @RequestParam(required = false) String caseNo,
            @Parameter(description = "案件类型") @RequestParam(required = false) String caseType,
            @Parameter(description = "案件状态") @RequestParam(required = false) String caseStatus) {
        
        // 构建查询条件
        CaseArchiveDTO condition = new CaseArchiveDTO();
        condition.setCaseTitle(caseTitle);
        condition.setCaseNo(caseNo);
        condition.setCaseType(caseType);
        condition.setCaseStatus(caseStatus);
        
        // 执行查询
        Page<CaseArchive> page = new Page<>(current, size);
        Page<CaseArchiveDTO> result = archiveService.pageCaseArchive(page, condition);
        
        return CommonResult.success(result);
    }

    /**
     * 借阅档案文件
     */
    @PostMapping("/file/borrow")
    @Operation(summary = "借阅档案文件", description = "借阅档案文件")
    public CommonResult<Boolean> borrowArchiveFile(
            @Parameter(description = "文件ID") @RequestParam String fileId,
            @Parameter(description = "借阅人ID") @RequestParam String borrowerId,
            @Parameter(description = "预计归还时间") @RequestParam(required = false) String expectedReturnTime) {
        
        boolean result = archiveService.borrowArchiveFile(fileId, borrowerId, expectedReturnTime);
        return CommonResult.success(result);
    }

    /**
     * 归还档案文件
     */
    @PostMapping("/file/return")
    @Operation(summary = "归还档案文件", description = "归还档案文件")
    public CommonResult<Boolean> returnArchiveFile(
            @Parameter(description = "文件ID") @RequestParam String fileId) {
        
        boolean result = archiveService.returnArchiveFile(fileId);
        return CommonResult.success(result);
    }
} 