package com.lawfirm.model.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.document.entity.base.DocumentTagRel;
import com.lawfirm.model.document.constant.DocumentSqlConstants;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 文档标签关联Mapper接口
 */
@Mapper
public interface DocumentTagRelMapper extends BaseMapper<DocumentTagRel> {
    
    /**
     * 根据文档ID查询标签关联
     *
     * @param documentId 文档ID
     * @return 标签关联列表
     */
    @Select(DocumentSqlConstants.TagRel.SELECT_BY_DOCUMENT_ID)
    List<DocumentTagRel> selectByDocumentId(@Param("documentId") Long documentId);
    
    /**
     * 根据标签ID查询文档ID列表
     *
     * @param tagId 标签ID
     * @return 文档ID列表
     */
    @Select(DocumentSqlConstants.TagRel.SELECT_DOCUMENT_IDS_BY_TAG_ID)
    List<Long> selectDocumentIdsByTagId(@Param("tagId") Long tagId);
    
    /**
     * 删除文档的标签关联
     *
     * @param documentId 文档ID
     * @return 影响行数
     */
    @Delete(DocumentSqlConstants.TagRel.DELETE_BY_DOCUMENT_ID)
    int deleteByDocumentId(@Param("documentId") Long documentId);
} 