package com.lawfirm.contract.util;

import com.lawfirm.model.contract.dto.ContractCreateDTO;
import com.lawfirm.model.contract.dto.ContractUpdateDTO;
import com.lawfirm.model.contract.entity.Contract;
import com.lawfirm.model.contract.vo.ContractDetailVO;
import com.lawfirm.model.contract.vo.ContractVO;

/**
 * 合同对象转换工具类
 * 用于实现不同合同相关对象之间的转换
 */
public class ContractConverter {

    /**
     * 将创建DTO转换为实体
     * @param createDTO 创建DTO
     * @return 合同实体
     */
    public static Contract toEntity(ContractCreateDTO createDTO) {
        Contract contract = new Contract();
        contract.setContractNo(createDTO.getContractNo());
        contract.setContractName(createDTO.getContractName());
        contract.setContractType(createDTO.getContractType());
        contract.setAmount(createDTO.getAmount());
        contract.setSigningDate(createDTO.getSigningDate());
        contract.setClientId(createDTO.getClientId());
        contract.setStatus(0); // 默认为草稿状态
        
        return contract;
    }
    
    /**
     * 使用更新DTO更新实体
     * @param entity 实体
     * @param updateDTO 更新DTO
     */
    public static void updateEntity(Contract entity, ContractUpdateDTO updateDTO) {
        if (updateDTO.getContractName() != null) {
            entity.setContractName(updateDTO.getContractName());
        }
        
        if (updateDTO.getContractType() != null) {
            entity.setContractType(updateDTO.getContractType());
        }
        
        if (updateDTO.getAmount() != null) {
            entity.setAmount(updateDTO.getAmount());
        }
        
        if (updateDTO.getSigningDate() != null) {
            entity.setSigningDate(updateDTO.getSigningDate());
        }
        
        if (updateDTO.getStatus() != null) {
            entity.setStatus(updateDTO.getStatus());
        }
    }
    
    /**
     * 将实体转换为VO
     * @param entity 实体
     * @return 合同VO
     */
    public static ContractVO toVO(Contract entity) {
        if (entity == null) {
            return null;
        }
        
        ContractVO vo = new ContractVO();
        vo.setId(entity.getId());
        vo.setContractNo(entity.getContractNo());
        vo.setContractName(entity.getContractName());
        vo.setStatus(entity.getStatus());
        vo.setClientId(entity.getClientId());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        
        return vo;
    }
    
    /**
     * 将实体转换为详情VO
     * @param entity 实体
     * @return 合同详情VO
     */
    public static ContractDetailVO toDetailVO(Contract entity) {
        if (entity == null) {
            return null;
        }
        
        ContractDetailVO detailVO = new ContractDetailVO();
        
        // 基本信息
        detailVO.setId(entity.getId());
        detailVO.setContractNo(entity.getContractNo());
        detailVO.setContractName(entity.getContractName());
        detailVO.setContractType(entity.getContractType());
        detailVO.setStatus(entity.getStatus());
        detailVO.setAmount(entity.getAmount());
        detailVO.setSigningDate(entity.getSigningDate());
        detailVO.setEffectiveDate(entity.getEffectiveDate());
        detailVO.setExpiryDate(entity.getExpiryDate());
        
        // 客户信息
        detailVO.setClientId(entity.getClientId());
        // clientName需要通过客户服务查询，此处暂不处理
        
        // 案件信息
        detailVO.setCaseId(entity.getCaseId());
        // caseName需要通过案件服务查询，此处暂不处理
        
        // 服务信息
        detailVO.setServiceScope(entity.getServiceScope());
        detailVO.setDelegatePower(entity.getDelegatePower());
        detailVO.setFeeType(entity.getFeeType());
        detailVO.setPaymentTerms(entity.getPaymentTerms());
        
        // 承办信息
        detailVO.setLeadAttorneyId(entity.getLeadAttorneyId());
        // leadAttorneyName需要通过律师服务查询，此处暂不处理
        detailVO.setDepartmentId(entity.getDepartmentId());
        // departmentName需要通过部门服务查询，此处暂不处理
        
        // 合同内容
        detailVO.setContractText(entity.getContractText());
        detailVO.setConfidentialityLevel(entity.getConfidentialityLevel());
        detailVO.setRemarks(entity.getRemarks());
        
        // 基础时间信息
        detailVO.setCreateTime(entity.getCreateTime());
        detailVO.setUpdateTime(entity.getUpdateTime());
        
        // 其他相关信息（如团队成员、收费项目、里程碑、附件等）需要通过关联查询获取，此处暂不处理
        
        return detailVO;
    }
} 