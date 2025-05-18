package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.common.util.excel.ExcelWriter;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 财务成本中心服务实现类
 */
@Slf4j
@Service("financeCostCenterServiceImpl")
@RequiredArgsConstructor
public class CostCenterServiceImpl extends BaseServiceImpl<CostCenterMapper, CostCenter> implements CostCenterService {

    private final SecurityContext securityContext;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
        
        // 查询成本中心记录
        List<CostCenter> costCenters;
        if (costCenterIds != null && !costCenterIds.isEmpty()) {
            costCenters = listByIds(costCenterIds);
        } else {
            // 如果没有指定ID，则获取所有记录
            costCenters = list();
        }
        
        if (costCenters.isEmpty()) {
            log.warn("没有找到要导出的成本中心");
            return null;
        }
        
        // 准备Excel数据
        List<List<String>> excelData = new ArrayList<>();
        
        // 添加表头
        List<String> header = new ArrayList<>();
        header.add("成本中心ID");
        header.add("成本中心编号");
        header.add("成本中心名称");
        header.add("上级成本中心ID");
        header.add("成本中心层级");
        header.add("成本中心路径");
        header.add("负责人ID");
        header.add("部门ID");
        header.add("说明");
        header.add("创建时间");
        header.add("更新时间");
        excelData.add(header);
        
        // 添加数据行
        for (CostCenter costCenter : costCenters) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(costCenter.getId()));
            row.add(costCenter.getCostCenterNumber());
            row.add(costCenter.getCostCenterName());
            row.add(costCenter.getParentId() != null ? String.valueOf(costCenter.getParentId()) : "");
            row.add(costCenter.getLevel() != null ? String.valueOf(costCenter.getLevel()) : "1");
            row.add(costCenter.getPath() != null ? costCenter.getPath() : "");
            row.add(costCenter.getManagerId() != null ? String.valueOf(costCenter.getManagerId()) : "");
            row.add(costCenter.getDepartmentId() != null ? String.valueOf(costCenter.getDepartmentId()) : "");
            row.add(costCenter.getDescription() != null ? costCenter.getDescription() : "");
            row.add(costCenter.getCreateTime() != null ? costCenter.getCreateTime().format(DATE_FORMATTER) : "");
            row.add(costCenter.getUpdateTime() != null ? costCenter.getUpdateTime().format(DATE_FORMATTER) : "");
            excelData.add(row);
        }
        
        // 生成临时文件名
        String fileName = "cost_centers_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx";
        String filePath = System.getProperty("java.io.tmpdir") + File.separator + fileName;
        
        try {
            // 将数据写入文件
            FileOutputStream outputStream = new FileOutputStream(filePath);
            ExcelWriter.write(excelData, outputStream);
            outputStream.close();
            
            // 返回文件路径
            return filePath;
        } catch (IOException e) {
            log.error("导出成本中心失败", e);
            return null;
        }
    }
}