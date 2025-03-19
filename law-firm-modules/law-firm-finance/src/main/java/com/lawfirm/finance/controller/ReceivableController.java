package com.lawfirm.finance.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.finance.entity.Receivable;
import com.lawfirm.model.finance.enums.ReceivableStatusEnum;
import com.lawfirm.model.finance.service.ReceivableService;
import com.lawfirm.model.finance.vo.receivable.ReceivableDetailVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 应收账款管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/finance/receivable")
@RequiredArgsConstructor
@Tag(name = "应收账款管理", description = "应收账款相关接口")
public class ReceivableController {

    private final ReceivableService receivableService;

    /**
     * 创建应收账款
     */
    @PostMapping
    @Operation(summary = "创建应收账款")
    public Long createReceivable(@Valid @RequestBody Receivable receivable) {
        return receivableService.createReceivable(receivable);
    }

    /**
     * 更新应收账款
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新应收账款")
    public Boolean updateReceivable(@PathVariable Long id, @Valid @RequestBody Receivable receivable) {
        receivable.setId(id);
        return receivableService.updateReceivable(receivable);
    }

    /**
     * 获取应收账款详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取应收账款详情")
    public ReceivableDetailVO getReceivable(@PathVariable Long id) {
        return receivableService.getReceivable(id);
    }

    /**
     * 删除应收账款
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除应收账款")
    public Boolean deleteReceivable(@PathVariable Long id) {
        return receivableService.deleteReceivable(id);
    }

    /**
     * 更新应收账款状态
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "更新应收账款状态")
    public Boolean updateReceivableStatus(@PathVariable Long id, 
                                        @Parameter(description = "状态") @RequestParam ReceivableStatusEnum status,
                                        @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        return receivableService.updateReceivableStatus(id, status, remark);
    }

    /**
     * 记录收款
     */
    @PostMapping("/{id}/receipt")
    @Operation(summary = "记录收款")
    public Long recordReceipt(@PathVariable Long id,
                            @Parameter(description = "收款金额") @RequestParam BigDecimal amount,
                            @Parameter(description = "收款账户ID") @RequestParam Long accountId,
                            @Parameter(description = "收款日期") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime receiveDate,
                            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        return receivableService.recordReceipt(id, amount, accountId, receiveDate, remark);
    }

    /**
     * 查询应收账款列表
     */
    @GetMapping("/list")
    @Operation(summary = "查询应收账款列表")
    public List<ReceivableDetailVO> listReceivables(@RequestParam(required = false) Long contractId,
                                                   @RequestParam(required = false) Long clientId,
                                                   @RequestParam(required = false) ReceivableStatusEnum status,
                                                   @RequestParam(required = false) Integer overdueDays) {
        return receivableService.listReceivables(contractId, clientId, status, overdueDays);
    }

    /**
     * 分页查询应收账款
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询应收账款")
    public IPage<ReceivableDetailVO> pageReceivables(@RequestParam(defaultValue = "1") long current,
                                                    @RequestParam(defaultValue = "10") long size,
                                                    @RequestParam(required = false) Long contractId,
                                                    @RequestParam(required = false) Long clientId,
                                                    @RequestParam(required = false) ReceivableStatusEnum status,
                                                    @RequestParam(required = false) Integer overdueDays) {
        Page<Receivable> page = new Page<>(current, size);
        return receivableService.pageReceivables(page, contractId, clientId, status, overdueDays);
    }

    /**
     * 按合同查询应收账款
     */
    @GetMapping("/contract/{contractId}")
    @Operation(summary = "按合同查询应收账款")
    public List<ReceivableDetailVO> listReceivablesByContract(@PathVariable Long contractId) {
        return receivableService.listReceivablesByContract(contractId);
    }

    /**
     * 按客户查询应收账款
     */
    @GetMapping("/client/{clientId}")
    @Operation(summary = "按客户查询应收账款")
    public List<ReceivableDetailVO> listReceivablesByClient(@PathVariable Long clientId) {
        return receivableService.listReceivablesByClient(clientId);
    }

    /**
     * 统计应收账款总额
     */
    @GetMapping("/sum")
    @Operation(summary = "统计应收账款总额")
    public BigDecimal sumReceivableAmount(@RequestParam(required = false) Long contractId,
                                        @RequestParam(required = false) Long clientId,
                                        @RequestParam(required = false) ReceivableStatusEnum status) {
        return receivableService.sumReceivableAmount(contractId, clientId, status);
    }

    /**
     * 统计已收款总额
     */
    @GetMapping("/sum/received")
    @Operation(summary = "统计已收款总额")
    public BigDecimal sumReceivedAmount(@RequestParam(required = false) Long contractId,
                                      @RequestParam(required = false) Long clientId) {
        return receivableService.sumReceivedAmount(contractId, clientId);
    }

    /**
     * 统计未收款总额
     */
    @GetMapping("/sum/unreceived")
    @Operation(summary = "统计未收款总额")
    public BigDecimal sumUnreceivedAmount(@RequestParam(required = false) Long contractId,
                                        @RequestParam(required = false) Long clientId) {
        return receivableService.sumUnreceivedAmount(contractId, clientId);
    }

    /**
     * 账龄分析
     */
    @GetMapping("/aging")
    @Operation(summary = "账龄分析")
    public Map<String, BigDecimal> agingAnalysis(@RequestParam(required = false) Long clientId) {
        return receivableService.agingAnalysis(clientId);
    }

    /**
     * 更新应收账款逾期状态
     */
    @PutMapping("/overdue/update")
    @Operation(summary = "更新应收账款逾期状态")
    public Integer updateOverdueStatus() {
        return receivableService.updateOverdueStatus();
    }
} 