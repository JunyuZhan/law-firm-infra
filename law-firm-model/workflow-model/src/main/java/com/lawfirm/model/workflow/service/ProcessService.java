package com.lawfirm.model.workflow.service;

import com.lawfirm.model.workflow.dto.process.ProcessCreateDTO;
import com.lawfirm.model.workflow.dto.process.ProcessQueryDTO;
import com.lawfirm.model.workflow.dto.process.ProcessUpdateDTO;
import com.lawfirm.model.workflow.vo.ProcessVO;

import java.util.List;

/**
 * 流程服务接口
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
} 