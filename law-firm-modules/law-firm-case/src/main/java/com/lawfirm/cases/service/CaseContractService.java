package com.lawfirm.cases.service;

import com.lawfirm.model.cases.vo.business.CaseContractVO;
import java.util.List;

/**
 * 案件合同关联服务接口
 * 负责处理案件与合同之间的关联关系，而不直接管理合同
 */
public interface CaseContractService {

    /**
     * 将案件关联到合同
     *
     * @param caseId 案件ID
     * @param contractId 合同ID
     * @return 是否关联成功
     */
    boolean associateContractWithCase(Long caseId, Long contractId);

    /**
     * 解除案件与合同的关联
     *
     * @param caseId 案件ID
     * @param contractId 合同ID
     * @return 是否成功
     */
    boolean disassociateContractFromCase(Long caseId, Long contractId);

    /**
     * 获取案件关联的所有合同
     *
     * @param caseId 案件ID
     * @return 合同列表
     */
    List<CaseContractVO> listCaseContracts(Long caseId);

    /**
     * 检查案件是否有关联的有效合同
     *
     * @param caseId 案件ID
     * @return 是否有有效合同
     */
    boolean checkCaseContractStatus(Long caseId);
    
    /**
     * 获取案件关联的特定合同详情
     *
     * @param caseId 案件ID
     * @param contractId 合同ID
     * @return 合同详情
     */
    CaseContractVO getCaseContractDetail(Long caseId, Long contractId);
} 