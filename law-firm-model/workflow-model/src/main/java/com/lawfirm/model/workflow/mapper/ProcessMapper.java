package com.lawfirm.model.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.workflow.entity.base.BaseProcess;
import com.lawfirm.model.workflow.constant.WorkflowSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 流程Mapper接口
 * 负责BaseProcess实体的数据访问操作
 */
@Mapper
public interface ProcessMapper extends BaseMapper<BaseProcess> {
    
    /**
     * 根据流程实例ID查询
     *
     * @param instanceId 流程实例ID
     * @return 流程实例
     */
    @Select(WorkflowSqlConstants.Process.SELECT_BY_INSTANCE_ID)
    BaseProcess selectByInstanceId(@Param("instanceId") String instanceId);
    
    /**
     * 根据业务ID查询流程
     *
     * @param businessId 业务ID
     * @param businessType 业务类型
     * @return 流程实例
     */
    @Select(WorkflowSqlConstants.Process.SELECT_BY_BUSINESS_ID)
    BaseProcess selectByBusinessId(@Param("businessId") String businessId, @Param("businessType") String businessType);
    
    /**
     * 查询用户发起的流程
     *
     * @param initiatorId 发起人ID
     * @return 流程列表
     */
    @Select(WorkflowSqlConstants.Process.SELECT_BY_INITIATOR)
    List<BaseProcess> selectByInitiator(@Param("initiatorId") Long initiatorId);
    
    /**
     * 更新流程状态
     *
     * @param id 流程ID
     * @param status 状态
     * @return 影响行数
     */
    @Update(WorkflowSqlConstants.Process.UPDATE_PROCESS_STATUS)
    int updateProcessStatus(@Param("id") Long id, @Param("status") Integer status);
} 