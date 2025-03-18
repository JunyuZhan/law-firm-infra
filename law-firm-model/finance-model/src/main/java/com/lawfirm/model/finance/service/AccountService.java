package com.lawfirm.model.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.finance.entity.Account;
import com.lawfirm.model.finance.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * 账户服务接口
 */
public interface AccountService {

    /**
     * 创建账户
     *
     * @param account 账户信息
     * @return 账户ID
     */
    Long createAccount(Account account);

    /**
     * 更新账户信息
     *
     * @param account 账户信息
     * @return 是否更新成功
     */
    boolean updateAccount(Account account);

    /**
     * 删除账户
     *
     * @param accountId 账户ID
     * @return 是否删除成功
     */
    boolean deleteAccount(Long accountId);

    /**
     * 获取账户详情
     *
     * @param accountId 账户ID
     * @return 账户信息
     */
    Account getAccountById(Long accountId);

    /**
     * 获取账户列表
     *
     * @param status 账户状态，可为null
     * @return 账户列表
     */
    List<Account> listAccounts(Integer status);

    /**
     * 分页查询账户
     *
     * @param page   分页参数
     * @param status 账户状态，可为null
     * @return 分页账户信息
     */
    IPage<Account> pageAccounts(IPage<Account> page, Integer status);

    /**
     * 增加账户余额
     *
     * @param accountId 账户ID
     * @param amount    增加金额
     * @param remark    备注
     * @return 是否操作成功
     */
    boolean increaseBalance(Long accountId, BigDecimal amount, String remark);

    /**
     * 减少账户余额
     *
     * @param accountId 账户ID
     * @param amount    减少金额
     * @param remark    备注
     * @return 是否操作成功
     */
    boolean decreaseBalance(Long accountId, BigDecimal amount, String remark);

    /**
     * 冻结账户金额
     *
     * @param accountId 账户ID
     * @param amount    冻结金额
     * @param remark    备注
     * @return 是否操作成功
     */
    boolean freezeAmount(Long accountId, BigDecimal amount, String remark);

    /**
     * 解冻账户金额
     *
     * @param accountId 账户ID
     * @param amount    解冻金额
     * @param remark    备注
     * @return 是否操作成功
     */
    boolean unfreezeAmount(Long accountId, BigDecimal amount, String remark);

    /**
     * 账户间转账
     *
     * @param fromAccountId 转出账户ID
     * @param toAccountId   转入账户ID
     * @param amount        转账金额
     * @param remark        备注
     * @return 转账交易ID
     */
    Long transfer(Long fromAccountId, Long toAccountId, BigDecimal amount, String remark);

    /**
     * 查询账户交易记录
     *
     * @param accountId 账户ID
     * @param startTime 开始时间，可为null
     * @param endTime   结束时间，可为null
     * @return 交易记录列表
     */
    List<Transaction> listAccountTransactions(Long accountId, String startTime, String endTime);

    /**
     * 分页查询账户交易记录
     *
     * @param page      分页参数
     * @param accountId 账户ID
     * @param startTime 开始时间，可为null
     * @param endTime   结束时间，可为null
     * @return 分页交易记录
     */
    IPage<Transaction> pageAccountTransactions(IPage<Transaction> page, Long accountId, 
                                             String startTime, String endTime);
} 