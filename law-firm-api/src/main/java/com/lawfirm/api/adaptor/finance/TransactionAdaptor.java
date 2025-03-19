package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.transaction.TransactionCreateDTO;
import com.lawfirm.model.finance.dto.transaction.TransactionUpdateDTO;
import com.lawfirm.model.finance.entity.Transaction;
import com.lawfirm.model.finance.service.TransactionService;
import com.lawfirm.model.finance.vo.transaction.TransactionVO;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 交易管理适配器
 */
@Component
public class TransactionAdaptor extends BaseAdaptor {

    @Autowired
    private TransactionService transactionService;

    /**
     * 创建交易
     */
    public TransactionVO createTransaction(TransactionCreateDTO dto) {
        Transaction transaction = transactionService.createTransaction(dto);
        return convert(transaction, TransactionVO.class);
    }

    /**
     * 更新交易
     */
    public TransactionVO updateTransaction(Long id, TransactionUpdateDTO dto) {
        Transaction transaction = transactionService.updateTransaction(id, dto);
        return convert(transaction, TransactionVO.class);
    }

    /**
     * 获取交易详情
     */
    public TransactionVO getTransaction(Long id) {
        Transaction transaction = transactionService.getTransaction(id);
        return convert(transaction, TransactionVO.class);
    }

    /**
     * 删除交易
     */
    public void deleteTransaction(Long id) {
        transactionService.deleteTransaction(id);
    }

    /**
     * 获取所有交易
     */
    public List<TransactionVO> listTransactions() {
        List<Transaction> transactions = transactionService.listTransactions();
        return transactions.stream()
                .map(transaction -> convert(transaction, TransactionVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据交易类型查询交易
     */
    public List<TransactionVO> getTransactionsByType(TransactionTypeEnum type) {
        List<Transaction> transactions = transactionService.getTransactionsByType(type);
        return transactions.stream()
                .map(transaction -> convert(transaction, TransactionVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据账户ID查询交易
     */
    public List<TransactionVO> getTransactionsByAccountId(Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return transactions.stream()
                .map(transaction -> convert(transaction, TransactionVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 检查交易是否存在
     */
    public boolean existsTransaction(Long id) {
        return transactionService.existsTransaction(id);
    }

    /**
     * 获取交易数量
     */
    public long countTransactions() {
        return transactionService.countTransactions();
    }
} 