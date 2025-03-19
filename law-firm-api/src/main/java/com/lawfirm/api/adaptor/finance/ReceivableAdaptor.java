package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.receivable.ReceivableCreateDTO;
import com.lawfirm.model.finance.dto.receivable.ReceivableUpdateDTO;
import com.lawfirm.model.finance.entity.Receivable;
import com.lawfirm.model.finance.service.ReceivableService;
import com.lawfirm.model.finance.vo.receivable.ReceivableVO;
import com.lawfirm.model.finance.enums.ReceivableStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public ReceivableVO createReceivable(ReceivableCreateDTO dto) {
        Receivable receivable = receivableService.createReceivable(dto);
        return convert(receivable, ReceivableVO.class);
    }

    /**
     * 更新应收账款
     */
    public ReceivableVO updateReceivable(Long id, ReceivableUpdateDTO dto) {
        Receivable receivable = receivableService.updateReceivable(id, dto);
        return convert(receivable, ReceivableVO.class);
    }

    /**
     * 获取应收账款详情
     */
    public ReceivableVO getReceivable(Long id) {
        Receivable receivable = receivableService.getReceivable(id);
        return convert(receivable, ReceivableVO.class);
    }

    /**
     * 删除应收账款
     */
    public void deleteReceivable(Long id) {
        receivableService.deleteReceivable(id);
    }

    /**
     * 获取所有应收账款
     */
    public List<ReceivableVO> listReceivables() {
        List<Receivable> receivables = receivableService.listReceivables();
        return receivables.stream()
                .map(receivable -> convert(receivable, ReceivableVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 更新应收账款状态
     */
    public void updateReceivableStatus(Long id, ReceivableStatusEnum status) {
        receivableService.updateReceivableStatus(id, status);
    }

    /**
     * 根据客户ID查询应收账款
     */
    public List<ReceivableVO> getReceivablesByClientId(Long clientId) {
        List<Receivable> receivables = receivableService.getReceivablesByClientId(clientId);
        return receivables.stream()
                .map(receivable -> convert(receivable, ReceivableVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据合同ID查询应收账款
     */
    public List<ReceivableVO> getReceivablesByContractId(Long contractId) {
        List<Receivable> receivables = receivableService.getReceivablesByContractId(contractId);
        return receivables.stream()
                .map(receivable -> convert(receivable, ReceivableVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询应收账款
     */
    public List<ReceivableVO> getReceivablesByDepartmentId(Long departmentId) {
        List<Receivable> receivables = receivableService.getReceivablesByDepartmentId(departmentId);
        return receivables.stream()
                .map(receivable -> convert(receivable, ReceivableVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 检查应收账款是否存在
     */
    public boolean existsReceivable(Long id) {
        return receivableService.existsReceivable(id);
    }

    /**
     * 获取应收账款数量
     */
    public long countReceivables() {
        return receivableService.countReceivables();
    }
} 