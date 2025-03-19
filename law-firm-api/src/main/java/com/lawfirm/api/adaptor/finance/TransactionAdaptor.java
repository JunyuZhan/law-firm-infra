package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.transaction.TransactionCreateDTO;
import com.lawfirm.model.finance.dto.transaction.TransactionUpdateDTO;
import com.lawfirm.model.finance.entity.Transaction;
import com.lawfirm.model.finance.service.TransactionService;
import com.lawfirm.model.finance.vo.transaction.TransactionDetailVO;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    public TransactionDetailVO createTransaction(TransactionCreateDTO dto) {
        Transaction transaction = convert(dto, Transaction.class);
        Long id = transactionService.createTransaction(transaction);
        return getTransaction(id);
    }

    /**
     * 更新交易
     */
    public TransactionDetailVO updateTransaction(Long id, TransactionUpdateDTO dto) {
        Transaction transaction = convert(dto, Transaction.class);
        transaction.setId(id);
        transactionService.updateTransaction(transaction);
        return getTransaction(id);
    }

    /**
     * 获取交易详情
     */
    public TransactionDetailVO getTransaction(Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return convert(transaction, TransactionDetailVO.class);
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
    public List<TransactionDetailVO> listTransactions() {
        List<Transaction> transactions = transactionService.listTransactions(null, null, null, null);
        return transactions.stream()
                .map(transaction -> convert(transaction, TransactionDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据交易类型查询交易
     */
    public List<TransactionDetailVO> getTransactionsByType(TransactionTypeEnum type) {
        List<Transaction> transactions = transactionService.listTransactions(type, null, null, null);
        return transactions.stream()
                .map(transaction -> convert(transaction, TransactionDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 根据账户ID查询交易
     */
    public List<TransactionDetailVO> getTransactionsByAccountId(Long accountId) {
        List<Transaction> transactions = transactionService.listTransactionsByAccount(accountId, null, null);
        return transactions.stream()
                .map(transaction -> convert(transaction, TransactionDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 检查交易是否存在
     */
    public boolean existsTransaction(Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return transaction != null;
    }

    /**
     * 获取交易数量
     */
    public long countTransactions() {
        List<Transaction> transactions = transactionService.listTransactions(null, null, null, null);
        return transactions.size();
    }
} 