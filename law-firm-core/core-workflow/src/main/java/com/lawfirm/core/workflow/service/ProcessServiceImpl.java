package com.lawfirm.core.workflow.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.workflow.dto.process.ProcessCreateDTO;
import com.lawfirm.model.workflow.dto.process.ProcessQueryDTO;
import com.lawfirm.model.workflow.dto.process.ProcessUpdateDTO;
import com.lawfirm.model.workflow.service.ProcessService;
import com.lawfirm.model.workflow.vo.ProcessVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 流程服务空实现
 * <p>
 * 当工作流模块被禁用时，提供所有流程相关操作的空实现
 * </p>
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "law-firm", name = "workflow.enabled", havingValue = "false", matchIfMissing = true)
public class ProcessServiceImpl implements ProcessService {

    @Override
    public Long createProcess(ProcessCreateDTO createDTO) {
        log.warn("工作流已禁用，无法创建流程: {}", createDTO);
        return -1L;
    }

    @Override
    public void updateProcess(ProcessUpdateDTO updateDTO) {
        log.warn("工作流已禁用，无法更新流程: {}", updateDTO.getId());
    }

    @Override
    public void deleteProcess(Long id) {
        log.warn("工作流已禁用，无法删除流程: {}", id);
    }

    @Override
    public ProcessVO getProcess(Long id) {
        log.warn("工作流已禁用，无法获取流程: {}", id);
        return null;
    }

    @Override
    public List<ProcessVO> listProcesses(ProcessQueryDTO queryDTO) {
        log.warn("工作流已禁用，无法查询流程列表");
        return Collections.emptyList();
    }

    @Override
    public Page<ProcessVO> getProcessList(ProcessQueryDTO queryDTO, int current, int size) {
        log.warn("工作流已禁用，无法分页查询流程列表");
        return new Page<>(current, size, 0);
    }

    @Override
    public void startProcess(Long id) {
        log.warn("工作流已禁用，无法启动流程: {}", id);
    }

    @Override
    public void suspendProcess(Long id) {
        log.warn("工作流已禁用，无法暂停流程: {}", id);
    }

    @Override
    public void resumeProcess(Long id) {
        log.warn("工作流已禁用，无法恢复流程: {}", id);
    }

    @Override
    public void terminateProcess(Long id) {
        log.warn("工作流已禁用，无法终止流程: {}", id);
    }

    @Override
    public void cancelProcess(Long id) {
        log.warn("工作流已禁用，无法取消流程: {}", id);
    }

    @Override
    public void completeProcess(Long id) {
        log.warn("工作流已禁用，无法完成流程: {}", id);
    }

    @Override
    public String startProcessInstance(String processDefinitionKey, String businessKey, Map<String, Object> variables) {
        log.warn("工作流已禁用，无法启动流程实例: {}, 业务键: {}", processDefinitionKey, businessKey);
        return "workflow-disabled";
    }

    @Override
    public void completeTask(String taskId, Map<String, Object> variables) {
        log.warn("工作流已禁用，无法完成任务: {}", taskId);
    }

    @Override
    public void terminateProcessInstance(String processInstanceId, String reason) {
        log.warn("工作流已禁用，无法终止流程实例: {}, 原因: {}", processInstanceId, reason);
    }
} 