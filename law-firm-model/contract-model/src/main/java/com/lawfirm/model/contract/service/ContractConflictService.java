package com.lawfirm.model.contract.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.contract.dto.ContractConflictCheckDTO;
import com.lawfirm.model.contract.dto.ContractConflictResultDTO;
import com.lawfirm.model.contract.entity.ContractConflict;
import com.lawfirm.model.contract.vo.ContractConflictVO;

import java.util.List;

/**
 * 合同冲突检查服务接口
 */
public interface ContractConflictService extends BaseService<ContractConflict> {
    
    /**
     * 执行合同冲突检查
     *
     * @param checkDTO 检查参数
     * @return 检查结果
     */
    ContractConflictResultDTO checkConflict(ContractConflictCheckDTO checkDTO);
    
    /**
     * 获取合同冲突检查历史
     *
     * @param contractId 合同ID
     * @return 冲突检查历史列表
     */
    List<ContractConflictVO> getConflictHistory(Long contractId);
    
    /**
     * 分页查询合同冲突
     *
     * @param page 分页参数
     * @param contractId 合同ID
     * @param conflictType 冲突类型
     * @param conflictLevel 冲突级别
     * @param isResolved 是否已解决
     * @return 分页结果
     */
    Page<ContractConflictVO> pageConflicts(Page<ContractConflictVO> page, Long contractId, 
            String conflictType, Integer conflictLevel, Boolean isResolved);
    
    /**
     * 解决合同冲突
     *
     * @param conflictId 冲突ID
     * @param resolution 解决方案
     * @return 是否解决成功
     */
    boolean resolveConflict(Long conflictId, String resolution);
    
    /**
     * 批量解决合同冲突
     *
     * @param conflictIds 冲突ID列表
     * @param resolution 解决方案
     * @return 是否解决成功
     */
    boolean batchResolveConflicts(List<Long> conflictIds, String resolution);
    
    /**
     * 获取合同冲突详情
     *
     * @param conflictId 冲突ID
     * @return 冲突详情
     */
    ContractConflictVO getConflictDetail(Long conflictId);
    
    /**
     * 检查合同是否存在冲突
     *
     * @param contractId 合同ID
     * @return 是否存在冲突
     */
    boolean hasConflict(Long contractId);
    
    /**
     * 获取合同未解决的冲突数量
     *
     * @param contractId 合同ID
     * @return 未解决冲突数量
     */
    int getUnresolvedConflictCount(Long contractId);
} 