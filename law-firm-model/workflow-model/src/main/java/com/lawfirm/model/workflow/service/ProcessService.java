package com.lawfirm.model.workflow.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.workflow.dto.process.ProcessCreateDTO;
import com.lawfirm.model.workflow.dto.process.ProcessQueryDTO;
import com.lawfirm.model.workflow.dto.process.ProcessUpdateDTO;
import com.lawfirm.model.workflow.vo.ProcessVO;

import java.util.List;
import java.util.Map;

/**
 * 流程服务接口
 * 提供流程实例相关的基本操作功�? *
 * @author JunyuZhan
 */
public interface ProcessService {
    /**
     * 创建流程
     *
     * @param createDTO 创建参数
     * @return 流程ID
     */
    Long createProcess(ProcessCreateDTO createDTO);

    /**
     * 更新流程
     *
     * @param updateDTO 更新参数
     */
    void updateProcess(ProcessUpdateDTO updateDTO);

    /**
     * 删除流程
     *
     * @param id 流程ID
     */
    void deleteProcess(Long id);

    /**
     * 获取流程详情
     *
     * @param id 流程ID
     * @return 流程详情
     */
    ProcessVO getProcess(Long id);

    /**
     * 查询流程列表
     *
     * @param queryDTO 查询参数
     * @return 流程列表
     */
    List<ProcessVO> listProcesses(ProcessQueryDTO queryDTO);
    
    /**
     * 分页查询流程列表
     *
     * @param queryDTO 查询参数
     * @param current 当前�?     * @param size 每页条数
     * @return 分页流程列表
     */
    Page<ProcessVO> getProcessList(ProcessQueryDTO queryDTO, int current, int size);

    /**
     * 启动流程
     *
     * @param id 流程ID
     */
    void startProcess(Long id);

    /**
     * 暂停流程
     *
     * @param id 流程ID
     */
    void suspendProcess(Long id);

    /**
     * 恢复流程
     *
     * @param id 流程ID
     */
    void resumeProcess(Long id);

    /**
     * 终止流程
     *
     * @param id 流程ID
     */
    void terminateProcess(Long id);

    /**
     * 取消流程
     *
     * @param id 流程ID
     */
    void cancelProcess(Long id);

    /**
     * 完成流程
     *
     * @param id 流程ID
     */
    void completeProcess(Long id);

    /**
     * 启动流程实例
     *
     * @param processDefinitionKey 流程定义�?     * @param businessKey 业务�?     * @param variables 变量
     * @return 流程实例ID
     */
    String startProcessInstance(String processDefinitionKey, String businessKey, Map<String, Object> variables);

    /**
     * 完成任务
     *
     * @param taskId 任务ID
     * @param variables 变量
     */
    void completeTask(String taskId, Map<String, Object> variables);

    /**
     * 终止流程实例
     *
     * @param processInstanceId 流程实例ID
     * @param reason 原因
     */
    void terminateProcessInstance(String processInstanceId, String reason);
} 
