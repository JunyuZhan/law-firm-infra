package com.lawfirm.api.adaptor.finance;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.receivable.ReceivableCreateDTO;
import com.lawfirm.model.finance.dto.receivable.ReceivableUpdateDTO;
import com.lawfirm.model.finance.dto.receivable.ReceivableQueryDTO;
import com.lawfirm.model.finance.entity.Receivable;
import com.lawfirm.model.finance.service.ReceivableService;
import com.lawfirm.model.finance.vo.receivable.ReceivableDetailVO;
import com.lawfirm.model.finance.enums.ReceivableStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 应收账款管理适配器
 */
@Component
public class ReceivableAdaptor extends BaseAdaptor {

    @Autowired
    private ReceivableService receivableService;

    /**
     * 创建应收账款
     */
    public Long createReceivable(ReceivableCreateDTO createDTO) {
        Receivable receivable = convert(createDTO, Receivable.class);
        return receivableService.createReceivable(receivable);
    }

    /**
     * 更新应收账款
     */
    public Boolean updateReceivable(ReceivableUpdateDTO updateDTO) {
        Receivable receivable = convert(updateDTO, Receivable.class);
        return receivableService.updateReceivable(receivable);
    }

    /**
     * 获取应收账款详情
     */
    public ReceivableDetailVO getReceivable(Long id) {
        return receivableService.getReceivable(id);
    }

    /**
     * 删除应收账款
     */
    public Boolean deleteReceivable(Long id) {
        return receivableService.deleteReceivable(id);
    }

    /**
     * 更新应收账款状态
     */
    public Boolean updateReceivableStatus(Long id, ReceivableStatusEnum status, String remark) {
        return receivableService.updateReceivableStatus(id, status, remark);
    }

    /**
     * 记录收款
     */
    public Long recordReceipt(Long id, BigDecimal amount, Long accountId, 
                            LocalDateTime receiveDate, String remark) {
        return receivableService.recordReceipt(id, amount, accountId, receiveDate, remark);
    }

    /**
     * 查询应收账款列表
     */
    public List<ReceivableDetailVO> listReceivables(ReceivableQueryDTO queryDTO) {
        return receivableService.listReceivables(
                queryDTO.getContractId(),
                queryDTO.getClientId(),
                queryDTO.getStatus(),
                queryDTO.getOverdueDays()
        );
    }

    /**
     * 分页查询应收账款
     */
    public IPage<ReceivableDetailVO> pageReceivables(Page<ReceivableDetailVO> page, ReceivableQueryDTO queryDTO) {
        Page<Receivable> receivablePage = new Page<>(page.getCurrent(), page.getSize());
        return receivableService.pageReceivables(
                receivablePage, 
                queryDTO.getContractId(),
                queryDTO.getClientId(),
                queryDTO.getStatus(),
                queryDTO.getOverdueDays()
        );
    }

    /**
     * 按合同查询应收账款
     */
    public List<ReceivableDetailVO> listReceivablesByContract(Long contractId) {
        return receivableService.listReceivablesByContract(contractId);
    }

    /**
     * 按客户查询应收账款
     */
    public List<ReceivableDetailVO> listReceivablesByClient(Long clientId) {
        return receivableService.listReceivablesByClient(clientId);
    }

    /**
     * 统计应收账款总额
     */
    public BigDecimal sumReceivableAmount(ReceivableQueryDTO queryDTO) {
        return receivableService.sumReceivableAmount(
                queryDTO.getContractId(),
                queryDTO.getClientId(),
                queryDTO.getStatus()
        );
    }

    /**
     * 统计已收款总额
     */
    public BigDecimal sumReceivedAmount(ReceivableQueryDTO queryDTO) {
        return receivableService.sumReceivedAmount(
                queryDTO.getContractId(),
                queryDTO.getClientId()
        );
    }

    /**
     * 统计未收款总额
     */
    public BigDecimal sumUnreceivedAmount(ReceivableQueryDTO queryDTO) {
        return receivableService.sumUnreceivedAmount(
                queryDTO.getContractId(),
                queryDTO.getClientId()
        );
    }

    /**
     * 账龄分析
     */
    public Map<String, BigDecimal> agingAnalysis(Long clientId) {
        return receivableService.agingAnalysis(clientId);
    }

    /**
     * 更新应收账款逾期状态
     */
    public Integer updateOverdueStatus() {
        return receivableService.updateOverdueStatus();
    }
} 