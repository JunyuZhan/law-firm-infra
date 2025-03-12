package com.lawfirm.document.controller.support;

import com.lawfirm.common.core.response.ResponseResult;
import com.lawfirm.document.manager.search.DocumentSearchManager;
import com.lawfirm.document.manager.security.DocumentSecurityManager;
import com.lawfirm.document.manager.workflow.DocumentWorkflowManager;
import com.lawfirm.model.document.vo.DocumentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 文档支持控制器
 */
@Tag(name = "文档支持接口")
@RestController
@RequestMapping("/support/document")
@RequiredArgsConstructor
public class DocumentSupportController {

    private final DocumentSearchManager searchManager;
    private final DocumentSecurityManager securityManager;
    private final DocumentWorkflowManager workflowManager;

    @Operation(summary = "全文检索")
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('document:search')")
    public ResponseResult<List<DocumentVO>> search(@RequestParam String keyword) {
        List<DocumentVO> documents = searchManager.search(keyword);
        return ResponseResult.success(documents);
    }

    @Operation(summary = "相关文档推荐")
    @GetMapping("/{id}/recommend")
    @PreAuthorize("hasAuthority('document:view')")
    public ResponseResult<List<DocumentVO>> recommend(@PathVariable Long id) {
        List<DocumentVO> documents = searchManager.recommend(id);
        return ResponseResult.success(documents);
    }

    @Operation(summary = "获取文档权限")
    @GetMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('document:manage')")
    public ResponseResult<Map<String, Object>> getPermissions(@PathVariable Long id) {
        Map<String, Object> permissions = securityManager.getPermissions(id);
        return ResponseResult.success(permissions);
    }

    @Operation(summary = "设置文档权限")
    @PutMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('document:manage')")
    public ResponseResult<Void> setPermissions(@PathVariable Long id, @RequestBody Map<String, Object> permissions) {
        securityManager.setPermissions(id, permissions);
        return ResponseResult.success();
    }

    @Operation(summary = "提交审批")
    @PostMapping("/{id}/submit")
    @PreAuthorize("hasAuthority('document:submit')")
    public ResponseResult<String> submitForApproval(@PathVariable Long id, @RequestParam String processKey) {
        String instanceId = workflowManager.submitForApproval(id, processKey);
        return ResponseResult.success(instanceId);
    }

    @Operation(summary = "审批操作")
    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAuthority('document:approve')")
    public ResponseResult<Void> approve(@PathVariable Long id, @RequestParam String taskId, @RequestParam Boolean approved, @RequestParam String comment) {
        workflowManager.approve(id, taskId, approved, comment);
        return ResponseResult.success();
    }

    @Operation(summary = "获取审批历史")
    @GetMapping("/{id}/approval-history")
    @PreAuthorize("hasAuthority('document:view')")
    public ResponseResult<List<Map<String, Object>>> getApprovalHistory(@PathVariable Long id) {
        List<Map<String, Object>> history = workflowManager.getApprovalHistory(id);
        return ResponseResult.success(history);
    }

    @Operation(summary = "获取文档操作日志")
    @GetMapping("/{id}/audit-logs")
    @PreAuthorize("hasAuthority('document:view')")
    public ResponseResult<List<Map<String, Object>>> getAuditLogs(@PathVariable Long id) {
        List<Map<String, Object>> logs = securityManager.getAuditLogs(id);
        return ResponseResult.success(logs);
    }

    @Operation(summary = "检查文档敏感信息")
    @GetMapping("/{id}/sensitive-check")
    @PreAuthorize("hasAuthority('document:check')")
    public ResponseResult<Map<String, Object>> checkSensitiveInfo(@PathVariable Long id) {
        Map<String, Object> result = securityManager.checkSensitiveInfo(id);
        return ResponseResult.success(result);
    }

    @Operation(summary = "生成文档摘要")
    @GetMapping("/{id}/summary")
    @PreAuthorize("hasAuthority('document:view')")
    public ResponseResult<String> generateSummary(@PathVariable Long id) {
        String summary = searchManager.generateSummary(id);
        return ResponseResult.success(summary);
    }

    @Operation(summary = "提取关键信息")
    @GetMapping("/{id}/key-info")
    @PreAuthorize("hasAuthority('document:view')")
    public ResponseResult<Map<String, Object>> extractKeyInfo(@PathVariable Long id) {
        Map<String, Object> keyInfo = searchManager.extractKeyInfo(id);
        return ResponseResult.success(keyInfo);
    }
}
