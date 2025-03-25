package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.Account;
import com.lawfirm.model.finance.entity.Transaction;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;
import com.lawfirm.model.finance.mapper.AccountMapper;
import com.lawfirm.model.finance.service.AccountService;
import com.lawfirm.model.finance.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 财务账户服务实现类
 */
@Slf4j
@Service("financeAccountServiceImpl")
@RequiredArgsConstructor
public class AccountServiceImpl extends BaseServiceImpl<AccountMapper, Account> implements AccountService {

    private final TransactionService transactionService;
    private final SecurityContext securityContext;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @PreAuthorize("hasPermission('account', 'create')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "account", allEntries = true)
    public Long createAccount(Account account) {
        log.info("创建账户: account={}", account);
        account.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        account.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        account.setCreateTime(LocalDateTime.now());
        account.setUpdateTime(LocalDateTime.now());
        save(account);
        return account.getId();
    }

    @Override
    @PreAuthorize("hasPermission('account', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "account", allEntries = true)
    public boolean updateAccount(Account account) {
        log.info("更新账户: account={}", account);
        account.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        account.setUpdateTime(LocalDateTime.now());
        return update(account);
    }

    @Override
    @PreAuthorize("hasPermission('account', 'delete')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "account", allEntries = true)
    public boolean deleteAccount(Long accountId) {
        log.info("删除账户: accountId={}", accountId);
        return remove(accountId);
    }

    @Override
    @PreAuthorize("hasPermission('account', 'view')")
    @Cacheable(value = "account", key = "#accountId")
    public Account getAccountById(Long accountId) {
        log.info("获取账户: accountId={}", accountId);
        return getById(accountId);
    }

    @Override
    @PreAuthorize("hasPermission('account', 'view')")
    @Cacheable(value = "account", key = "'list:' + #status")
    public List<Account> listAccounts(Integer status) {
        log.info("查询账户列表: status={}", status);
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Account::getStatus, status);
        }
        wrapper.orderByDesc(Account::getCreateTime);
        return list(wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('account', 'view')")
    @Cacheable(value = "account", key = "'page:' + #page.current + ':' + #page.size + ':' + #status")
    public IPage<Account> pageAccounts(IPage<Account> page, Integer status) {
        log.info("分页查询账户: page={}, status={}", page, status);
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            wrapper.eq(Account::getStatus, status);
        }
        wrapper.orderByDesc(Account::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    @PreAuthorize("hasPermission('account', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "account", allEntries = true)
    public boolean increaseBalance(Long accountId, BigDecimal amount, String remark) {
        log.info("增加账户余额: accountId={}, amount={}, remark={}", accountId, amount, remark);
        Account account = getAccountById(accountId);
        if (account == null) {
            return false;
        }
        BigDecimal currentBalance = account.getBalance();
        account.setBalance(currentBalance.add(amount));
        account.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        account.setUpdateTime(LocalDateTime.now());
        boolean success = update(account);
        
        // 记录交易流水
        if (success) {
            Transaction transaction = new Transaction();
            transaction.setFromAccountId(accountId);
            transaction.setAmount(amount);
            transaction.setTransactionType(TransactionTypeEnum.INCOME);
            transaction.setRemark(remark);
            transaction.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
            transaction.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
            transactionService.createTransaction(transaction);
        }
        return success;
    }

    @Override
    @PreAuthorize("hasPermission('account', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "account", allEntries = true)
    public boolean decreaseBalance(Long accountId, BigDecimal amount, String remark) {
        log.info("减少账户余额: accountId={}, amount={}, remark={}", accountId, amount, remark);
        Account account = getAccountById(accountId);
        if (account == null) {
            return false;
        }
        BigDecimal currentBalance = account.getBalance();
        if (currentBalance.compareTo(amount) < 0) {
            return false; // 余额不足
        }
        account.setBalance(currentBalance.subtract(amount));
        account.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        account.setUpdateTime(LocalDateTime.now());
        boolean success = update(account);
        
        // 记录交易流水
        if (success) {
            Transaction transaction = new Transaction();
            transaction.setFromAccountId(accountId);
            transaction.setAmount(amount);
            transaction.setTransactionType(TransactionTypeEnum.EXPENSE);
            transaction.setRemark(remark);
            transaction.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
            transaction.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
            transactionService.createTransaction(transaction);
        }
        return success;
    }

    @Override
    @PreAuthorize("hasPermission('account', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "account", allEntries = true)
    public boolean freezeAmount(Long accountId, BigDecimal amount, String remark) {
        log.info("冻结账户金额: accountId={}, amount={}, remark={}", accountId, amount, remark);
        Account account = getAccountById(accountId);
        if (account == null) {
            return false;
        }
        BigDecimal currentBalance = account.getBalance();
        BigDecimal currentFrozen = account.getFrozenAmount();
        if (currentBalance.subtract(currentFrozen).compareTo(amount) < 0) {
            return false; // 可用余额不足
        }
        account.setFrozenAmount(currentFrozen.add(amount));
        account.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        account.setUpdateTime(LocalDateTime.now());
        boolean success = update(account);
        
        // 记录交易流水
        if (success) {
            Transaction transaction = new Transaction();
            transaction.setFromAccountId(accountId);
            transaction.setAmount(amount);
            transaction.setTransactionType(TransactionTypeEnum.OTHER);
            transaction.setRemark("冻结金额: " + remark);
            transaction.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
            transaction.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
            transactionService.createTransaction(transaction);
        }
        return success;
    }

    @Override
    @PreAuthorize("hasPermission('account', 'update')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "account", allEntries = true)
    public boolean unfreezeAmount(Long accountId, BigDecimal amount, String remark) {
        log.info("解冻账户金额: accountId={}, amount={}, remark={}", accountId, amount, remark);
        Account account = getAccountById(accountId);
        if (account == null) {
            return false;
        }
        BigDecimal currentFrozen = account.getFrozenAmount();
        if (currentFrozen.compareTo(amount) < 0) {
            return false; // 冻结金额不足
        }
        account.setFrozenAmount(currentFrozen.subtract(amount));
        account.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        account.setUpdateTime(LocalDateTime.now());
        boolean success = update(account);
        
        // 记录交易流水
        if (success) {
            Transaction transaction = new Transaction();
            transaction.setFromAccountId(accountId);
            transaction.setAmount(amount);
            transaction.setTransactionType(TransactionTypeEnum.OTHER);
            transaction.setRemark("解冻金额: " + remark);
            transaction.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
            transaction.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
            transactionService.createTransaction(transaction);
        }
        return success;
    }

    @Override
    @PreAuthorize("hasPermission('account', 'transfer')")
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "account", allEntries = true)
    public Long transfer(Long fromAccountId, Long toAccountId, BigDecimal amount, String remark) {
        log.info("账户间转账: fromAccountId={}, toAccountId={}, amount={}, remark={}", 
                fromAccountId, toAccountId, amount, remark);
        
        // 检查账户是否存在
        Account fromAccount = getAccountById(fromAccountId);
        Account toAccount = getAccountById(toAccountId);
        if (fromAccount == null || toAccount == null) {
            return null;
        }
        
        // 检查余额是否充足
        BigDecimal fromBalance = fromAccount.getBalance();
        BigDecimal fromFrozen = fromAccount.getFrozenAmount();
        if (fromBalance.subtract(fromFrozen).compareTo(amount) < 0) {
            return null; // 可用余额不足
        }
        
        // 减少源账户余额
        fromAccount.setBalance(fromBalance.subtract(amount));
        fromAccount.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        fromAccount.setUpdateTime(LocalDateTime.now());
        
        // 增加目标账户余额
        BigDecimal toBalance = toAccount.getBalance();
        toAccount.setBalance(toBalance.add(amount));
        toAccount.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        toAccount.setUpdateTime(LocalDateTime.now());
        
        // 更新账户
        update(fromAccount);
        update(toAccount);
        
        // 记录交易流水
        Transaction transaction = new Transaction();
        transaction.setFromAccountId(fromAccountId);
        transaction.setToAccountId(toAccountId);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionTypeEnum.TRANSFER);
        transaction.setRemark(remark);
        transaction.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        transaction.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        transactionService.createTransaction(transaction);
        return transaction.getId();
    }

    @Override
    @PreAuthorize("hasPermission('account', 'view')")
    @Cacheable(value = "account_transaction", key = "#accountId + ':' + #startTime + ':' + #endTime")
    public List<Transaction> listAccountTransactions(Long accountId, String startTimeStr, String endTimeStr) {
        log.info("查询账户交易记录: accountId={}, startTime={}, endTime={}", accountId, startTimeStr, endTimeStr);
        
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        if (StringUtils.hasText(startTimeStr)) {
            startTime = LocalDateTime.parse(startTimeStr, DATE_TIME_FORMATTER);
        }
        if (StringUtils.hasText(endTimeStr)) {
            endTime = LocalDateTime.parse(endTimeStr, DATE_TIME_FORMATTER);
        }
        
        return transactionService.listTransactions(null, accountId, startTime, endTime);
    }

    @Override
    @PreAuthorize("hasPermission('account', 'view')")
    @Cacheable(value = "account_transaction", key = "#page.current + ':' + #page.size + ':' + #accountId + ':' + #startTime + ':' + #endTime")
    public IPage<Transaction> pageAccountTransactions(IPage<Transaction> page, Long accountId, 
                                                   String startTimeStr, String endTimeStr) {
        log.info("分页查询账户交易记录: page={}, accountId={}, startTime={}, endTime={}", 
                page, accountId, startTimeStr, endTimeStr);
        
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        if (StringUtils.hasText(startTimeStr)) {
            startTime = LocalDateTime.parse(startTimeStr, DATE_TIME_FORMATTER);
        }
        if (StringUtils.hasText(endTimeStr)) {
            endTime = LocalDateTime.parse(endTimeStr, DATE_TIME_FORMATTER);
        }
        
        return transactionService.pageTransactions(page, null, accountId, startTime, endTime);
    }
}