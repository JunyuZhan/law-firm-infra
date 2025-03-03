package com.lawfirm.model.contract.service;

import com.lawfirm.model.contract.entity.Contract;
import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.contract.dto.ContractCreateDTO;
import com.lawfirm.model.contract.dto.ContractUpdateDTO;
import com.lawfirm.model.contract.dto.ContractQueryDTO;
import com.lawfirm.model.contract.vo.ContractVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 合同服务接口，提供合同相关的业务逻辑
 */
public interface ContractService extends BaseService<Contract> {

    /**
     * 创建合同
     * @param createDTO 创建参数
     * @return 合同ID
     */
    Long createContract(ContractCreateDTO createDTO);

    /**
     * 更新合同
     * @param updateDTO 更新参数
     * @return 是否成功
     */
    boolean updateContract(ContractUpdateDTO updateDTO);

    /**
     * 查询合同列表
     * @param queryDTO 查询参数
     * @return 合同列表
     */
    List<ContractVO> listContracts(ContractQueryDTO queryDTO);

    /**
     * 分页查询合同
     * @param page 分页参数
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    IPage<ContractVO> pageContracts(IPage<ContractVO> page, ContractQueryDTO queryDTO);
} 