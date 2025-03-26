package com.lawfirm.model.contract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.contract.entity.ContractTemplate;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合同模板Mapper接口
 */
@Mapper
public interface ContractTemplateMapper extends BaseMapper<ContractTemplate> {
    // 可以添加自定义的查询方法
} 