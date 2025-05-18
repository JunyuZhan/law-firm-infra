package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.common.util.excel.ExcelWriter;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.BillingRecord;
import com.lawfirm.model.finance.enums.BillingStatusEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import com.lawfirm.model.finance.mapper.BillingRecordMapper;
import com.lawfirm.model.finance.service.BillingRecordService;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 财务账单记录服务实现类
 */
@Slf4j
@Service("financeBillingRecordServiceImpl")
@RequiredArgsConstructor
public class BillingRecordServiceImpl extends BaseServiceImpl<BillingRecordMapper, BillingRecord> implements BillingRecordService {

    private final SecurityContext securityContext;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @PreAuthorize("hasPermission('billing_record', 'create')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "billing_record", allEntries = true)
    public Long createBillingRecord(BillingRecord billingRecord) {
        log.info("创建账单记录: billingRecord={}", billingRecord);
        billingRecord.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        billingRecord.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        billingRecord.setCreateTime(LocalDateTime.now());
        billingRecord.setUpdateTime(LocalDateTime.now());
        save(billingRecord);
        return billingRecord.getId();
    }

    @Override
    @PreAuthorize("hasPermission('billing_record', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "billing_record", allEntries = true)
    public boolean updateBillingRecord(BillingRecord billingRecord) {
        log.info("更新账单记录: billingRecord={}", billingRecord);
        billingRecord.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        billingRecord.setUpdateTime(LocalDateTime.now());
        return update(billingRecord);
    }

    @Override
    @PreAuthorize("hasPermission('billing_record', 'delete')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "billing_record", allEntries = true)
    public boolean deleteBillingRecord(Long billingRecordId) {
        log.info("删除账单记录: billingRecordId={}", billingRecordId);
        return remove(billingRecordId);
    }

    @Override
    @PreAuthorize("hasPermission('billing_record', 'view')")
    @Cacheable(value = "billing_record", key = "#billingRecordId")
    public BillingRecord getBillingRecordById(Long billingRecordId) {
        log.info("获取账单记录: billingRecordId={}", billingRecordId);
        return getById(billingRecordId);
    }

    @Override
    @PreAuthorize("hasPermission('billing_record', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "billing_record", allEntries = true)
    public boolean updateBillingRecordStatus(Long billingRecordId, BillingStatusEnum status, String remark) {
        log.info("更新账单记录状态: billingRecordId={}, status={}, remark={}", billingRecordId, status, remark);
        BillingRecord billingRecord = new BillingRecord();
        billingRecord.setId(billingRecordId);
        billingRecord.setBillingStatus(status.getCode());
        billingRecord.setRemark(remark);
        billingRecord.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        billingRecord.setUpdateTime(LocalDateTime.now());
        return update(billingRecord);
    }

    @Override
    @PreAuthorize("hasPermission('billing_record', 'confirm')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "billing_record", allEntries = true)
    public boolean confirmBillingRecord(Long billingRecordId, Long operatorId, String remark) {
        log.info("确认账单记录: billingRecordId={}, operatorId={}, remark={}", billingRecordId, operatorId, remark);
        BillingRecord billingRecord = new BillingRecord();
        billingRecord.setId(billingRecordId);
        billingRecord.setBillingStatus(BillingStatusEnum.CONFIRMED.getCode());
        billingRecord.setOperatorId(operatorId);
        billingRecord.setRemark(remark);
        billingRecord.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        billingRecord.setUpdateTime(LocalDateTime.now());
        return update(billingRecord);
    }

    @Override
    @PreAuthorize("hasPermission('billing_record', 'cancel')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "billing_record", allEntries = true)
    public boolean cancelBillingRecord(Long billingRecordId, String reason) {
        log.info("取消账单记录: billingRecordId={}, reason={}", billingRecordId, reason);
        BillingRecord billingRecord = new BillingRecord();
        billingRecord.setId(billingRecordId);
        billingRecord.setBillingStatus(BillingStatusEnum.CANCELLED.getCode());
        billingRecord.setRemark(reason);
        billingRecord.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        billingRecord.setUpdateTime(LocalDateTime.now());
        return update(billingRecord);
    }

    @Override
    @PreAuthorize("hasPermission('billing_record', 'view')")
    @Cacheable(value = "billing_record", key = "'list:' + #status + ':' + #clientId + ':' + #caseId + ':' + #startTime + ':' + #endTime")
    public List<BillingRecord> listBillingRecords(BillingStatusEnum status, Long clientId, Long caseId,
                                                LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询账单记录列表: status={}, clientId={}, caseId={}, startTime={}, endTime={}", 
                status, clientId, caseId, startTime, endTime);
        LambdaQueryWrapper<BillingRecord> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(BillingRecord::getBillingStatus, status.getCode());
        }
        if (clientId != null) {
            wrapper.eq(BillingRecord::getClientId, clientId);
        }
        if (caseId != null) {
            wrapper.eq(BillingRecord::getCaseId, caseId);
        }
        if (startTime != null) {
            wrapper.ge(BillingRecord::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(BillingRecord::getCreateTime, endTime);
        }
        wrapper.orderByDesc(BillingRecord::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('billing_record', 'view')")
    @Cacheable(value = "billing_record", key = "'page:' + #page.current + ':' + #page.size + ':' + #status + ':' + #clientId + ':' + #caseId + ':' + #startTime + ':' + #endTime")
    public IPage<BillingRecord> pageBillingRecords(IPage<BillingRecord> page, BillingStatusEnum status,
                                                 Long clientId, Long caseId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("分页查询账单记录: page={}, status={}, clientId={}, caseId={}, startTime={}, endTime={}", 
                page, status, clientId, caseId, startTime, endTime);
        LambdaQueryWrapper<BillingRecord> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(BillingRecord::getBillingStatus, status.getCode());
        }
        if (clientId != null) {
            wrapper.eq(BillingRecord::getClientId, clientId);
        }
        if (caseId != null) {
            wrapper.eq(BillingRecord::getCaseId, caseId);
        }
        if (startTime != null) {
            wrapper.ge(BillingRecord::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(BillingRecord::getCreateTime, endTime);
        }
        wrapper.orderByDesc(BillingRecord::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('billing_record', 'view')")
    @Cacheable(value = "billing_record", key = "'list_by_client:' + #clientId + ':' + #startTime + ':' + #endTime")
    public List<BillingRecord> listBillingRecordsByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("按客户查询账单记录: clientId={}, startTime={}, endTime={}", clientId, startTime, endTime);
        LambdaQueryWrapper<BillingRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BillingRecord::getClientId, clientId);
        if (startTime != null) {
            wrapper.ge(BillingRecord::getCreateTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(BillingRecord::getCreateTime, endTime);
        }
        wrapper.orderByDesc(BillingRecord::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('billing_record', 'view')")
    @Cacheable(value = "billing_record", key = "'list_by_case:' + #caseId")
    public List<BillingRecord> listBillingRecordsByCase(Long caseId) {
        log.info("按案件查询账单记录: caseId={}", caseId);
        return list(new LambdaQueryWrapper<BillingRecord>()
                .eq(BillingRecord::getCaseId, caseId)
                .orderByDesc(BillingRecord::getCreateTime));
    }

    @Override
    @PreAuthorize("hasPermission('billing_record', 'view')")
    @Cacheable(value = "billing_record", key = "'sum_amount:' + #status + ':' + #clientId + ':' + #caseId + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumBillingAmount(BillingStatusEnum status, Long clientId, Long caseId,
                                     LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计账单金额: status={}, clientId={}, caseId={}, startTime={}, endTime={}", 
                status, clientId, caseId, startTime, endTime);
        List<BillingRecord> billingRecords = listBillingRecords(status, clientId, caseId, startTime, endTime);
        return billingRecords.stream()
                .map(BillingRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('billing_record', 'view')")
    @Cacheable(value = "billing_record", key = "'sum_client_amount:' + #clientId + ':' + #status + ':' + #startTime + ':' + #endTime")
    public BigDecimal sumClientBillingAmount(Long clientId, BillingStatusEnum status,
                                           LocalDateTime startTime, LocalDateTime endTime) {
        log.info("统计客户账单金额: clientId={}, status={}, startTime={}, endTime={}", 
                clientId, status, startTime, endTime);
        List<BillingRecord> billingRecords = listBillingRecords(status, clientId, null, startTime, endTime);
        return billingRecords.stream()
                .map(BillingRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('billing_record', 'view')")
    @Cacheable(value = "billing_record", key = "'sum_case_amount:' + #caseId + ':' + #status")
    public BigDecimal sumCaseBillingAmount(Long caseId, BillingStatusEnum status) {
        log.info("统计案件账单金额: caseId={}, status={}", caseId, status);
        List<BillingRecord> billingRecords = listBillingRecords(status, null, caseId, null, null);
        return billingRecords.stream()
                .map(BillingRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @PreAuthorize("hasPermission('billing_record', 'export')")
    public String exportBillingRecords(List<Long> billingRecordIds) {
        log.info("导出账单记录数据: billingRecordIds={}", billingRecordIds);
        
        // 查询账单记录
        List<BillingRecord> records;
        if (billingRecordIds != null && !billingRecordIds.isEmpty()) {
            records = listByIds(billingRecordIds);
        } else {
            // 如果没有指定ID，则获取所有记录
            records = list();
        }
        
        if (records.isEmpty()) {
            log.warn("没有找到要导出的账单记录");
            return null;
        }
        
        // 准备Excel数据
        List<List<String>> excelData = new ArrayList<>();
        
        // 添加表头
        List<String> header = new ArrayList<>();
        header.add("账单ID");
        header.add("客户ID");
        header.add("案件ID");
        header.add("账单编号");
        header.add("账单说明");
        header.add("账单金额");
        header.add("币种");
        header.add("账单状态");
        header.add("账单日期");
        header.add("付款截止日期");
        header.add("已付金额");
        header.add("未付金额");
        header.add("创建时间");
        header.add("更新时间");
        header.add("备注");
        excelData.add(header);
        
        // 添加数据行
        for (BillingRecord record : records) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(record.getId()));
            row.add(String.valueOf(record.getClientId()));
            row.add(record.getCaseId() != null ? String.valueOf(record.getCaseId()) : "");
            row.add(record.getBillingNumber());
            row.add(record.getDescription() != null ? record.getDescription() : "");
            row.add(record.getAmount().toString());
            row.add(record.getCurrency());
            row.add(BillingStatusEnum.getByCode(record.getBillingStatus()).getDesc());
            row.add(record.getBillingDate() != null ? record.getBillingDate().format(DATE_FORMATTER) : "");
            row.add(record.getDueDate() != null ? record.getDueDate().format(DATE_FORMATTER) : "");
            row.add(record.getPaidAmount() != null ? record.getPaidAmount().toString() : "0");
            row.add(record.getUnpaidAmount() != null ? record.getUnpaidAmount().toString() : "0");
            row.add(record.getCreateTime() != null ? record.getCreateTime().format(DATE_FORMATTER) : "");
            row.add(record.getUpdateTime() != null ? record.getUpdateTime().format(DATE_FORMATTER) : "");
            row.add(record.getRemark());
            excelData.add(row);
        }
        
        // 生成临时文件名
        String fileName = "billing_records_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx";
        String filePath = System.getProperty("java.io.tmpdir") + File.separator + fileName;
        
        try {
            // 将数据写入文件
            FileOutputStream outputStream = new FileOutputStream(filePath);
            ExcelWriter.write(excelData, outputStream);
            outputStream.close();
            
            // 返回文件路径
            return filePath;
        } catch (IOException e) {
            log.error("导出账单记录失败", e);
            return null;
        }
    }
} 