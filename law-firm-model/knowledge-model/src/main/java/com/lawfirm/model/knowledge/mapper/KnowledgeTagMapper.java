package com.lawfirm.model.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.knowledge.entity.KnowledgeTag;
import com.lawfirm.model.knowledge.constant.KnowledgeSqlConstants;
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
     * 根据编码查询标签
     */
    @Select(KnowledgeSqlConstants.Tag.SELECT_BY_CODE)
    KnowledgeTag selectByCode(@Param("code") String code);

    /**
     * 根据名称模糊查询标签
     */
    @Select(KnowledgeSqlConstants.Tag.SELECT_BY_NAME)
    List<KnowledgeTag> selectByName(@Param("name") String name);
}