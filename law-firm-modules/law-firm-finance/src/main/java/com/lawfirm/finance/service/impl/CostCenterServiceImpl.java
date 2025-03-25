package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.CostCenter;
import com.lawfirm.model.finance.mapper.CostCenterMapper;
import com.lawfirm.model.finance.service.CostCenterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 财务成本中心服务实现类
 */
@Slf4j
@Service("financeCostCenterServiceImpl")
@RequiredArgsConstructor
public class CostCenterServiceImpl extends BaseServiceImpl<CostCenterMapper, CostCenter> implements CostCenterService {

    private final SecurityContext securityContext;

    @Override
    @PreAuthorize("hasPermission('cost_center', 'create')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "cost_center", allEntries = true)
    public Long createCostCenter(CostCenter costCenter) {
        log.info("创建成本中心: costCenter={}", costCenter);
        costCenter.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        costCenter.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        costCenter.setCreateTime(LocalDateTime.now());
        costCenter.setUpdateTime(LocalDateTime.now());
        save(costCenter);
        return costCenter.getId();
    }

    @Override
    @PreAuthorize("hasPermission('cost_center', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "cost_center", allEntries = true)
    public boolean updateCostCenter(CostCenter costCenter) {
        log.info("更新成本中心: costCenter={}", costCenter);
        costCenter.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        costCenter.setUpdateTime(LocalDateTime.now());
        return update(costCenter);
    }

    @Override
    @PreAuthorize("hasPermission('cost_center', 'delete')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "cost_center", allEntries = true)
    public boolean deleteCostCenter(Long costCenterId) {
        log.info("删除成本中心: costCenterId={}", costCenterId);
        return remove(costCenterId);
    }

    @Override
    @PreAuthorize("hasPermission('cost_center', 'view')")
    @Cacheable(value = "cost_center", key = "#costCenterId")
    public CostCenter getCostCenterById(Long costCenterId) {
        log.info("获取成本中心: costCenterId={}", costCenterId);
        return getById(costCenterId);
    }

    @Override
    @PreAuthorize("hasPermission('cost_center', 'view')")
    @Cacheable(value = "cost_center", key = "'list:' + #departmentId")
    public List<CostCenter> listCostCenters(Long departmentId) {
        log.info("查询成本中心列表: departmentId={}", departmentId);
        LambdaQueryWrapper<CostCenter> wrapper = new LambdaQueryWrapper<>();
        if (departmentId != null) {
            wrapper.eq(CostCenter::getDepartmentId, departmentId);
        }
        wrapper.orderByDesc(CostCenter::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('cost_center', 'view')")
    @Cacheable(value = "cost_center", key = "'page:' + #page.current + ':' + #page.size + ':' + #departmentId")
    public IPage<CostCenter> pageCostCenters(IPage<CostCenter> page, Long departmentId) {
        log.info("分页查询成本中心: page={}, departmentId={}", page, departmentId);
        LambdaQueryWrapper<CostCenter> wrapper = new LambdaQueryWrapper<>();
        if (departmentId != null) {
            wrapper.eq(CostCenter::getDepartmentId, departmentId);
        }
        wrapper.orderByDesc(CostCenter::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('cost_center', 'view')")
    @Cacheable(value = "cost_center", key = "'list_by_department:' + #departmentId")
    public List<CostCenter> listCostCentersByDepartment(Long departmentId) {
        log.info("按部门查询成本中心: departmentId={}", departmentId);
        return list(new LambdaQueryWrapper<CostCenter>()
                .eq(CostCenter::getDepartmentId, departmentId)
                .orderByDesc(CostCenter::getCreateTime));
    }

    @Override
    @PreAuthorize("hasPermission('cost_center', 'export')")
    public String exportCostCenters(List<Long> costCenterIds) {
        log.info("导出成本中心数据: costCenterIds={}", costCenterIds);
        // TODO: 实现导出功能
        return null;
    }
}