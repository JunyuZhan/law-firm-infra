package com.lawfirm.core.workflow.controller;

import com.lawfirm.common.web.controller.BaseController;
import com.lawfirm.core.workflow.model.BusinessProcess;
import com.lawfirm.core.workflow.service.BusinessProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 业务流程关联控制器
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workflow/business-processes")
public class BusinessProcessController extends BaseController {

    private final BusinessProcessService businessProcessService;

    /**
     * 创建业务流程关联
     */
    @PostMapping
    public BusinessProcess createBusinessProcess(@RequestBody BusinessProcess businessProcess) {
        return businessProcessService.createBusinessProcess(businessProcess);
    }

    /**
     * 更新业务流程关联
     */
    @PutMapping
    public BusinessProcess updateBusinessProcess(@RequestBody BusinessProcess businessProcess) {
        return businessProcessService.updateBusinessProcess(businessProcess);
    }

    /**
     * 删除业务流程关联
     */
    @DeleteMapping("/{businessType}/{businessId}")
    public void deleteBusinessProcess(
            @PathVariable String businessType,
            @PathVariable String businessId) {
        businessProcessService.deleteBusinessProcess(businessType, businessId);
    }

    /**
     * 获取业务流程关联
     */
    @GetMapping("/{businessType}/{businessId}")
    public BusinessProcess getBusinessProcess(
            @PathVariable String businessType,
            @PathVariable String businessId) {
        return businessProcessService.getBusinessProcess(businessType, businessId);
    }

    /**
     * 获取流程实例关联的业务
     */
    @GetMapping("/process-instances/{processInstanceId}")
    public BusinessProcess getByProcessInstanceId(@PathVariable String processInstanceId) {
        return businessProcessService.getByProcessInstanceId(processInstanceId);
    }

    /**
     * 查询业务流程关联列表
     */
    @GetMapping
    public List<BusinessProcess> listBusinessProcesses(
            @RequestParam String businessType,
            @RequestParam(required = false) String processStatus,
            @RequestParam(required = false) String startUserId) {
        return businessProcessService.listBusinessProcesses(businessType, processStatus, startUserId);
    }

    /**
     * 更新流程状态
     */
    @PutMapping("/process-instances/{processInstanceId}/status")
    public void updateProcessStatus(
            @PathVariable String processInstanceId,
            @RequestParam String processStatus) {
        businessProcessService.updateProcessStatus(processInstanceId, processStatus);
    }

    /**
     * 更新当前任务信息
     */
    @PutMapping("/process-instances/{processInstanceId}/current-task")
    public void updateCurrentTask(
            @PathVariable String processInstanceId,
            @RequestParam String taskId,
            @RequestParam String taskName,
            @RequestParam String assignee) {
        businessProcessService.updateCurrentTask(processInstanceId, taskId, taskName, assignee);
    }

    /**
     * 保存业务表单数据
     */
    @PutMapping("/process-instances/{processInstanceId}/form-data")
    public void saveFormData(
            @PathVariable String processInstanceId,
            @RequestBody Map<String, Object> formData) {
        businessProcessService.saveFormData(processInstanceId, formData);
    }

    /**
     * 保存流程变量
     */
    @PutMapping("/process-instances/{processInstanceId}/variables")
    public void saveProcessVariables(
            @PathVariable String processInstanceId,
            @RequestBody Map<String, Object> variables) {
        businessProcessService.saveProcessVariables(processInstanceId, variables);
    }
} 