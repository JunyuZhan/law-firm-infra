package com.lawfirm.contract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.contract.entity.ContractApproval;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合同审批记录Mapper接口
 */
@Mapper
public interface ContractApprovalMapper extends BaseMapper<ContractApproval> {
} 