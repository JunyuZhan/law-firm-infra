package com.lawfirm.model.contract.service;

import com.lawfirm.model.contract.entity.ContractReview;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.contract.dto.ContractReviewDTO;
import java.util.List;

/**
 * 合同审核服务接口，提供合同审核的业务逻辑
 */
public interface ContractReviewService extends BaseService<ContractReview> {

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