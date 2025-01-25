package com.lawfirm.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.knowledge.entity.Knowledge;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 知识库数据访问层
 */
public interface KnowledgeMapper extends BaseMapper<Knowledge> {

    /**
     * 根据分类ID查询知识列表
     */
    @Select("SELECT * FROM knowledge WHERE category_id = #{categoryId} AND deleted = 0")
    List<Knowledge> selectByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 根据标签查询知识列表
     */
    @Select("SELECT * FROM knowledge WHERE tags LIKE CONCAT('%',#{tag},'%') AND deleted = 0")
    List<Knowledge> selectByTag(@Param("tag") String tag);

    /**
     * 根据作者ID查询知识列表
     */
    @Select("SELECT * FROM knowledge WHERE author_id = #{authorId} AND deleted = 0")
    List<Knowledge> selectByAuthorId(@Param("authorId") Long authorId);

    /**
     * 增加浏览次数
     */
    @Select("UPDATE knowledge SET view_count = view_count + 1 WHERE id = #{id}")
    void incrementViewCount(@Param("id") Long id);

    /**
     * 增加点赞次数
     */
    @Select("UPDATE knowledge SET like_count = like_count + 1 WHERE id = #{id}")
    void incrementLikeCount(@Param("id") Long id);

    /**
     * 增加收藏次数
     */
    @Select("UPDATE knowledge SET favorite_count = favorite_count + 1 WHERE id = #{id}")
    void incrementFavoriteCount(@Param("id") Long id);

    /**
     * 减少点赞次数
     */
    @Select("UPDATE knowledge SET like_count = like_count - 1 WHERE id = #{id} AND like_count > 0")
    void decrementLikeCount(@Param("id") Long id);

    /**
     * 减少收藏次数
     */
    @Select("UPDATE knowledge SET favorite_count = favorite_count - 1 WHERE id = #{id} AND favorite_count > 0")
    void decrementFavoriteCount(@Param("id") Long id);

    /**
     * 统计各类型知识数量
     */
    @Select("SELECT type, COUNT(*) as count FROM knowledge WHERE deleted = 0 GROUP BY type")
    List<Map<String, Object>> countByType();

    /**
     * 统计各状态知识数量
     */
    @Select("SELECT status, COUNT(*) as count FROM knowledge WHERE deleted = 0 GROUP BY status")
    List<Map<String, Object>> countByStatus();

    /**
     * 统计部门知识数量
     */
    @Select("SELECT department_id, COUNT(*) as count FROM knowledge WHERE deleted = 0 GROUP BY department_id")
    List<Map<String, Object>> countByDepartment();

    /**
     * 统计时间段内的知识数量
     */
    @Select("SELECT DATE_FORMAT(create_time,'%Y-%m') as month, COUNT(*) as count FROM knowledge " +
            "WHERE deleted = 0 AND create_time BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY DATE_FORMAT(create_time,'%Y-%m')")
    List<Map<String, Object>> countByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
} 