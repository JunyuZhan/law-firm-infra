package com.lawfirm.model.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.knowledge.entity.KnowledgeTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 知识标签Mapper接口
 */
@Mapper
public interface KnowledgeTagMapper extends BaseMapper<KnowledgeTag> {

    /**
     * 根据标签编码查询标签
     */
    @Select("SELECT * FROM knowledge_tag WHERE code = #{code}")
    KnowledgeTag selectByCode(@Param("code") String code);

    /**
     * 根据标签名称模糊查询标签列表
     */
    @Select("SELECT * FROM knowledge_tag WHERE name LIKE CONCAT('%', #{name}, '%') ORDER BY sort")
    List<KnowledgeTag> selectByName(@Param("name") String name);
} 