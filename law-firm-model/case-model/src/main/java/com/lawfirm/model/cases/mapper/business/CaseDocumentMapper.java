package com.lawfirm.model.cases.mapper.business;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.business.CaseDocument;
import com.lawfirm.model.cases.constants.CaseSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 案件文档Mapper接口
 */
@Mapper
public interface CaseDocumentMapper extends BaseMapper<CaseDocument> {
    
    /**
     * 根据案件ID查询文档
     *
     * @param caseId 案件ID
     * @return 文档列表
     */
    @Select(CaseSqlConstants.Document.SELECT_BY_CASE_ID)
    List<CaseDocument> selectByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 根据文档类型查询
     *
     * @param caseId 案件ID
     * @param documentType 文档类型
     * @return 文档列表
     */
    @Select(CaseSqlConstants.Document.SELECT_BY_TYPE)
    List<CaseDocument> selectByType(@Param("caseId") Long caseId, @Param("documentType") Integer documentType);

    /**
     * 根据文档ID查询案件文档
     *
     * @param documentId 文档ID
     * @return 案件文档
     */
    @Select("SELECT * FROM case_document WHERE document_id = #{documentId} AND deleted = 0")
    CaseDocument selectByDocumentId(@Param("documentId") Long documentId);
} 