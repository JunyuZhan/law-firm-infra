package com.lawfirm.model.search.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.search.entity.SearchIndex;
import com.lawfirm.model.search.constant.SearchSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 搜索索引Mapper接口
 */
@Mapper
public interface SearchIndexMapper extends BaseMapper<SearchIndex> {
    
    /**
     * 根据索引名称查询
     *
     * @param indexName 索引名称
     * @return 索引信息
     */
    @Select(SearchSqlConstants.SearchIndex.FIND_BY_INDEX_NAME)
    SearchIndex findByIndexName(@Param("indexName") String indexName);
    
    /**
     * 根据索引类型查询
     *
     * @param indexType 索引类型
     * @return 索引列表
     */
    @Select(SearchSqlConstants.SearchIndex.FIND_BY_INDEX_TYPE)
    List<SearchIndex> findByIndexType(@Param("indexType") String indexType);
    
    /**
     * 更新索引状态
     *
     * @param id 索引ID
     * @param status 状态
     * @return 影响行数
     */
    @Update(SearchSqlConstants.SearchIndex.UPDATE_STATUS)
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 更新文档数量
     *
     * @param id 索引ID
     * @param docCount 文档数量
     * @return 影响行数
     */
    @Update(SearchSqlConstants.SearchIndex.UPDATE_DOC_COUNT)
    int updateDocCount(@Param("id") Long id, @Param("docCount") Integer docCount);
} 