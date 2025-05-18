package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.common.util.excel.ExcelWriter;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.Fee;
import com.lawfirm.model.finance.enums.FeeStatusEnum;
import com.lawfirm.model.finance.enums.FeeTypeEnum;
import com.lawfirm.model.finance.mapper.FeeMapper;
import com.lawfirm.model.finance.service.FeeService;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 费用服务实现类
 */
@Slf4j
@Service("financeServiceImpl")
@RequiredArgsConstructor
public class FeeServiceImpl extends BaseServiceImpl<FeeMapper, Fee> implements FeeService {

    private final SecurityContext securityContext;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @PreAuthorize("hasPermission('fee', 'create')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "fee", allEntries = true)
    public Long createFee(Fee fee) {
        log.info("创建费用: fee={}", fee);
        fee.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        fee.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        fee.setCreateTime(LocalDateTime.now());
        fee.setUpdateTime(LocalDateTime.now());
        save(fee);
        return fee.getId();
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "fee", allEntries = true)
    public boolean updateFee(Fee fee) {
        log.info("更新费用: fee={}", fee);
        fee.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        fee.setUpdateTime(LocalDateTime.now());
        return update(fee);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'delete')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "fee", allEntries = true)
    public boolean deleteFee(Long feeId) {
        log.info("删除费用: feeId={}", feeId);
        return remove(feeId);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "#feeId")
    public Fee getFeeById(Long feeId) {
        log.info("获取费用: feeId={}", feeId);
        return getById(feeId);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "fee", allEntries = true)
    public boolean updateFeeStatus(Long feeId, FeeStatusEnum status, String remark) {
        log.info("更新费用状态: feeId={}, status={}, remark={}", feeId, status, remark);
        Fee fee = getFeeById(feeId);
        if (fee == null) {
            return false;
        }
        fee.setDescription(remark);
        fee.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        fee.setUpdateTime(LocalDateTime.now());
        return update(fee);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'approve')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "fee", allEntries = true)
    public boolean approveFee(Long feeId, boolean approved, Long approverId, String remark) {
        log.info("审批费用: feeId={}, approved={}, approverId={}, remark={}", feeId, approved, approverId, remark);
        Fee fee = getFeeById(feeId);
        if (fee == null) {
            return false;
        }
        fee.setDescription(remark + "(由ID:" + approverId + "审批)");
        fee.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        fee.setUpdateTime(LocalDateTime.now());
        return update(fee);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'pay')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "fee", allEntries = true)
    public boolean payFee(Long feeId, Long accountId, LocalDateTime paymentTime, String remark) {
        log.info("支付费用: feeId={}, accountId={}, paymentTime={}, remark={}", feeId, accountId, paymentTime, remark);
        Fee fee = getFeeById(feeId);
        if (fee == null) {
            return false;
        }
        fee.setDescription(remark + "(支付时间:" + paymentTime + ",账户ID:" + accountId + ")");
        fee.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        fee.setUpdateTime(LocalDateTime.now());
        return update(fee);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'cancel')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "fee", allEntries = true)
    public boolean cancelFee(Long feeId, String reason) {
        log.info("取消费用: feeId={}, reason={}", feeId, reason);
        Fee fee = getFeeById(feeId);
        if (fee == null) {
            return false;
        }
        fee.setDescription(reason + "(已取消)");
        fee.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        fee.setUpdateTime(LocalDateTime.now());
        return update(fee);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'list:' + #feeType + ':' + #status + ':' + #departmentId + ':' + #costCenterId + ':' + #startTime + ':' + #endTime")
    public List<Fee> listFees(FeeTypeEnum feeType, FeeStatusEnum status, Long departmentId, Long costCenterId,
                             LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询费用列表: feeType={}, status={}, departmentId={}, costCenterId={}, startTime={}, endTime={}", 
                feeType, status, departmentId, costCenterId, startTime, endTime);
        LambdaQueryWrapper<Fee> wrapper = new LambdaQueryWrapper<>();
        if (feeType != null) {
            wrapper.eq(Fee::getFeeType, feeType);
        }
        if (departmentId != null) {
            wrapper.eq(Fee::getDepartmentId, departmentId);
        }
        if (startTime != null) {
            wrapper.ge(Fee::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Fee::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Fee::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'page:' + #page.current + ':' + #page.size + ':' + #feeType + ':' + #status + ':' + #departmentId + ':' + #costCenterId + ':' + #startTime + ':' + #endTime")
    public IPage<Fee> pageFees(IPage<Fee> page, FeeTypeEnum feeType, FeeStatusEnum status,
                              Long departmentId, Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("分页查询费用: page={}, feeType={}, status={}, departmentId={}, costCenterId={}, startTime={}, endTime={}", 
                page, feeType, status, departmentId, costCenterId, startTime, endTime);
        LambdaQueryWrapper<Fee> wrapper = new LambdaQueryWrapper<>();
        if (feeType != null) {
            wrapper.eq(Fee::getFeeType, feeType);
        }
        if (departmentId != null) {
            wrapper.eq(Fee::getDepartmentId, departmentId);
        }
        if (startTime != null) {
            wrapper.ge(Fee::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Fee::getCreateTime, endTime);
        }
        wrapper.orderByDesc(Fee::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'list_by_department:' + #departmentId + ':' + #startTime + ':' + #endTime")
    public List<Fee> listFeesByDepartment(Long departmentId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按部门查询费用: departmentId={}, startTime={}, endTime={}", departmentId, startTime, endTime);
        return listFees(null, null, departmentId, null, startTime, endTime);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'list_by_cost_center:' + #costCenterId + ':' + #startTime + ':' + #endTime")
    public List<Fee> listFeesByCostCenter(Long costCenterId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按成本中心查询费用: costCenterId={}, startTime={}, endTime={}", costCenterId, startTime, endTime);
        return java.util.Collections.emptyList();
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'list_by_case:' + #caseId")
    public List<Fee> listFeesByCase(Long caseId) {
        log.info("按案件查询费用: caseId={}", caseId);
        return list(new LambdaQueryWrapper<Fee>()
                .eq(Fee::getCaseId, caseId)
                .orderByDesc(Fee::getCreateTime));
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'sum_amount:' + #feeType + ':' + #status + ':' + #departmentId + ':' + #costCenterId + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumFeeAmount(FeeTypeEnum feeType, FeeStatusEnum status, Long departmentId, Long costCenterId,
                                 LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计费用金额: feeType={}, status={}, departmentId={}, costCenterId={}, startTime={}, endTime={}", 
                feeType, status, departmentId, costCenterId, startTime, endTime);
        List<Fee> fees = listFees(feeType, status, departmentId, costCenterId, startTime, endTime);
        return fees.stream()
                .map(Fee::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'sum_department_amount:' + #departmentId + ':' + #feeType + ':' + #status + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumDepartmentFeeAmount(Long departmentId, FeeTypeEnum feeType, FeeStatusEnum status,
                                           LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计部门费用金额: departmentId={}, feeType={}, status={}, startTime={}, endTime={}", 
                departmentId, feeType, status, startTime, endTime);
        return sumFeeAmount(feeType, status, departmentId, null, startTime, endTime);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'sum_cost_center_amount:' + #costCenterId + ':' + #feeType + ':' + #status + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumCostCenterFeeAmount(Long costCenterId, FeeTypeEnum feeType, FeeStatusEnum status,
                                           LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计成本中心费用金额: costCenterId={}, feeType={}, status={}, startTime={}, endTime={}", 
                costCenterId, feeType, status, startTime, endTime);
        return BigDecimal.ZERO;
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'view')")
    @Cacheable(value = "fee", key = "'sum_case_amount:' + #caseId + ':' + #feeType + ':' + #status")
    public BigDecimal sumCaseFeeAmount(Long caseId, FeeTypeEnum feeType, FeeStatusEnum status) {
        log.info("统计案件费用金额: caseId={}, feeType={}, status={}", caseId, feeType, status);
        
        // 构建查询条件
        LambdaQueryWrapper<Fee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Fee::getCaseId, caseId);
        if (feeType != null) {
            wrapper.eq(Fee::getFeeType, feeType);
        }
        // 由于Fee实体中没有status字段，这里忽略status条件
        
        // 执行查询并汇总金额
        List<Fee> fees = list(wrapper);
        return fees.stream()
                .map(Fee::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('fee', 'export')")
    public String exportFees(List<Long> feeIds) {
        log.info("导出费用数据: feeIds={}", feeIds);
        
        // 查询费用记录
        List<Fee> fees;
        if (feeIds != null && !feeIds.isEmpty()) {
            fees = listByIds(feeIds);
        } else {
            // 如果没有指定ID，则获取所有记录
            fees = list();
        }
        
        if (fees.isEmpty()) {
            log.warn("没有找到要导出的费用记录");
            return null;
        }
        
        // 准备Excel数据
        List<List<String>> excelData = new ArrayList<>();
        
        // 添加表头
        List<String> header = new ArrayList<>();
        header.add("费用ID");
        header.add("费用编号");
        header.add("费用类型");
        header.add("费用名称");
        header.add("费用金额");
        header.add("币种");
        header.add("费用发生时间");
        header.add("案件ID");
        header.add("客户ID");
        header.add("律师ID");
        header.add("费用说明");
        header.add("部门ID");
        header.add("创建时间");
        header.add("更新时间");
        excelData.add(header);
        
        // 添加数据行
        for (Fee fee : fees) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(fee.getId()));
            row.add(fee.getFeeNumber());
            row.add(fee.getFeeType() != null ? fee.getFeeType().toString() : "");
            row.add(fee.getFeeName());
            row.add(fee.getAmount() != null ? fee.getAmount().toString() : "0");
            row.add(fee.getCurrency() != null ? fee.getCurrency().toString() : "");
            row.add(fee.getFeeTime() != null ? fee.getFeeTime().format(DATE_FORMATTER) : "");
            row.add(fee.getCaseId() != null ? String.valueOf(fee.getCaseId()) : "");
            row.add(fee.getClientId() != null ? String.valueOf(fee.getClientId()) : "");
            row.add(fee.getLawyerId() != null ? String.valueOf(fee.getLawyerId()) : "");
            row.add(fee.getDescription() != null ? fee.getDescription() : "");
            row.add(fee.getDepartmentId() != null ? String.valueOf(fee.getDepartmentId()) : "");
            row.add(fee.getCreateTime() != null ? fee.getCreateTime().format(DATE_FORMATTER) : "");
            row.add(fee.getUpdateTime() != null ? fee.getUpdateTime().format(DATE_FORMATTER) : "");
            excelData.add(row);
        }
        
        // 生成临时文件名
        String fileName = "fees_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx";
        String filePath = System.getProperty("java.io.tmpdir") + File.separator + fileName;
        
        try {
            // 将数据写入文件
            FileOutputStream outputStream = new FileOutputStream(filePath);
            ExcelWriter.write(excelData, outputStream);
            outputStream.close();
            
            // 返回文件路径
            return filePath;
        } catch (IOException e) {
            log.error("导出费用记录失败", e);
            return null;
        }
    }
}