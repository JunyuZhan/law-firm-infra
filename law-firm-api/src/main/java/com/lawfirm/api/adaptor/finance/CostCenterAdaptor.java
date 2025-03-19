package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.costcenter.CostCenterCreateDTO;
import com.lawfirm.model.finance.dto.costcenter.CostCenterUpdateDTO;
import com.lawfirm.model.finance.entity.CostCenter;
import com.lawfirm.model.finance.service.CostCenterService;
import com.lawfirm.model.finance.vo.costcenter.CostCenterVO;
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
    public CostCenterVO createCostCenter(CostCenterCreateDTO dto) {
        CostCenter costCenter = costCenterService.createCostCenter(dto);
        return convert(costCenter, CostCenterVO.class);
    }

    /**
     * 更新成本中心
     */
    public CostCenterVO updateCostCenter(Long id, CostCenterUpdateDTO dto) {
        CostCenter costCenter = costCenterService.updateCostCenter(id, dto);
        return convert(costCenter, CostCenterVO.class);
    }

    /**
     * 获取成本中心详情
     */
    public CostCenterVO getCostCenter(Long id) {
        CostCenter costCenter = costCenterService.getCostCenter(id);
        return convert(costCenter, CostCenterVO.class);
    }

    /**
     * 删除成本中心
     */
    public void deleteCostCenter(Long id) {
        costCenterService.deleteCostCenter(id);
    }

    /**
     * 获取所有成本中心
     */
    public List<CostCenterVO> listCostCenters() {
        List<CostCenter> costCenters = costCenterService.listCostCenters();
        return costCenters.stream()
                .map(costCenter -> convert(costCenter, CostCenterVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据部门ID查询成本中心
     */
    public List<CostCenterVO> getCostCentersByDepartmentId(Long departmentId) {
        List<CostCenter> costCenters = costCenterService.getCostCentersByDepartmentId(departmentId);
        return costCenters.stream()
                .map(costCenter -> convert(costCenter, CostCenterVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据父级ID查询成本中心
     */
    public List<CostCenterVO> getCostCentersByParentId(Long parentId) {
        List<CostCenter> costCenters = costCenterService.getCostCentersByParentId(parentId);
        return costCenters.stream()
                .map(costCenter -> convert(costCenter, CostCenterVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 检查成本中心是否存在
     */
    public boolean existsCostCenter(Long id) {
        return costCenterService.existsCostCenter(id);
    }

    /**
     * 获取成本中心数量
     */
    public long countCostCenters() {
        return costCenterService.countCostCenters();
    }
} 