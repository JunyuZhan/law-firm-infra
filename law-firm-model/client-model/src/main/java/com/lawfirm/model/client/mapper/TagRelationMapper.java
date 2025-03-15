package com.lawfirm.model.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.client.entity.relation.ClientTagRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    List<ClientTagRelation> selectByClientId(@Param("clientId") Long clientId);
    
    /**
     * 获取标签关联的客户
     *
     * @param tagId 标签ID
     * @return 标签关联列表
     */
    List<ClientTagRelation> selectByTagId(@Param("tagId") Long tagId);
    
    /**
     * 删除客户的标签关联
     *
     * @param clientId 客户ID
     * @return 影响行数
     */
    int deleteByClientId(@Param("clientId") Long clientId);
    
    /**
     * 删除标签的关联
     *
     * @param tagId 标签ID
     * @return 影响行数
     */
    int deleteByTagId(@Param("tagId") Long tagId);
    
    /**
     * 删除客户的指定标签关联
     *
     * @param clientId 客户ID
     * @param tagId 标签ID
     * @return 影响行数
     */
    int deleteByClientIdAndTagId(@Param("clientId") Long clientId, @Param("tagId") Long tagId);
} 