package com.lawfirm.model.search.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.search.entity.SearchDoc;
import com.lawfirm.model.search.constant.SearchSqlConstants;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 搜索文档Mapper接口
 */
@Mapper
public interface SearchDocMapper extends BaseMapper<SearchDoc> {
    
    /**
     * 根据对象ID和类型查询文档
     *
     * @param objectId 对象ID
     * @param objectType 对象类型
     * @return 搜索文档
     */
    @Select(SearchSqlConstants.SearchDoc.FIND_BY_OBJECT_ID_AND_TYPE)
    SearchDoc findByObjectIdAndType(@Param("objectId") String objectId, @Param("objectType") String objectType);
    
    /**
     * 根据索引ID查询文档列表
     *
     * @param indexId 索引ID
     * @return 文档列表
     */
    @Select(SearchSqlConstants.SearchDoc.FIND_BY_INDEX_ID)
    List<SearchDoc> findByIndexId(@Param("indexId") Long indexId);
    
    /**
     * 删除指定类型的文档
     *
     * @param objectType 对象类型
     * @return 影响行数
     */
    @Delete(SearchSqlConstants.SearchDoc.DELETE_BY_OBJECT_TYPE)
    int deleteByObjectType(@Param("objectType") String objectType);
    
    /**
     * 更新文档状态
     *
     * @param id 文档ID
     * @param status 状态
     * @return 影响行数
     */
    @Update(SearchSqlConstants.SearchDoc.UPDATE_STATUS)
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
} 