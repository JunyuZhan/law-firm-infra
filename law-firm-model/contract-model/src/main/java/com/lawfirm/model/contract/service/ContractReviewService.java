package com.lawfirm.model.contract.service;

import com.lawfirm.model.contract.entity.ContractReview;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.contract.dto.ContractReviewDTO;
import com.lawfirm.model.contract.dto.ContractReviewQueryDTO;
import com.lawfirm.model.contract.vo.ContractReviewDetailVO;
import com.lawfirm.model.contract.vo.ContractReviewVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;

/**
 * 合同审核服务接口，提供合同审核的业务逻辑
 */
public interface ContractReviewService extends BaseService<ContractReview> {

    /**
     * 提交合同审批
     * @param reviewDTO 审核参数
     * @return 审核ID
     */
    Long submitReview(ContractReviewDTO reviewDTO);
    
    /**
     * 审核通过
     * @param id 审核ID
     * @param comment 审核意见
     * @return 是否成功
     */
    boolean approveReview(Long id, String comment);
    
    /**
     * 审核拒绝
     * @param id 审核ID
     * @param comment 审核意见
     * @return 是否成功
     */
    boolean rejectReview(Long id, String comment);
    
    /**
     * 获取审核详情
     * @param id 审核ID
     * @return 审核详情
     */
    ContractReviewDetailVO getReviewDetail(Long id);
    
    /**
     * 查询合同审核历史
     * @param contractId 合同ID
     * @return 审核历史列表
     */
    List<ContractReviewVO> listContractReviews(Long contractId);
    
    /**
     * 分页查询审核列表
     * @param page 分页参数
     * @param queryDTO 查询条件
     * @return 分页结果
     */
    IPage<ContractReviewVO> pageReviews(Page<ContractReviewVO> page, ContractReviewQueryDTO queryDTO);
    
    /**
     * 撤销审核
     * @param id 审核ID
     * @param reason 撤销原因
     * @return 是否成功
     */
    boolean revokeReview(Long id, String reason);
    
    /**
     * 催办审核
     * @param id 审核ID
     * @return 是否成功
     */
    boolean urgeReview(Long id);
    
    /**
     * 转办审核
     * @param id 审核ID
     * @param newReviewerId 新审核人ID
     * @param reason 转办原因
     * @return 是否成功
     */
    boolean transferReview(Long id, Long newReviewerId, String reason);
    
    /**
     * 审核合同
     * @param reviewDTO 审核参数
     * @return 是否成功
     */
    boolean reviewContract(ContractReviewDTO reviewDTO);

    /**
     * 查询合同审核列表
     * @return 合同审核列表
     */
    List<ContractReview> listContractReviews();
} 