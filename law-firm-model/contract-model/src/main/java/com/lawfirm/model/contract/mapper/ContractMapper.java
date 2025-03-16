package com.lawfirm.model.contract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.contract.entity.Contract;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合同Mapper接口
 */
@Mapper
public interface ContractMapper extends BaseMapper<Contract> {
    // 可以添加自定义的查询方法
} 