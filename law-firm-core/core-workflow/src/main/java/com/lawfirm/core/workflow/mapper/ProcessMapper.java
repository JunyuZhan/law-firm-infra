package com.lawfirm.core.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.workflow.entity.ProcessInstance;
import com.lawfirm.model.workflow.dto.process.ProcessQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流程Mapper接口
 * 基于MyBatis Plus提供流程的数据库访问
 *
 * @author JunyuZhan
 * @date 2023/03/03
 */
@Mapper
public interface ProcessMapper extends BaseMapper<ProcessInstance> {
    
    /**
     * 根据流程实例ID查询流程
     * 
     * @param processInstanceId 流程实例ID
     * @return 流程实例
     */
    ProcessInstance selectByProcessInstanceId(@Param("processInstanceId") String processInstanceId);
    
    /**
     * 根据条件查询流程列表
     * 
     * @param queryDTO 查询条件
     * @return 流程列表
     */
    List<ProcessInstance> selectByCondition(@Param("query") ProcessQueryDTO queryDTO);
    
    /**
     * 根据条件分页查询流程
     * 
     * @param page 分页参数
     * @param queryDTO 查询条件
     * @return 分页流程数据
     */
    IPage<ProcessInstance> selectPageByCondition(Page<ProcessInstance> page, @Param("query") ProcessQueryDTO queryDTO);
    
    /**
     * 查询当前处理人的流程
     * 
     * @param handlerId 处理人ID
     * @return 流程列表
     */
    List<ProcessInstance> selectCurrentHandlerProcesses(@Param("handlerId") String handlerId);
} 
