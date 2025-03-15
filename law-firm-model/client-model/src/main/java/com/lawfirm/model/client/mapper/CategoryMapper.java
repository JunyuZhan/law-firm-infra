package com.lawfirm.model.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.client.entity.common.ClientCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类Mapper接口
 */
@Mapper
public interface CategoryMapper extends BaseMapper<ClientCategory> {
    /**
     * 获取子分类列表
     *
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<ClientCategory> selectByParentId(@Param("parentId") Long parentId);
} 