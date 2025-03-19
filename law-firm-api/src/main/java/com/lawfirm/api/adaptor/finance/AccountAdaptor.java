package com.lawfirm.api.adaptor.finance;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.finance.dto.account.AccountCreateDTO;
import com.lawfirm.model.finance.dto.account.AccountUpdateDTO;
import com.lawfirm.model.finance.entity.Account;
import com.lawfirm.model.finance.service.AccountService;
import com.lawfirm.model.finance.vo.account.AccountVO;
import com.lawfirm.model.finance.enums.AccountStatusEnum;
import com.lawfirm.model.finance.enums.AccountTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        Account account = accountService.createAccount(dto);
        return convert(account, AccountVO.class);
    }

    /**
     * 更新账户
     */
    public AccountVO updateAccount(Long id, AccountUpdateDTO dto) {
        Account account = accountService.updateAccount(id, dto);
        return convert(account, AccountVO.class);
    }

    /**
     * 获取账户详情
     */
    public AccountVO getAccount(Long id) {
        Account account = accountService.getAccount(id);
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
        List<Account> accounts = accountService.listAccounts();
        return accounts.stream()
                .map(account -> convert(account, AccountVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 更新账户状态
     */
    public void updateAccountStatus(Long id, AccountStatusEnum status) {
        accountService.updateAccountStatus(id, status);
    }

    /**
     * 根据账户类型查询账户
     */
    public List<AccountVO> getAccountsByType(AccountTypeEnum type) {
        List<Account> accounts = accountService.getAccountsByType(type);
        return accounts.stream()
                .map(account -> convert(account, AccountVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 检查账户是否存在
     */
    public boolean existsAccount(Long id) {
        return accountService.existsAccount(id);
    }

    /**
     * 获取账户数量
     */
    public long countAccounts() {
        return accountService.countAccounts();
    }
} 