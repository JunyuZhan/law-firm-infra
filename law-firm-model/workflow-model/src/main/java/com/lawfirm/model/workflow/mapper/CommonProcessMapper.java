package com.lawfirm.model.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.workflow.entity.common.CommonProcess;
import com.lawfirm.model.workflow.constant.WorkflowSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 通用流程Mapper接口
 * 负责CommonProcess实体的数据访问操作
 */
@Mapper
public interface CommonProcessMapper extends BaseMapper<CommonProcess> {
    
    /**
     * 根据类型查询公共流程
     *
     * @param type 流程类型
     * @return 公共流程列表
     */
    @Select(WorkflowSqlConstants.CommonProcess.SELECT_BY_TYPE)
    List<CommonProcess> selectByType(@Param("type") String type);
    
    /**
     * 查询部门适用的公共流程
     *
     * @param departmentId 部门ID
     * @return 适用的公共流程列表
     */
    @Select(WorkflowSqlConstants.CommonProcess.SELECT_BY_DEPARTMENT)
    List<CommonProcess> selectByDepartment(@Param("departmentId") Long departmentId);
} 