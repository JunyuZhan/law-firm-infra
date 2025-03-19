package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.entity.CostCenter;
import com.lawfirm.model.finance.service.CostCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 成本中心管理适配器
 */
@Component
public class CostCenterAdaptor extends BaseAdaptor {

    @Autowired
    private CostCenterService costCenterService;

    /**
     * 创建成本中心
     */
    public CostCenter createCostCenter(CostCenter costCenter) {
        Long id = costCenterService.createCostCenter(costCenter);
        return costCenterService.getCostCenterById(id);
    }

    /**
     * 更新成本中心
     */
    public CostCenter updateCostCenter(CostCenter costCenter) {
        costCenterService.updateCostCenter(costCenter);
        return costCenterService.getCostCenterById(costCenter.getId());
    }

    /**
     * 获取成本中心详情
     */
    public CostCenter getCostCenter(Long id) {
        return costCenterService.getCostCenterById(id);
    }

    /**
     * 删除成本中心
     */
    public boolean deleteCostCenter(Long id) {
        return costCenterService.deleteCostCenter(id);
    }

    /**
     * 获取所有成本中心
     */
    public List<CostCenter> listCostCenters() {
        return costCenterService.listCostCenters(null);
    }

    /**
     * 根据部门ID查询成本中心
     */
    public List<CostCenter> getCostCentersByDepartmentId(Long departmentId) {
        return costCenterService.listCostCentersByDepartment(departmentId);
    }

    /**
     * 根据父级ID查询成本中心
     */
    public List<CostCenter> getCostCentersByParentId(Long parentId) {
        // 父级ID查询不是CostCenterService的标准功能，需要自行实现或修改此方法
        return costCenterService.listCostCenters(null).stream()
                .filter(costCenter -> parentId.equals(costCenter.getParentId()))
                .collect(Collectors.toList());
    }

    /**
     * 检查成本中心是否存在
     */
    public boolean existsCostCenter(Long id) {
        return costCenterService.getCostCenterById(id) != null;
    }

    /**
     * 获取成本中心数量
     */
    public long countCostCenters() {
        return costCenterService.listCostCenters(null).size();
    }
} 