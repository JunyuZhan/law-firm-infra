package com.lawfirm.model.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.task.constants.WorkTaskSqlConstants;
import com.lawfirm.model.task.entity.WorkTaskCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 工作任务分类Mapper接口
 */
@Mapper
public interface WorkTaskCategoryMapper extends BaseMapper<WorkTaskCategory> {
    
    /**
     * 根据父分类ID获取子分类列表
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    @Select(WorkTaskSqlConstants.Category.SELECT_CHILD_CATEGORIES)
    List<WorkTaskCategory> selectChildCategories(Long parentId);
    
    /**
     * 根据分类编码获取分类
     *
     * @param code 分类编码
     * @return 分类
     */
    @Select(WorkTaskSqlConstants.Category.SELECT_BY_CODE)
    WorkTaskCategory selectByCode(String code);
} 