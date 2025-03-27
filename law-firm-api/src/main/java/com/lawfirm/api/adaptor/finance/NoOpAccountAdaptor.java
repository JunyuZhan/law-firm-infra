package com.lawfirm.api.adaptor.finance;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.account.AccountCreateDTO;
import com.lawfirm.model.finance.dto.account.AccountUpdateDTO;
import com.lawfirm.model.finance.enums.AccountStatusEnum;
import com.lawfirm.model.finance.enums.AccountTypeEnum;
import com.lawfirm.model.finance.vo.account.AccountVO;
import com.lawfirm.model.finance.vo.transaction.TransactionDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * 账户管理适配器空实现，当存储功能禁用时使用
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "lawfirm.storage.enabled", havingValue = "false", matchIfMissing = false)
public class NoOpAccountAdaptor extends BaseAdaptor {

    /**
     * 创建账户
     */
    public AccountVO createAccount(AccountCreateDTO dto) {
        log.warn("存储功能已禁用，忽略创建账户操作: {}", dto);
        return null;
    }

    /**
     * 更新账户
     */
    public AccountVO updateAccount(Long id, AccountUpdateDTO dto) {
        log.warn("存储功能已禁用，忽略更新账户操作: {}, {}", id, dto);
        return null;
    }

    /**
     * 获取账户详情
     */
    public AccountVO getAccount(Long id) {
        log.warn("存储功能已禁用，忽略获取账户详情操作: {}", id);
        return null;
    }

    /**
     * 删除账户
     */
    public void deleteAccount(Long id) {
        log.warn("存储功能已禁用，忽略删除账户操作: {}", id);
    }

    /**
     * 获取所有账户
     */
    public List<AccountVO> listAccounts() {
        log.warn("存储功能已禁用，忽略获取所有账户操作");
        return Collections.emptyList();
    }

    /**
     * 获取账户列表（按状态筛选）
     */
    public List<AccountVO> listAccountsByStatus(Integer status) {
        log.warn("存储功能已禁用，忽略按状态获取账户列表操作: {}", status);
        return Collections.emptyList();
    }

    /**
     * 分页查询账户
     */
    public IPage<AccountVO> pageAccounts(Integer page, Integer size, Integer status) {
        log.warn("存储功能已禁用，忽略分页查询账户操作: {}, {}, {}", page, size, status);
        return new Page<>();
    }

    /**
     * 增加账户余额
     */
    public boolean increaseBalance(Long accountId, BigDecimal amount, String remark) {
        log.warn("存储功能已禁用，忽略增加账户余额操作: {}, {}, {}", accountId, amount, remark);
        return true;
    }

    /**
     * 减少账户余额
     */
    public boolean decreaseBalance(Long accountId, BigDecimal amount, String remark) {
        log.warn("存储功能已禁用，忽略减少账户余额操作: {}, {}, {}", accountId, amount, remark);
        return true;
    }

    /**
     * 冻结账户金额
     */
    public boolean freezeAmount(Long accountId, BigDecimal amount, String remark) {
        log.warn("存储功能已禁用，忽略冻结账户金额操作: {}, {}, {}", accountId, amount, remark);
        return true;
    }

    /**
     * 解冻账户金额
     */
    public boolean unfreezeAmount(Long accountId, BigDecimal amount, String remark) {
        log.warn("存储功能已禁用，忽略解冻账户金额操作: {}, {}, {}", accountId, amount, remark);
        return true;
    }

    /**
     * 账户间转账
     */
    public Long transfer(Long fromAccountId, Long toAccountId, BigDecimal amount, String remark) {
        log.warn("存储功能已禁用，忽略账户间转账操作: {}, {}, {}, {}", fromAccountId, toAccountId, amount, remark);
        return null;
    }

    /**
     * 查询账户交易记录
     */
    public List<TransactionDetailVO> listAccountTransactions(Long accountId, String startTime, String endTime) {
        log.warn("存储功能已禁用，忽略查询账户交易记录操作: {}, {}, {}", accountId, startTime, endTime);
        return Collections.emptyList();
    }

    /**
     * 分页查询账户交易记录
     */
    public IPage<TransactionDetailVO> pageAccountTransactions(Integer page, Integer size, Long accountId, 
                                                       String startTime, String endTime) {
        log.warn("存储功能已禁用，忽略分页查询账户交易记录操作: {}, {}, {}, {}, {}", page, size, accountId, startTime, endTime);
        return new Page<>();
    }

    /**
     * 更新账户状态（兼容旧接口）
     */
    public void updateAccountStatus(Long id, AccountStatusEnum status) {
        log.warn("存储功能已禁用，忽略更新账户状态操作: {}, {}", id, status);
    }

    /**
     * 根据账户类型查询账户（兼容旧接口）
     */
    public List<AccountVO> getAccountsByType(AccountTypeEnum type) {
        log.warn("存储功能已禁用，忽略按类型查询账户操作: {}", type);
        return Collections.emptyList();
    }

    /**
     * 检查账户是否存在
     */
    public boolean existsAccount(Long id) {
        log.warn("存储功能已禁用，忽略检查账户是否存在操作: {}", id);
        return false;
    }

    /**
     * 获取账户数量
     */
    public long countAccounts() {
        log.warn("存储功能已禁用，忽略获取账户数量操作");
        return 0;
    }
} 