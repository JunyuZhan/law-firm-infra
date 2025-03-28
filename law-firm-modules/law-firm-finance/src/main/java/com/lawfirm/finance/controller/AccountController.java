package com.lawfirm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.finance.dto.account.AccountCreateDTO;
import com.lawfirm.model.finance.dto.account.AccountQueryDTO;
import com.lawfirm.model.finance.dto.account.AccountUpdateDTO;
import com.lawfirm.model.finance.entity.Account;
import com.lawfirm.model.finance.entity.Transaction;
import com.lawfirm.model.finance.service.AccountService;
import com.lawfirm.model.finance.vo.account.AccountVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController("accountController")
@RequiredArgsConstructor
@RequestMapping("/api/finance/account")
@Tag(name = "账户管理", description = "账户相关接口")
public class AccountController {
    
    private final AccountService accountService;
    
    @PostMapping
    @Operation(summary = "创建账户", description = "创建新的账户")
    public ResponseEntity<Account> createAccount(@RequestBody @Validated AccountCreateDTO createDTO) {
        log.info("创建账户: {}", createDTO);
        Account account = new Account();
        account.setAccountNumber(createDTO.getAccountNumber());
        account.setAccountName(createDTO.getAccountName());
        account.setAccountType(createDTO.getAccountType());
        account.setAccountStatus(createDTO.getAccountStatus());
        account.setCurrency(createDTO.getCurrency());
        account.setBalance(createDTO.getBalance());
        account.setFrozenAmount(createDTO.getFrozenAmount());
        account.setAvailableAmount(createDTO.getAvailableAmount());
        account.setCreditLimit(createDTO.getCreditLimit());
        account.setBankName(createDTO.getBankName());
        account.setBankAccount(createDTO.getBankAccount());
        account.setAccountHolder(createDTO.getAccountHolder());
        account.setClientId(createDTO.getClientId());
        account.setDepartmentId(createDTO.getDepartmentId());
        
        Long accountId = accountService.createAccount(account);
        return ResponseEntity.ok(accountService.getAccountById(accountId));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取账户详情", description = "根据ID获取账户详情")
    public ResponseEntity<Account> getAccount(
            @Parameter(description = "账户ID") @PathVariable Long id) {
        log.info("获取账户详情, id: {}", id);
        return ResponseEntity.ok(accountService.getAccountById(id));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "更新账户", description = "更新账户信息")
    public ResponseEntity<Account> updateAccount(
            @Parameter(description = "账户ID") @PathVariable Long id,
            @RequestBody @Validated AccountUpdateDTO updateDTO) {
        log.info("更新账户, id: {}, data: {}", id, updateDTO);
        Account account = accountService.getAccountById(id);
        // 更新属性
        if (updateDTO.getAccountName() != null) {
            account.setAccountName(updateDTO.getAccountName());
        }
        if (updateDTO.getAccountStatus() != null) {
            account.setAccountStatus(updateDTO.getAccountStatus());
        }
        if (updateDTO.getBankName() != null) {
            account.setBankName(updateDTO.getBankName());
        }
        if (updateDTO.getBankAccount() != null) {
            account.setBankAccount(updateDTO.getBankAccount());
        }
        if (updateDTO.getAccountHolder() != null) {
            account.setAccountHolder(updateDTO.getAccountHolder());
        }
        
        boolean success = accountService.updateAccount(account);
        if (success) {
            return ResponseEntity.ok(accountService.getAccountById(id));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除账户", description = "根据ID删除账户")
    public ResponseEntity<Void> deleteAccount(
            @Parameter(description = "账户ID") @PathVariable Long id) {
        log.info("删除账户, id: {}", id);
        boolean success = accountService.deleteAccount(id);
        if (success) {
        return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping
    @Operation(summary = "查询账户列表", description = "根据条件查询账户列表")
    public ResponseEntity<List<Account>> listAccounts(
            @Parameter(description = "账户状态") @RequestParam(required = false) Integer status) {
        log.info("查询账户列表, status: {}", status);
        List<Account> accounts = accountService.listAccounts(status);
        return ResponseEntity.ok(accounts);
    }
    
    @GetMapping("/page")
    @Operation(summary = "分页查询账户", description = "分页查询账户信息")
    public ResponseEntity<IPage<Account>> pageAccounts(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "账户状态") @RequestParam(required = false) Integer status) {
        log.info("分页查询账户, current: {}, size: {}, status: {}", current, size, status);
        IPage<Account> page = accountService.pageAccounts(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(current, size), status);
        return ResponseEntity.ok(page);
    }
    
    @PostMapping("/{id}/increase")
    @Operation(summary = "增加账户余额", description = "增加指定账户的余额")
    public ResponseEntity<Void> increaseBalance(
            @Parameter(description = "账户ID") @PathVariable Long id,
            @Parameter(description = "增加金额") @RequestParam BigDecimal amount,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        log.info("增加账户余额, id: {}, amount: {}, remark: {}", id, amount, remark);
        boolean success = accountService.increaseBalance(id, amount, remark);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{id}/decrease")
    @Operation(summary = "减少账户余额", description = "减少指定账户的余额")
    public ResponseEntity<Void> decreaseBalance(
            @Parameter(description = "账户ID") @PathVariable Long id,
            @Parameter(description = "减少金额") @RequestParam BigDecimal amount,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        log.info("减少账户余额, id: {}, amount: {}, remark: {}", id, amount, remark);
        boolean success = accountService.decreaseBalance(id, amount, remark);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{id}/freeze")
    @Operation(summary = "冻结账户金额", description = "冻结指定账户的部分金额")
    public ResponseEntity<Void> freezeAmount(
            @Parameter(description = "账户ID") @PathVariable Long id,
            @Parameter(description = "冻结金额") @RequestParam BigDecimal amount,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        log.info("冻结账户金额, id: {}, amount: {}, remark: {}", id, amount, remark);
        boolean success = accountService.freezeAmount(id, amount, remark);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{id}/unfreeze")
    @Operation(summary = "解冻账户金额", description = "解冻指定账户的部分金额")
    public ResponseEntity<Void> unfreezeAmount(
            @Parameter(description = "账户ID") @PathVariable Long id,
            @Parameter(description = "解冻金额") @RequestParam BigDecimal amount,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        log.info("解冻账户金额, id: {}, amount: {}, remark: {}", id, amount, remark);
        boolean success = accountService.unfreezeAmount(id, amount, remark);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/{id}/transfer")
    @Operation(summary = "账户转账", description = "从指定账户转账到目标账户")
    public ResponseEntity<Long> transfer(
            @Parameter(description = "源账户ID") @PathVariable Long id,
            @Parameter(description = "目标账户ID") @RequestParam Long toAccountId,
            @Parameter(description = "转账金额") @RequestParam BigDecimal amount,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        log.info("账户转账, 源账户id: {}, 目标账户id: {}, 金额: {}, 备注: {}", 
                id, toAccountId, amount, remark);
        Long transactionId = accountService.transfer(id, toAccountId, amount, remark);
        return ResponseEntity.ok(transactionId);
    }
    
    @GetMapping("/{id}/transactions")
    @Operation(summary = "查询账户交易记录", description = "查询指定账户的交易记录")
    public ResponseEntity<List<Transaction>> listAccountTransactions(
            @Parameter(description = "账户ID") @PathVariable Long id,
            @Parameter(description = "开始时间") @RequestParam(required = false) String startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) String endTime) {
        log.info("查询账户交易记录, id: {}, startTime: {}, endTime: {}", id, startTime, endTime);
        List<Transaction> transactions = accountService.listAccountTransactions(id, startTime, endTime);
        return ResponseEntity.ok(transactions);
    }
    
    @GetMapping("/{id}/transactions/page")
    @Operation(summary = "分页查询账户交易记录", description = "分页查询指定账户的交易记录")
    public ResponseEntity<IPage<Transaction>> pageAccountTransactions(
            @Parameter(description = "账户ID") @PathVariable Long id,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "开始时间") @RequestParam(required = false) String startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) String endTime) {
        log.info("分页查询账户交易记录, id: {}, current: {}, size: {}, startTime: {}, endTime: {}", 
                id, current, size, startTime, endTime);
        IPage<Transaction> page = accountService.pageAccountTransactions(
                new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(current, size),
                id, startTime, endTime);
        return ResponseEntity.ok(page);
    }
}
