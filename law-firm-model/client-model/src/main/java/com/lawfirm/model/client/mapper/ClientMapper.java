package com.lawfirm.model.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.client.entity.base.Client;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户Mapper接口
 */
@Mapper
public interface ClientMapper extends BaseMapper<Client> {
    // 可添加自定义查询方法
} 