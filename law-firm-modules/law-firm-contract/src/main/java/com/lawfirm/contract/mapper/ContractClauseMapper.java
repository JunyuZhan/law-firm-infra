package com.lawfirm.contract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.contract.entity.ContractClause;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合同条款数据访问层
 */
@Mapper
public interface ContractClauseMapper extends BaseMapper<ContractClause> {
} 