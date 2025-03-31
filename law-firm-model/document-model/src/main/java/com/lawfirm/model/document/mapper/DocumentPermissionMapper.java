package com.lawfirm.model.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.document.constant.DocumentSqlConstants;
import com.lawfirm.model.document.entity.base.DocumentPermission;
import com.lawfirm.model.document.entity.base.BaseDocument;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 文档权限数据访问接口
 */
@Mapper
public interface DocumentPermissionMapper extends BaseMapper<DocumentPermission> {

    /**
     * 根据文档ID查询权限列表
     */
    @Select(DocumentSqlConstants.Permission.FIND_BY_DOCUMENT_ID)
    List<DocumentPermission> findByDocumentId(@Param("documentId") Long documentId);

    /**
     * 根据文档ID删除权限
     */
    @Select(DocumentSqlConstants.Permission.DELETE_BY_DOCUMENT_ID)
    void deleteByDocumentId(@Param("documentId") Long documentId);

    /**
     * 根据文档ID查询文档
     */
    @Select(DocumentSqlConstants.Permission.FIND_DOCUMENT_BY_ID)
    BaseDocument findDocumentById(@Param("documentId") Long documentId);

    /**
     * 统计用户对文档的权限数
     */
    @Select(DocumentSqlConstants.Permission.COUNT_BY_DOCUMENT_AND_USER)
    int countByDocumentAndUser(@Param("documentId") Long documentId, @Param("userId") String userId);
} 