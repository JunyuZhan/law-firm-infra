package com.lawfirm.model.contract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.contract.entity.ContractReview;
import com.lawfirm.model.contract.constant.ContractSqlConstants;
import com.lawfirm.model.contract.entity.Contract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 合同审核Mapper接口
 */
@Mapper
public interface ContractReviewBaseMapper extends BaseMapper<ContractReview> {
    
    /**
     * 根据合同ID查询审核记录
     *
     * @param contractId 合同ID
     * @return 审核记录列表
     */
    @Select(ContractSqlConstants.Review.SELECT_APPROVAL_BY_CONTRACT_ID)
    List<ContractReview> selectByContractId(@Param("contractId") Long contractId);
    
    /**
     * 查询用户待审核的合同
     *
     * @param approverId 审核人ID
     * @return 待审核合同列表
     */
    @Select(ContractSqlConstants.Review.SELECT_PENDING_REVIEWS)
    List<Contract> selectPendingReviews(@Param("approverId") Long approverId);
    
    /**
     * 查询当前节点的审核记录
     *
     * @param contractId 合同ID
     * @param node 节点编号
     * @return 审核记录
     */
    @Select(ContractSqlConstants.Review.SELECT_CURRENT_NODE_APPROVAL)
    ContractReview selectCurrentNodeApproval(@Param("contractId") Long contractId, @Param("node") Integer node);
} 