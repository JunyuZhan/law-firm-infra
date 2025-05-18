package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.util.excel.ExcelWriter;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.Transaction;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;
import com.lawfirm.model.finance.mapper.TransactionMapper;
import com.lawfirm.model.finance.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 财务交易服务实现类
 */
@Slf4j
@Service("financeTransactionServiceImpl")
@ConditionalOnProperty(name = "law-firm.storage.enabled", havingValue = "true", matchIfMissing = true)
public class TransactionServiceImpl extends BaseServiceImpl<TransactionMapper, Transaction>
        implements TransactionService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTransaction(Transaction transaction) {
        log.info("创建交易记录: transaction={}", transaction);
        transaction.setCreateTime(LocalDateTime.now());
        transaction.setUpdateTime(LocalDateTime.now());
        save(transaction);
        return transaction.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTransaction(Transaction transaction) {
        log.info("更新交易记录: transaction={}", transaction);
        transaction.setUpdateTime(LocalDateTime.now());
        return update(transaction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteTransaction(Long transactionId) {
        log.info("删除交易记录: transactionId={}", transactionId);
        return remove(transactionId);
    }

    @Override
    public Transaction getTransactionById(Long transactionId) {
        log.info("获取交易记录: transactionId={}", transactionId);
        return getById(transactionId);
    }

    @Override
    public List<Transaction> listTransactions(TransactionTypeEnum transactionType, Long accountId,
                                            LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询交易记录列表: transactionType={}, accountId={}, startTime={}, endTime={}",
                transactionType, accountId, startTime, endTime);
        LambdaQueryWrapper<Transaction> wrapper = buildTransactionWrapper(transactionType, accountId, startTime, endTime);
        return list(wrapper);
    }

    @Override
    public IPage<Transaction> pageTransactions(IPage<Transaction> page, TransactionTypeEnum transactionType,
                                             Long accountId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("分页查询交易记录: page={}, transactionType={}, accountId={}, startTime={}, endTime={}",
                page, transactionType, accountId, startTime, endTime);
        LambdaQueryWrapper<Transaction> wrapper = buildTransactionWrapper(transactionType, accountId, startTime, endTime);
        return page(page, wrapper);
    }

    @Override
    public List<Transaction> listTransactionsByAccount(Long accountId, LocalDateTime startTime, LocalDateTime endTime) {
        return listTransactions(null, accountId, startTime, endTime);
    }

    @Override
    public IPage<Transaction> pageTransactionsByAccount(IPage<Transaction> page, Long accountId,
                                                      LocalDateTime startTime, LocalDateTime endTime) {
        return pageTransactions(page, null, accountId, startTime, endTime);
    }

    @Override
    public List<Transaction> listTransactionsByContract(Long contractId) {
        log.info("查询合同相关交易记录: contractId={}", contractId);
        LambdaQueryWrapper<Transaction> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Transaction::getBusinessId, contractId)
               .eq(Transaction::getBusinessType, "CONTRACT");
        return list(wrapper);
    }

    @Override
    public List<Transaction> listTransactionsByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("查询客户相关交易记录: clientId={}, startTime={}, endTime={}", clientId, startTime, endTime);
        LambdaQueryWrapper<Transaction> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Transaction::getBusinessId, clientId)
               .eq(Transaction::getBusinessType, "CLIENT");
        if (startTime != null) {
            wrapper.ge(Transaction::getTransactionTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Transaction::getTransactionTime, endTime);
        }
        return list(wrapper);
    }

    @Override
    public BigDecimal sumTransactionAmount(TransactionTypeEnum transactionType, Long accountId,
                                         LocalDateTime startTime, LocalDateTime endTime) {
        log.info("计算交易金额: transactionType={}, accountId={}, startTime={}, endTime={}",
                transactionType, accountId, startTime, endTime);
        List<Transaction> transactions = listTransactions(transactionType, accountId, startTime, endTime);
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal sumAccountTransactionAmount(Long accountId, TransactionTypeEnum transactionType,
                                                LocalDateTime startTime, LocalDateTime endTime) {
        return sumTransactionAmount(transactionType, accountId, startTime, endTime);
    }

    @Override
    public String exportTransactions(List<Long> transactionIds) {
        log.info("导出交易记录: transactionIds={}", transactionIds);
        
        // 查询交易记录
        List<Transaction> transactions;
        if (!CollectionUtils.isEmpty(transactionIds)) {
            transactions = listByIds(transactionIds);
        } else {
            // 如果没有指定ID，则获取所有记录
            transactions = list();
        }
        
        if (transactions.isEmpty()) {
            log.warn("没有找到要导出的交易记录");
            return null;
        }
        
        // 准备Excel数据
        List<List<String>> excelData = new ArrayList<>();
        
        // 添加表头
        List<String> header = new ArrayList<>();
        header.add("交易ID");
        header.add("交易编号");
        header.add("交易类型");
        header.add("交易金额");
        header.add("币种");
        header.add("交易时间");
        header.add("付款账户ID");
        header.add("收款账户ID");
        header.add("业务ID");
        header.add("业务类型");
        header.add("交易摘要");
        header.add("交易备注");
        header.add("部门ID");
        header.add("创建时间");
        header.add("更新时间");
        excelData.add(header);
        
        // 添加数据行
        for (Transaction transaction : transactions) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(transaction.getId()));
            row.add(transaction.getTransactionNumber());
            row.add(transaction.getTransactionType() != null ? transaction.getTransactionType().toString() : "");
            row.add(transaction.getAmount() != null ? transaction.getAmount().toString() : "0");
            row.add(transaction.getCurrency() != null ? transaction.getCurrency().toString() : "");
            row.add(transaction.getTransactionTime() != null ? transaction.getTransactionTime().format(DATE_FORMATTER) : "");
            row.add(transaction.getFromAccountId() != null ? String.valueOf(transaction.getFromAccountId()) : "");
            row.add(transaction.getToAccountId() != null ? String.valueOf(transaction.getToAccountId()) : "");
            row.add(transaction.getBusinessId() != null ? String.valueOf(transaction.getBusinessId()) : "");
            row.add(transaction.getBusinessType());
            row.add(transaction.getSummary());
            row.add(transaction.getRemark());
            row.add(transaction.getDepartmentId() != null ? String.valueOf(transaction.getDepartmentId()) : "");
            row.add(transaction.getCreateTime() != null ? transaction.getCreateTime().format(DATE_FORMATTER) : "");
            row.add(transaction.getUpdateTime() != null ? transaction.getUpdateTime().format(DATE_FORMATTER) : "");
            excelData.add(row);
        }
        
        // 生成临时文件名
        String fileName = "transactions_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx";
        String filePath = System.getProperty("java.io.tmpdir") + File.separator + fileName;
        
        try {
            // 将数据写入文件
            FileOutputStream outputStream = new FileOutputStream(filePath);
            ExcelWriter.write(excelData, outputStream);
            outputStream.close();
            
            // 返回文件路径
            return filePath;
        } catch (IOException e) {
            log.error("导出交易记录失败", e);
            return null;
        }
    }

    /**
     * 构建交易查询条件
     */
    private LambdaQueryWrapper<Transaction> buildTransactionWrapper(TransactionTypeEnum transactionType,
                                                                  Long accountId,
                                                                  LocalDateTime startTime,
                                                                  LocalDateTime endTime) {
        LambdaQueryWrapper<Transaction> wrapper = new LambdaQueryWrapper<>();
        if (transactionType != null) {
            wrapper.eq(Transaction::getTransactionType, transactionType);
        }
        if (accountId != null) {
            wrapper.and(w -> w.eq(Transaction::getFromAccountId, accountId)
                            .or()
                            .eq(Transaction::getToAccountId, accountId));
        }
        if (startTime != null) {
            wrapper.ge(Transaction::getTransactionTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(Transaction::getTransactionTime, endTime);
        }
        wrapper.orderByDesc(Transaction::getTransactionTime);
        return wrapper;
    }
}