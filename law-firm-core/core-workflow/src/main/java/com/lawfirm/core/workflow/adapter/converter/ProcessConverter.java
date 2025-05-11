package com.lawfirm.core.workflow.adapter.converter;

import com.lawfirm.core.workflow.vo.ProcessInstanceVO;
import com.lawfirm.core.workflow.vo.ProcessVO;
import com.lawfirm.model.workflow.dto.process.ProcessCreateDTO;
import com.lawfirm.model.workflow.entity.ProcessInstance;
import com.lawfirm.model.workflow.entity.base.BaseProcess;
import com.lawfirm.model.workflow.enums.ProcessStatusEnum;
import com.lawfirm.model.workflow.enums.ProcessTypeEnum;
import com.lawfirm.model.workflow.enums.TaskPriorityEnum;
import com.lawfirm.core.workflow.vo.ProcessDefinitionVO;
import org.flowable.engine.repository.ProcessDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 流程数据转换器
 * 
 * @author JunyuZhan
 */
@Component("coreProcessConverter")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "law-firm", name = "workflow.enabled", havingValue = "true", matchIfMissing = false)
public class ProcessConverter {

    /**
     * 将创建DTO转换为实体
     * 
     * @param createDTO 创建DTO
     * @return 实体对象
     */
    public BaseProcess toEntity(ProcessCreateDTO createDTO) {
        if (createDTO == null) {
            return null;
        }
        
        BaseProcess process = new BaseProcess();
        process.setProcessName(createDTO.getProcessName())
               .setProcessType(createDTO.getProcessType())
               .setDescription(createDTO.getDescription())
               .setBusinessId(createDTO.getBusinessId())
               .setBusinessType(createDTO.getBusinessType())
               .setPriority(createDTO.getPriority())
               .setAllowRevoke(createDTO.getAllowRevoke())
               .setAllowTransfer(createDTO.getAllowTransfer())
               .setProcessConfig(createDTO.getProcessConfig());
        
        return process;
    }
    
    /**
     * 将创建DTO转换为ProcessInstance实体
     * 
     * @param createDTO 创建DTO
     * @return ProcessInstance实体对象
     */
    public ProcessInstance toProcessInstance(ProcessCreateDTO createDTO) {
        if (createDTO == null) {
            return null;
        }
        
        ProcessInstance process = new ProcessInstance();
        process.setProcessName(createDTO.getProcessName());
        process.setProcessType(createDTO.getProcessType());
        process.setDescription(createDTO.getDescription());
        process.setBusinessId(createDTO.getBusinessId());
        process.setBusinessType(createDTO.getBusinessType());
        process.setPriority(createDTO.getPriority());
        process.setAllowRevoke(createDTO.getAllowRevoke());
        process.setAllowTransfer(createDTO.getAllowTransfer());
        process.setProcessConfig(createDTO.getProcessConfig());
        
        return process;
    }
    
    /**
     * 将实体转换为视图对象
     * 
     * @param process 实体对象
     * @return 视图对象
     */
    public ProcessVO toVO(BaseProcess process) {
        if (process == null) {
            return null;
        }
        
        ProcessVO vo = new ProcessVO();
        vo.setId(process.getId())
          .setProcessName(process.getProcessName())
          .setStatus(process.getStatus())
          .setDescription(process.getDescription())
          .setBusinessId(process.getBusinessId())
          .setBusinessType(process.getBusinessType())
          .setCurrentHandlerId(process.getCurrentHandlerId())
          .setCurrentHandlerName(process.getCurrentHandlerName())
          .setStartTime(process.getStartTime())
          .setEndTime(process.getEndTime())
          .setCreateTime(process.getCreateTime())
          .setUpdateTime(process.getUpdateTime());
        
        return vo;
    }
    
    /**
     * 将ProcessInstance实体转换为ProcessInstanceVO视图对象
     * 
     * @param process ProcessInstance实体对象
     * @return ProcessInstanceVO视图对象
     */
    public ProcessInstanceVO toProcessInstanceVO(ProcessInstance process) {
        if (process == null) {
            return null;
        }
        
        ProcessInstanceVO vo = new ProcessInstanceVO();
        vo.setId(process.getProcessInstanceId());
        vo.setProcessDefinitionId(process.getProcessDefinitionId());
        vo.setProcessDefinitionKey(process.getProcessDefinitionKey());
        vo.setProcessDefinitionName(process.getProcessDefinitionName());
        vo.setProcessDefinitionVersion(process.getProcessDefinitionVersion());
        vo.setDeploymentId(process.getDeploymentId());
        vo.setBusinessKey(process.getBusinessKey());
        vo.setStartTime(process.getStartTime());
        vo.setEndTime(process.getEndTime());
        vo.setName(process.getProcessName());
        vo.setDescription(process.getDescription());
        
        return vo;
    }

    /**
     * 将Flowable流程定义转换为视图对象
     * 
     * @param processDefinition Flowable流程定义
     * @return 流程定义视图对象
     */
    public ProcessDefinitionVO toProcessDefinitionVO(ProcessDefinition processDefinition) {
        if (processDefinition == null) {
            return null;
        }

        ProcessDefinitionVO vo = new ProcessDefinitionVO();
        vo.setId(processDefinition.getId());
        vo.setKey(processDefinition.getKey());
        vo.setName(processDefinition.getName());
        vo.setCategory(processDefinition.getCategory());
        vo.setVersion(processDefinition.getVersion());
        vo.setDeploymentId(processDefinition.getDeploymentId());
        vo.setResourceName(processDefinition.getResourceName());
        vo.setDiagramResourceName(processDefinition.getDiagramResourceName());
        vo.setDescription(processDefinition.getDescription());
        vo.setSuspended(processDefinition.isSuspended());
        vo.setTenantId(processDefinition.getTenantId());
        vo.setEngineVersion(processDefinition.getEngineVersion());
        vo.setHasStartForm(processDefinition.hasStartFormKey());
        vo.setGraphicalNotation(processDefinition.hasGraphicalNotation());

        return vo;
    }
} 
