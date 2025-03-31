package com.lawfirm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.finance.entity.Transaction;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;
import com.lawfirm.model.finance.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController("transactionController")
@RequiredArgsConstructor
@RequestMapping("/finance/transaction")
@Tag(name = "交易管理", description = "交易管理相关API")
public class TransactionController {

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService transactionService;

    @PostMapping
    @Operation(summary = "创建交易", description = "创建新的交易记录")
    public ResponseEntity<Transaction> createTransaction(@RequestBody @Validated Transaction transaction) {
        logger.info("创建交易: {}", transaction);
        Long transactionId = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(transactionService.getTransactionById(transactionId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取交易详情", description = "根据ID获取交易详情")
    public ResponseEntity<Transaction> getTransaction(
            @Parameter(description = "交易ID") @PathVariable Long id) {
        logger.info("获取交易详情, id: {}", id);
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新交易", description = "更新交易信息")
    public ResponseEntity<Transaction> updateTransaction(
            @Parameter(description = "交易ID") @PathVariable Long id,
            @RequestBody @Validated Transaction transaction) {
        logger.info("更新交易, id: {}, data: {}", id, transaction);
        transaction.setId(id);
        boolean success = transactionService.updateTransaction(transaction);
        if (success) {
            return ResponseEntity.ok(transactionService.getTransactionById(id));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除交易", description = "根据ID删除交易")
    public ResponseEntity<Void> deleteTransaction(
            @Parameter(description = "交易ID") @PathVariable Long id) {
        logger.info("删除交易, id: {}", id);
        boolean success = transactionService.deleteTransaction(id);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @Operation(summary = "查询交易列表", description = "根据条件查询交易列表")
    public ResponseEntity<List<Transaction>> listTransactions(
            @Parameter(description = "交易类型") @RequestParam(required = false) TransactionTypeEnum type,
            @Parameter(description = "账户ID") @RequestParam(required = false) Long accountId,
            @Parameter(description = "开始时间") @RequestParam(required = false) 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        logger.info("查询交易列表, type: {}, accountId: {}, startTime: {}, endTime: {}", 
                type, accountId, startTime, endTime);
        List<Transaction> transactions = transactionService.listTransactions(type, accountId, startTime, endTime);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询交易", description = "分页查询交易信息")
    public ResponseEntity<IPage<Transaction>> pageTransactions(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "交易类型") @RequestParam(required = false) TransactionTypeEnum type,
            @Parameter(description = "账户ID") @RequestParam(required = false) Long accountId,
            @Parameter(description = "开始时间") @RequestParam(required = false) 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        logger.info("分页查询交易, current: {}, size: {}, type: {}, accountId: {}, startTime: {}, endTime: {}", 
                current, size, type, accountId, startTime, endTime);
        IPage<Transaction> page = transactionService.pageTransactions(
                new Page<>(current, size), type, accountId, startTime, endTime);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "按账户查询交易", description = "查询指定账户的交易记录")
    public ResponseEntity<List<Transaction>> listTransactionsByAccount(
            @Parameter(description = "账户ID") @PathVariable Long accountId,
            @Parameter(description = "开始时间") @RequestParam(required = false) 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        logger.info("按账户查询交易, accountId: {}, startTime: {}, endTime: {}", 
                accountId, startTime, endTime);
        List<Transaction> transactions = transactionService.listTransactionsByAccount(accountId, startTime, endTime);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/account/{accountId}/page")
    @Operation(summary = "按账户分页查询交易", description = "分页查询指定账户的交易记录")
    public ResponseEntity<IPage<Transaction>> pageTransactionsByAccount(
            @Parameter(description = "账户ID") @PathVariable Long accountId,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "开始时间") @RequestParam(required = false) 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        logger.info("按账户分页查询交易, accountId: {}, current: {}, size: {}, startTime: {}, endTime: {}", 
                accountId, current, size, startTime, endTime);
        IPage<Transaction> page = transactionService.pageTransactionsByAccount(
                new Page<>(current, size), accountId, startTime, endTime);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/contract/{contractId}")
    @Operation(summary = "按合同查询交易", description = "查询指定合同的交易记录")
    public ResponseEntity<List<Transaction>> listTransactionsByContract(
            @Parameter(description = "合同ID") @PathVariable Long contractId) {
        logger.info("按合同查询交易, contractId: {}", contractId);
        List<Transaction> transactions = transactionService.listTransactionsByContract(contractId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "按客户查询交易", description = "查询指定客户的交易记录")
    public ResponseEntity<List<Transaction>> listTransactionsByClient(
            @Parameter(description = "客户ID") @PathVariable Long clientId,
            @Parameter(description = "开始时间") @RequestParam(required = false) 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        logger.info("按客户查询交易, clientId: {}, startTime: {}, endTime: {}", 
                clientId, startTime, endTime);
        List<Transaction> transactions = transactionService.listTransactionsByClient(clientId, startTime, endTime);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/sum")
    @Operation(summary = "统计交易金额", description = "统计指定条件的交易总金额")
    public ResponseEntity<BigDecimal> sumTransactionAmount(
            @Parameter(description = "交易类型") @RequestParam(required = false) TransactionTypeEnum type,
            @Parameter(description = "账户ID") @RequestParam(required = false) Long accountId,
            @Parameter(description = "开始时间") @RequestParam(required = false) 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        logger.info("统计交易金额, type: {}, accountId: {}, startTime: {}, endTime: {}", 
                type, accountId, startTime, endTime);
        BigDecimal amount = transactionService.sumTransactionAmount(type, accountId, startTime, endTime);
        return ResponseEntity.ok(amount);
    }

    @GetMapping("/account/{accountId}/sum")
    @Operation(summary = "统计账户交易金额", description = "统计指定账户的交易总金额")
    public ResponseEntity<BigDecimal> sumAccountTransactionAmount(
            @Parameter(description = "账户ID") @PathVariable Long accountId,
            @Parameter(description = "交易类型") @RequestParam(required = false) TransactionTypeEnum type,
            @Parameter(description = "开始时间") @RequestParam(required = false) 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @Parameter(description = "结束时间") @RequestParam(required = false) 
                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        logger.info("统计账户交易金额, accountId: {}, type: {}, startTime: {}, endTime: {}", 
                accountId, type, startTime, endTime);
        BigDecimal amount = transactionService.sumAccountTransactionAmount(accountId, type, startTime, endTime);
        return ResponseEntity.ok(amount);
    }

    @PostMapping("/export")
    @Operation(summary = "导出交易数据", description = "导出指定交易的数据")
    public ResponseEntity<String> exportTransactions(
            @RequestBody List<Long> transactionIds) {
        logger.info("导出交易数据, transactionIds: {}", transactionIds);
        String filePath = transactionService.exportTransactions(transactionIds);
        return ResponseEntity.ok(filePath);
    }
}
