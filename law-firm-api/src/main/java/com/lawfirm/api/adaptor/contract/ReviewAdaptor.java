package com.lawfirm.api.adaptor.contract;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.contract.dto.ContractReviewDTO;
import com.lawfirm.model.contract.dto.ContractReviewQueryDTO;
import com.lawfirm.model.contract.service.ContractReviewService;
import com.lawfirm.model.contract.vo.ContractReviewDetailVO;
import com.lawfirm.model.contract.vo.ContractReviewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 合同审批适配器
 */
@Slf4j
@Component
public class ReviewAdaptor extends BaseAdaptor {

    private final ContractReviewService reviewService;

    public ReviewAdaptor(@Qualifier("contractReviewService") ContractReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * 提交审批
     */
    public Long submitReview(ContractReviewDTO reviewDTO) {
        log.info("提交合同审批, 合同ID: {}, 审批人: {}", reviewDTO.getContractId(), reviewDTO.getReviewerId());
        return reviewService.submitReview(reviewDTO);
    }

    /**
     * 审批通过
     */
    public boolean approveReview(Long reviewId, String comment) {
        log.info("审批通过, 审批ID: {}", reviewId);
        return reviewService.approveReview(reviewId, comment);
    }

    /**
     * 审批拒绝
     */
    public boolean rejectReview(Long reviewId, String comment) {
        log.info("审批拒绝, 审批ID: {}", reviewId);
        return reviewService.rejectReview(reviewId, comment);
    }

    /**
     * 获取审批详情
     */
    public ContractReviewDetailVO getReviewDetail(Long reviewId) {
        log.info("获取审批详情, 审批ID: {}", reviewId);
        return reviewService.getReviewDetail(reviewId);
    }

    /**
     * 查询合同的审批历史
     */
    public List<ContractReviewVO> listContractReviews(Long contractId) {
        log.info("查询合同的审批历史, 合同ID: {}", contractId);
        return reviewService.listContractReviews(contractId);
    }

    /**
     * 分页查询待审批列表
     */
    public IPage<ContractReviewVO> pagePendingReviews(Long reviewerId, Integer pageNum, Integer pageSize) {
        log.info("分页查询待审批列表, 审批人: {}, pageNum={}, pageSize={}", reviewerId, pageNum, pageSize);
        ContractReviewQueryDTO queryDTO = new ContractReviewQueryDTO();
        queryDTO.setReviewerId(reviewerId);
        queryDTO.setStatus(0); // 0-待审核
        Page<ContractReviewVO> page = new Page<>(pageNum, pageSize);
        return reviewService.pageReviews(page, queryDTO);
    }

    /**
     * 分页查询已审批列表
     */
    public IPage<ContractReviewVO> pageCompletedReviews(Long reviewerId, Integer pageNum, Integer pageSize) {
        log.info("分页查询已审批列表, 审批人: {}, pageNum={}, pageSize={}", reviewerId, pageNum, pageSize);
        ContractReviewQueryDTO queryDTO = new ContractReviewQueryDTO();
        queryDTO.setReviewerId(reviewerId);
        queryDTO.setStatus(1); // 1-已通过
        Page<ContractReviewVO> page = new Page<>(pageNum, pageSize);
        return reviewService.pageReviews(page, queryDTO);
    }

    /**
     * 撤销审批
     */
    public boolean revokeReview(Long reviewId, String reason) {
        log.info("撤销审批, 审批ID: {}", reviewId);
        return reviewService.revokeReview(reviewId, reason);
    }

    /**
     * 催办审批
     */
    public boolean urgeReview(Long reviewId) {
        log.info("催办审批, 审批ID: {}", reviewId);
        return reviewService.urgeReview(reviewId);
    }

    /**
     * 转交审批
     */
    public boolean transferReview(Long reviewId, Long newReviewerId, String reason) {
        log.info("转交审批, 审批ID: {}, 新审批人: {}", reviewId, newReviewerId);
        return reviewService.transferReview(reviewId, newReviewerId, reason);
    }
} 