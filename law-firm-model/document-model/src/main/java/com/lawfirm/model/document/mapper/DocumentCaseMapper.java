package com.lawfirm.model.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.document.entity.business.DocumentCase;
import com.lawfirm.model.document.constant.DocumentSqlConstants;
import com.lawfirm.model.document.entity.base.BaseDocument;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 重命名为DocumentCaseMapper避免与案件模块中的同名接口冲突
 */
@Mapper
public interface DocumentCaseMapper extends BaseMapper<DocumentCase> {
    // 继承自BaseMapper的基础方法已经满足大部分需求
    // 如需自定义方法，可在此添加
    
    /**
     * 根据案件ID查询文档
     *
     * @param caseId 案件ID
     * @return 文档列表
     */
    @Select(DocumentSqlConstants.Case.SELECT_BY_CASE_ID)
    List<BaseDocument> selectByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 根据案件ID和文档类型查询
     *
     * @param caseId 案件ID
     * @param documentType 文档类型
     * @return 文档列表
     */
    @Select(DocumentSqlConstants.Case.SELECT_BY_CASE_ID_AND_TYPE)
    List<BaseDocument> selectByCaseIdAndType(@Param("caseId") Long caseId, @Param("documentType") String documentType);
} 