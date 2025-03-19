package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.fee.FeeCreateDTO;
import com.lawfirm.model.finance.dto.fee.FeeUpdateDTO;
import com.lawfirm.model.finance.entity.Fee;
import com.lawfirm.model.finance.service.FeeService;
import com.lawfirm.model.finance.vo.fee.FeeVO;
import com.lawfirm.model.finance.enums.FeeStatusEnum;
import com.lawfirm.model.finance.enums.FeeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 费用管理适配器
 */
@Component
public class FeeAdaptor extends BaseAdaptor {

    @Autowired
    private FeeService feeService;

    /**
     * 创建费用
     */
    public FeeVO createFee(FeeCreateDTO dto) {
        Fee fee = feeService.createFee(dto);
        return convert(fee, FeeVO.class);
    }

    /**
     * 更新费用
     */
    public FeeVO updateFee(Long id, FeeUpdateDTO dto) {
        Fee fee = feeService.updateFee(id, dto);
        return convert(fee, FeeVO.class);
    }

    /**
     * 获取费用详情
     */
    public FeeVO getFee(Long id) {
        Fee fee = feeService.getFee(id);
        return convert(fee, FeeVO.class);
    }

    /**
     * 删除费用
     */
    public void deleteFee(Long id) {
        feeService.deleteFee(id);
    }

    /**
     * 获取所有费用
     */
    public List<FeeVO> listFees() {
        List<Fee> fees = feeService.listFees();
        return fees.stream()
                .map(fee -> convert(fee, FeeVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 更新费用状态
     */
    public void updateFeeStatus(Long id, FeeStatusEnum status) {
        feeService.updateFeeStatus(id, status);
    }

    /**
     * 根据费用类型查询费用
     */
    public List<FeeVO> getFeesByType(FeeTypeEnum type) {
        List<Fee> fees = feeService.getFeesByType(type);
        return fees.stream()
                .map(fee -> convert(fee, FeeVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询费用
     */
    public List<FeeVO> getFeesByDepartmentId(Long departmentId) {
        List<Fee> fees = feeService.getFeesByDepartmentId(departmentId);
        return fees.stream()
                .map(fee -> convert(fee, FeeVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据预算ID查询费用
     */
    public List<FeeVO> getFeesByBudgetId(Long budgetId) {
        List<Fee> fees = feeService.getFeesByBudgetId(budgetId);
        return fees.stream()
                .map(fee -> convert(fee, FeeVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 检查费用是否存在
     */
    public boolean existsFee(Long id) {
        return feeService.existsFee(id);
    }

    /**
     * 获取费用数量
     */
    public long countFees() {
        return feeService.countFees();
    }
} 