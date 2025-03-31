package com.lawfirm.model.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.knowledge.entity.KnowledgeAttachment;
import com.lawfirm.model.knowledge.constant.KnowledgeSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 知识附件Mapper接口
 */
@Mapper
public interface KnowledgeAttachmentMapper extends BaseMapper<KnowledgeAttachment> {

    /**
     * 查询知识的附件列表
     */
    @Select(KnowledgeSqlConstants.Attachment.SELECT_BY_KNOWLEDGE_ID)
    List<KnowledgeAttachment> selectByKnowledgeId(@Param("knowledgeId") Long knowledgeId);

    /**
     * 根据文件类型查询附件
     */
    @Select(KnowledgeSqlConstants.Attachment.SELECT_BY_FILE_TYPE)
    List<KnowledgeAttachment> selectByFileType(@Param("fileType") String fileType);
} 