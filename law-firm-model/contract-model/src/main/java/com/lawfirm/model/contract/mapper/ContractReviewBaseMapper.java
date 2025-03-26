package com.lawfirm.model.contract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.contract.entity.ContractReview;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合同审核Mapper接口
 */
@Mapper
public interface ContractReviewBaseMapper extends BaseMapper<ContractReview> {
    // 可以添加自定义的查询方法
} 