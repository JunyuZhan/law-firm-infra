package com.lawfirm.model.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.document.entity.base.DocumentCategory;
import com.lawfirm.model.document.constant.DocumentSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 文档分类数据访问层
 */
@Mapper
public interface DocumentCategoryMapper extends BaseMapper<DocumentCategory> {
    
    /**
     * 根据父类ID查询子分类
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    @Select(DocumentSqlConstants.Category.SELECT_BY_PARENT_ID)
    List<DocumentCategory> selectByParentId(@Param("parentId") Long parentId);
    
    /**
     * 查询分类下的文档数量
     *
     * @param categoryId 分类ID
     * @return 文档数量
     */
    @Select(DocumentSqlConstants.Category.COUNT_DOCUMENTS_BY_CATEGORY_ID)
    int countDocumentsByCategoryId(@Param("categoryId") Long categoryId);
    
    /**
     * 查询根分类
     *
     * @return 根分类列表
     */
    @Select(DocumentSqlConstants.Category.SELECT_ROOT_CATEGORIES)
    List<DocumentCategory> selectRootCategories();
} 