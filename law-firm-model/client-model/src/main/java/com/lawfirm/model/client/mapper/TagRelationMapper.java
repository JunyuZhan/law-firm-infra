package com.lawfirm.model.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.client.entity.relation.ClientTagRelation;
import com.lawfirm.model.client.constant.ClientSqlConstants;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 客户标签关联Mapper接口
 */
@Mapper
public interface TagRelationMapper extends BaseMapper<ClientTagRelation> {
    
    /**
     * 获取客户的标签关联
     *
     * @param clientId 客户ID
     * @return 标签关联列表
     */
    @Select(ClientSqlConstants.TagRelation.SELECT_BY_CLIENT_ID)
    List<ClientTagRelation> selectByClientId(@Param("clientId") Long clientId);
    
    /**
     * 获取标签关联的客户
     *
     * @param tagId 标签ID
     * @return 标签关联列表
     */
    @Select(ClientSqlConstants.TagRelation.SELECT_BY_TAG_ID)
    List<ClientTagRelation> selectByTagId(@Param("tagId") Long tagId);
    
    /**
     * 删除客户的标签关联
     *
     * @param clientId 客户ID
     * @return 影响行数
     */
    @Delete(ClientSqlConstants.TagRelation.DELETE_BY_CLIENT_ID)
    int deleteByClientId(@Param("clientId") Long clientId);
    
    /**
     * 删除标签的关联
     *
     * @param tagId 标签ID
     * @return 影响行数
     */
    @Delete(ClientSqlConstants.TagRelation.DELETE_BY_TAG_ID)
    int deleteByTagId(@Param("tagId") Long tagId);
    
    /**
     * 删除客户的指定标签关联
     *
     * @param clientId 客户ID
     * @param tagId 标签ID
     * @return 影响行数
     */
    @Delete(ClientSqlConstants.TagRelation.DELETE_BY_CLIENT_ID_AND_TAG_ID)
    int deleteByClientIdAndTagId(@Param("clientId") Long clientId, @Param("tagId") Long tagId);
} 