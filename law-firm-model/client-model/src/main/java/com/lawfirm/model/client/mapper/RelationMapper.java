package com.lawfirm.model.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.client.entity.base.ClientRelation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户关系Mapper接口
 */
@Mapper
public interface RelationMapper extends BaseMapper<ClientRelation> {
    /**
     * 获取客户关联关系列表
     *
     * @param clientId 客户ID
     * @return 关系列表
     */
    List<ClientRelation> selectByClientId(@Param("clientId") Long clientId);
    
    /**
     * 获取业务相关的客户关系
     *
     * @param businessId 业务ID
     * @param relationType 关系类型
     * @return 关系列表
     */
    List<ClientRelation> selectByBusiness(@Param("businessId") Long businessId, @Param("relationType") String relationType);
} 