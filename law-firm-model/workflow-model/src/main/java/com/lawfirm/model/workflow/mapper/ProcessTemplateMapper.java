package com.lawfirm.model.workflow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.workflow.entity.ProcessTemplate;
import com.lawfirm.model.workflow.constant.WorkflowSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 流程模板Mapper接口
 * 基于MyBatis Plus提供流程模板的数据库访问
 *
 * @author JunyuZhan
 * @date 2023/03/03
 */
@Mapper
public interface ProcessTemplateMapper extends BaseMapper<ProcessTemplate> {
    
    /**
     * 根据模板标识查询最新版本的模板
     * 
     * @param key 模板标识
     * @return 流程模板
     */
    @Select(WorkflowSqlConstants.Template.SELECT_LATEST_BY_KEY)
    ProcessTemplate selectLatestByKey(@Param("key") String key);
    
    /**
     * 根据模板标识和版本号查询模板
     * 
     * @param key 模板标识
     * @param version 版本号
     * @return 流程模板
     */
    @Select(WorkflowSqlConstants.Template.SELECT_BY_KEY_AND_VERSION)
    ProcessTemplate selectByKeyAndVersion(@Param("key") String key, @Param("version") Integer version);
    
    /**
     * 根据流程定义ID查询模板
     * 
     * @param processDefinitionId 流程定义ID
     * @return 流程模板
     */
    @Select(WorkflowSqlConstants.Template.SELECT_BY_DEFINITION_ID)
    ProcessTemplate selectByProcessDefinitionId(@Param("processDefinitionId") String processDefinitionId);
    
    /**
     * 根据分类查询模板列表
     *
     * @param category 模板分类
     * @return 模板列表
     */
    @Select(WorkflowSqlConstants.Template.SELECT_BY_CATEGORY)
    List<ProcessTemplate> selectByCategory(@Param("category") String category);
    
    /**
     * 查询所有启用状态的模板
     *
     * @return 启用状态的模板列表
     */
    @Select(WorkflowSqlConstants.Template.SELECT_ALL_ENABLED)
    List<ProcessTemplate> selectAllEnabled();
    
    /**
     * 分页查询流程模板
     * 
     * @param page 分页参数
     * @param key 模板标识
     * @param name 模板名称
     * @param category 模板分类
     * @return 模板分页数据
     */
    IPage<ProcessTemplate> selectPageByCondition(
            Page<ProcessTemplate> page,
            @Param("key") String key,
            @Param("name") String name,
            @Param("category") String category);
} 
