package com.lawfirm.model.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.finance.entity.CostCenter;

import java.util.List;

/**
 * 成本中心服务接口
 */
public interface CostCenterService {
    
    /**
     * 创建成本中心
     *
     * @param costCenter 成本中心信息
     * @return 成本中心ID
     */
    Long createCostCenter(CostCenter costCenter);
    
    /**
     * 更新成本中心
     *
     * @param costCenter 成本中心信息
     * @return 是否更新成功
     */
    boolean updateCostCenter(CostCenter costCenter);
    
    /**
     * 删除成本中心
     *
     * @param costCenterId 成本中心ID
     * @return 是否删除成功
     */
    boolean deleteCostCenter(Long costCenterId);
    
    /**
     * 获取成本中心详情
     *
     * @param costCenterId 成本中心ID
     * @return 成本中心信息
     */
    CostCenter getCostCenterById(Long costCenterId);
    
    /**
     * 查询成本中心列表
     *
     * @param departmentId 部门ID，可为null
     * @return 成本中心列表
     */
    List<CostCenter> listCostCenters(Long departmentId);
    
    /**
     * 分页查询成本中心
     *
     * @param page 分页参数
     * @param departmentId 部门ID，可为null
     * @return 分页成本中心信息
     */
    IPage<CostCenter> pageCostCenters(IPage<CostCenter> page, Long departmentId);
    
    /**
     * 按部门查询成本中心
     *
     * @param departmentId 部门ID
     * @return 成本中心列表
     */
    List<CostCenter> listCostCentersByDepartment(Long departmentId);
    
    /**
     * 导出成本中心数据
     *
     * @param costCenterIds 成本中心ID列表
     * @return 导出文件路径
     */
    String exportCostCenters(List<Long> costCenterIds);
} 