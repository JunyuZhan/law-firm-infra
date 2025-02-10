package com.lawfirm.core.workflow.controller;

import com.lawfirm.common.web.controller.BaseController;
import com.lawfirm.core.workflow.model.ProcessPermission;
import com.lawfirm.core.workflow.service.ProcessPermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程权限控制器
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workflow/permissions")
public class ProcessPermissionController extends BaseController {

    private final ProcessPermissionService processPermissionService;

    /**
     * 创建流程权限
     */
    @PostMapping
    public ProcessPermission createPermission(@RequestBody ProcessPermission permission) {
        return processPermissionService.createPermission(permission);
    }

    /**
     * 更新流程权限
     */
    @PutMapping
    public ProcessPermission updatePermission(@RequestBody ProcessPermission permission) {
        return processPermissionService.updatePermission(permission);
    }

    /**
     * 删除流程权限
     */
    @DeleteMapping("/{processKey}")
    public void deletePermission(@PathVariable String processKey) {
        processPermissionService.deletePermission(processKey);
    }

    /**
     * 获取流程权限
     */
    @GetMapping("/{processKey}")
    public ProcessPermission getPermission(@PathVariable String processKey) {
        return processPermissionService.getPermission(processKey);
    }

    /**
     * 查询流程权限列表
     */
    @GetMapping
    public List<ProcessPermission> listPermissions(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean enabled) {
        return processPermissionService.listPermissions(category, enabled);
    }

    /**
     * 检查流程发起权限
     */
    @GetMapping("/{processKey}/check-start")
    public boolean checkStartPermission(
            @PathVariable String processKey,
            @RequestParam String userId) {
        return processPermissionService.checkStartPermission(processKey, userId);
    }

    /**
     * 检查任务处理权限
     */
    @GetMapping("/{processKey}/tasks/{taskKey}/check-permission")
    public boolean checkTaskPermission(
            @PathVariable String processKey,
            @PathVariable String taskKey,
            @RequestParam String userId) {
        return processPermissionService.checkTaskPermission(processKey, taskKey, userId);
    }

    /**
     * 获取可发起流程列表
     */
    @GetMapping("/startable-processes")
    public List<String> getStartableProcesses(@RequestParam String userId) {
        return processPermissionService.getStartableProcesses(userId);
    }

    /**
     * 获取任务候选人列表
     */
    @GetMapping("/{processKey}/tasks/{taskKey}/candidates")
    public List<String> getTaskCandidates(
            @PathVariable String processKey,
            @PathVariable String taskKey) {
        return processPermissionService.getTaskCandidates(processKey, taskKey);
    }
} 