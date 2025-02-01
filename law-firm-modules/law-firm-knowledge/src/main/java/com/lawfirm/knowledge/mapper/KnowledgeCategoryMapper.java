package com.lawfirm.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.knowledge.entity.KnowledgeCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 知识分类Mapper接口
 */
@Mapper
public interface KnowledgeCategoryMapper extends BaseMapper<KnowledgeCategory> {

    /**
     * 获取子分类列表
     */
    @Select("SELECT * FROM knowledge_category WHERE parent_id = #{parentId} AND deleted = 0 ORDER BY order_num")
    List<KnowledgeCategory> selectChildren(Long parentId);

    /**
     * 获取分类路径
     */
    @Select("WITH RECURSIVE category_path AS (" +
            "  SELECT id, parent_id, name, 1 as level FROM knowledge_category WHERE id = #{id} AND deleted = 0" +
            "  UNION ALL" +
            "  SELECT c.id, c.parent_id, c.name, cp.level + 1" +
            "  FROM knowledge_category c" +
            "  INNER JOIN category_path cp ON c.id = cp.parent_id" +
            "  WHERE c.deleted = 0" +
            ")" +
            "SELECT * FROM category_path ORDER BY level DESC")
    List<KnowledgeCategory> selectPath(Long id);

    /**
     * 查询分类树
     */
    @Select("WITH RECURSIVE category_tree AS (" +
            "  SELECT id, parent_id, name, 0 as level" +
            "  FROM knowledge_category" +
            "  WHERE parent_id IS NULL AND deleted = 0" +
            "  UNION ALL" +
            "  SELECT c.id, c.parent_id, c.name, ct.level + 1" +
            "  FROM knowledge_category c" +
            "  INNER JOIN category_tree ct ON c.parent_id = ct.id" +
            "  WHERE c.deleted = 0" +
            ")" +
            "SELECT * FROM category_tree ORDER BY level, order_num")
    List<KnowledgeCategory> selectTree();
} 