package com.lawfirm.model.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
    @Select("SELECT * FROM doc_permission WHERE document_id = #{documentId} AND is_enabled = 1")
    List<DocumentPermission> findByDocumentId(@Param("documentId") Long documentId);

    /**
     * 根据文档ID删除权限
     */
    @Select("DELETE FROM doc_permission WHERE document_id = #{documentId}")
    void deleteByDocumentId(@Param("documentId") Long documentId);

    /**
     * 根据文档ID查询文档
     */
    @Select("SELECT * FROM doc_base WHERE id = #{documentId}")
    BaseDocument findDocumentById(@Param("documentId") Long documentId);

    /**
     * 统计用户对文档的权限数
     */
    @Select("SELECT COUNT(*) FROM doc_permission WHERE document_id = #{documentId} AND subject_type = 'USER' AND subject_id = #{userId} AND is_enabled = 1")
    int countByDocumentAndUser(@Param("documentId") Long documentId, @Param("userId") String userId);
} 