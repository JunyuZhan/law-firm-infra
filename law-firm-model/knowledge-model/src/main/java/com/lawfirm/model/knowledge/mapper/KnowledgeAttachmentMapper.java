package com.lawfirm.model.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.knowledge.entity.KnowledgeAttachment;
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
     * 根据知识ID查询附件列表
     */
    @Select("SELECT * FROM knowledge_attachment WHERE knowledge_id = #{knowledgeId} ORDER BY sort")
    List<KnowledgeAttachment> selectByKnowledgeId(@Param("knowledgeId") Long knowledgeId);

    /**
     * 根据文件类型查询附件列表
     */
    @Select("SELECT * FROM knowledge_attachment WHERE file_type = #{fileType} ORDER BY sort")
    List<KnowledgeAttachment> selectByFileType(@Param("fileType") String fileType);
} 