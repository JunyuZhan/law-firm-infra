package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.finance.entity.Account;
import com.lawfirm.model.finance.entity.Transaction;
import com.lawfirm.model.finance.mapper.AccountMapper;
import com.lawfirm.model.finance.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * 财务账户服务空实现类，当存储功能禁用时使用
 */
@Slf4j
@Service("financeAccountServiceImpl")
@ConditionalOnProperty(name = "lawfirm.storage.enabled", havingValue = "false", matchIfMissing = false)
public class NoOpAccountServiceImpl extends BaseServiceImpl<BaseMapper<Account>, Account> implements AccountService {

    @Override
    public Long createAccount(Account account) {
        log.warn("存储功能已禁用，忽略创建账户操作");
        return null;
    }

    @Override
    public boolean updateAccount(Account account) {
        log.warn("存储功能已禁用，忽略更新账户操作");
        return true;
    }

    @Override
    public boolean deleteAccount(Long accountId) {
        log.warn("存储功能已禁用，忽略删除账户操作: {}", accountId);
        return true;
    }

    @Override
    public Account getAccountById(Long accountId) {
        log.warn("存储功能已禁用，忽略获取账户详情操作: {}", accountId);
        return null;
    }

    @Override
    public List<Account> listAccounts(Integer status) {
        log.warn("存储功能已禁用，忽略获取账户列表操作");
        return Collections.emptyList();
    }

    @Override
    public IPage<Account> pageAccounts(IPage<Account> page, Integer status) {
        log.warn("存储功能已禁用，忽略分页查询账户操作");
        return page;
    }

    @Override
    public boolean increaseBalance(Long accountId, BigDecimal amount, String remark) {
        log.warn("存储功能已禁用，忽略增加账户余额操作: {}, {}, {}", accountId, amount, remark);
        return true;
    }

    @Override
    public boolean decreaseBalance(Long accountId, BigDecimal amount, String remark) {
        log.warn("存储功能已禁用，忽略减少账户余额操作: {}, {}, {}", accountId, amount, remark);
        return true;
    }

    @Override
    public boolean freezeAmount(Long accountId, BigDecimal amount, String remark) {
        log.warn("存储功能已禁用，忽略冻结账户金额操作: {}, {}, {}", accountId, amount, remark);
        return true;
    }

    @Override
    public boolean unfreezeAmount(Long accountId, BigDecimal amount, String remark) {
        log.warn("存储功能已禁用，忽略解冻账户金额操作: {}, {}, {}", accountId, amount, remark);
        return true;
    }

    @Override
    public Long transfer(Long fromAccountId, Long toAccountId, BigDecimal amount, String remark) {
        log.warn("存储功能已禁用，忽略账户间转账操作: {}, {}, {}, {}", fromAccountId, toAccountId, amount, remark);
        return null;
    }

    @Override
    public List<Transaction> listAccountTransactions(Long accountId, String startTime, String endTime) {
        log.warn("存储功能已禁用，忽略查询账户交易记录操作: {}, {}, {}", accountId, startTime, endTime);
        return Collections.emptyList();
    }

    @Override
    public IPage<Transaction> pageAccountTransactions(IPage<Transaction> page, Long accountId, String startTime, String endTime) {
        log.warn("存储功能已禁用，忽略分页查询账户交易记录操作: {}, {}, {}", accountId, startTime, endTime);
        return page;
    }
} 