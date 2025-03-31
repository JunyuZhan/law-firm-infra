package com.lawfirm.model.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.document.entity.template.TemplateDocument;
import com.lawfirm.model.document.constant.DocumentSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 模板文档数据访问层
 */
@Mapper
public interface TemplateDocumentMapper extends BaseMapper<TemplateDocument> {
    
    /**
     * 查询分类下的模板文档
     *
     * @param categoryId 分类ID
     * @return 模板文档列表
     */
    @Select(DocumentSqlConstants.Template.SELECT_BY_CATEGORY_ID)
    List<TemplateDocument> selectByCategoryId(@Param("categoryId") Long categoryId);
    
    /**
     * 根据模板编码查询
     *
     * @param templateCode 模板编码
     * @return 模板文档
     */
    @Select(DocumentSqlConstants.Template.SELECT_BY_TEMPLATE_CODE)
    TemplateDocument selectByTemplateCode(@Param("templateCode") String templateCode);
} 