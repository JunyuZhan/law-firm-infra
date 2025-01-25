package com.lawfirm.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.client.entity.Client;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClientMapper extends BaseMapper<Client> {
} 