package com.lawfirm.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.client.entity.Client;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户Mapper接口
 */
@Mapper
public interface ClientMapper extends BaseMapper<Client> {
} 