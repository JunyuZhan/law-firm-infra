package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.account.AccountCreateDTO;
import com.lawfirm.model.finance.dto.account.AccountUpdateDTO;
import com.lawfirm.model.finance.entity.Account;
import com.lawfirm.model.finance.entity.Transaction;
import com.lawfirm.model.finance.service.AccountService;
import com.lawfirm.model.finance.vo.account.AccountVO;
import com.lawfirm.model.finance.vo.transaction.TransactionDetailVO;
import com.lawfirm.model.finance.enums.AccountStatusEnum;
import com.lawfirm.model.finance.enums.AccountTypeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 账户管理适配器
 */
@Component
public class AccountAdaptor extends BaseAdaptor {

    @Autowired
    private AccountService accountService;

    /**
     * 创建账户
     */
    public AccountVO createAccount(AccountCreateDTO dto) {
        Account account = convert(dto, Account.class);
        Long accountId = accountService.createAccount(account);
        account.setId(accountId);
        return convert(account, AccountVO.class);
    }

    /**
     * 更新账户
     */
    public AccountVO updateAccount(Long id, AccountUpdateDTO dto) {
        Account account = convert(dto, Account.class);
        account.setId(id);
        boolean success = accountService.updateAccount(account);
        if (success) {
            Account updatedAccount = accountService.getAccountById(id);
            return convert(updatedAccount, AccountVO.class);
        }
        return null;
    }

    /**
     * 获取账户详情
     */
    public AccountVO getAccount(Long id) {
        Account account = accountService.getAccountById(id);
        return convert(account, AccountVO.class);
    }

    /**
     * 删除账户
     */
    public void deleteAccount(Long id) {
        accountService.deleteAccount(id);
    }

    /**
     * 获取所有账户
     */
    public List<AccountVO> listAccounts() {
        List<Account> accounts = accountService.listAccounts(null);
        return accounts.stream()
                .map(account -> convert(account, AccountVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 获取账户列表（按状态筛选）
     */
    public List<AccountVO> listAccountsByStatus(Integer status) {
        List<Account> accounts = accountService.listAccounts(status);
        return accounts.stream()
                .map(account -> convert(account, AccountVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 分页查询账户
     */
    public IPage<AccountVO> pageAccounts(Integer page, Integer size, Integer status) {
        Page<Account> pageParam = new Page<>(page, size);
        IPage<Account> accountPage = accountService.pageAccounts(pageParam, status);
        
        return accountPage.convert(account -> convert(account, AccountVO.class));
    }

    /**
     * 增加账户余额
     */
    public boolean increaseBalance(Long accountId, BigDecimal amount, String remark) {
        return accountService.increaseBalance(accountId, amount, remark);
    }

    /**
     * 减少账户余额
     */
    public boolean decreaseBalance(Long accountId, BigDecimal amount, String remark) {
        return accountService.decreaseBalance(accountId, amount, remark);
    }

    /**
     * 冻结账户金额
     */
    public boolean freezeAmount(Long accountId, BigDecimal amount, String remark) {
        return accountService.freezeAmount(accountId, amount, remark);
    }

    /**
     * 解冻账户金额
     */
    public boolean unfreezeAmount(Long accountId, BigDecimal amount, String remark) {
        return accountService.unfreezeAmount(accountId, amount, remark);
    }

    /**
     * 账户间转账
     */
    public Long transfer(Long fromAccountId, Long toAccountId, BigDecimal amount, String remark) {
        return accountService.transfer(fromAccountId, toAccountId, amount, remark);
    }

    /**
     * 查询账户交易记录
     */
    public List<TransactionDetailVO> listAccountTransactions(Long accountId, String startTime, String endTime) {
        List<Transaction> transactions = accountService.listAccountTransactions(accountId, startTime, endTime);
        return transactions.stream()
                .map(transaction -> convert(transaction, TransactionDetailVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 分页查询账户交易记录
     */
    public IPage<TransactionDetailVO> pageAccountTransactions(Integer page, Integer size, Long accountId, 
                                                       String startTime, String endTime) {
        Page<Transaction> pageParam = new Page<>(page, size);
        IPage<Transaction> transactionPage = accountService.pageAccountTransactions(pageParam, accountId, startTime, endTime);
        
        return transactionPage.convert(transaction -> convert(transaction, TransactionDetailVO.class));
    }

    /**
     * 更新账户状态（兼容旧接口）
     */
    public void updateAccountStatus(Long id, AccountStatusEnum status) {
        Account account = accountService.getAccountById(id);
        if (account != null) {
            account.setAccountStatus(status);
            accountService.updateAccount(account);
        }
    }

    /**
     * 根据账户类型查询账户（兼容旧接口）
     */
    public List<AccountVO> getAccountsByType(AccountTypeEnum type) {
        List<Account> accounts = accountService.listAccounts(null);
        return accounts.stream()
                .filter(account -> account.getAccountType() == type)
                .map(account -> convert(account, AccountVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 检查账户是否存在
     */
    public boolean existsAccount(Long id) {
        return accountService.getAccountById(id) != null;
    }

    /**
     * 获取账户数量
     */
    public long countAccounts() {
        List<Account> accounts = accountService.listAccounts(null);
        return accounts.size();
    }
} 