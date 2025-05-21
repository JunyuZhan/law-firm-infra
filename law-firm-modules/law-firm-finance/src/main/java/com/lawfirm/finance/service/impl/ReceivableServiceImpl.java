package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.model.finance.entity.Receivable;
import com.lawfirm.model.finance.enums.ReceivableStatusEnum;
import com.lawfirm.model.finance.mapper.ReceivableMapper;
import com.lawfirm.model.finance.service.ReceivableService;
import com.lawfirm.model.finance.vo.receivable.ReceivableDetailVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lawfirm.finance.exception.FinanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.workflow.service.ProcessService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 财务应收款服务实现类
 */
@Slf4j
@Service("financeReceivableServiceImpl")
@RequiredArgsConstructor
public class ReceivableServiceImpl implements ReceivableService {

    private final ReceivableMapper receivableMapper;
    private final SecurityContext securityContext;

    /**
     * 注入core层审计服务，便于后续记录应收款操作日志
     */
    @Autowired(required = false)
    @Qualifier("financeAuditService")
    private AuditService auditService;

    /**
     * 注入core层消息发送服务，便于后续应收款相关通知等
     */
    @Autowired(required = false)
    @Qualifier("financeMessageSender")
    private MessageSender messageSender;

    /**
     * 注入core层文件存储服务，便于后续应收款附件上传等
     */
    @Autowired(required = false)
    @Qualifier("financeFileService")
    private FileService fileService;

    /**
     * 注入core层存储桶服务，便于后续应收款桶管理等
     */
    @Autowired(required = false)
    @Qualifier("financeBucketService")
    private BucketService bucketService;

    /**
     * 注入core层全文检索服务，便于后续应收款检索等
     */
    @Autowired(required = false)
    @Qualifier("financeSearchService")
    private SearchService searchService;

    /**
     * 注入core层流程服务，便于后续应收款审批流转等
     */
    @Autowired(required = false)
    @Qualifier("financeProcessService")
    private ProcessService processService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createReceivable(Receivable receivable) {
        log.info("创建应收账款: {}", receivable);
        receivable.setStatusEnum(ReceivableStatusEnum.PENDING);
        receivable.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        receivable.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        receivableMapper.insert(receivable);
        return receivable.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateReceivable(Receivable receivable) {
        log.info("更新应收账款: {}", receivable);
        receivable.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        return receivableMapper.updateById(receivable) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteReceivable(Long id) {
        log.info("删除应收账款: {}", id);
        return receivableMapper.deleteById(id) > 0;
    }

    @Override
    public ReceivableDetailVO getReceivable(Long id) {
        log.info("获取应收账款详情: {}", id);
        Receivable receivable = receivableMapper.selectById(id);
        return convertToDetailVO(receivable);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateReceivableStatus(Long id, ReceivableStatusEnum status, String remark) {
        log.info("更新应收账款状态: id={}, status={}, remark={}", id, status, remark);
        Receivable receivable = receivableMapper.selectById(id);
        if (receivable == null) {
            throw FinanceException.notFound("应收账款不存在");
        }
        receivable.setStatusEnum(status);
        receivable.setRemark(remark);
        receivable.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        return receivableMapper.updateById(receivable) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long recordReceipt(Long id, BigDecimal amount, Long accountId, 
                            LocalDateTime receiveDate, String remark) {
        log.info("记录收款: id={}, amount={}, accountId={}, receiveDate={}, remark={}", 
                id, amount, accountId, receiveDate, remark);
        Receivable receivable = receivableMapper.selectById(id);
        if (receivable == null) {
            throw FinanceException.notFound("应收账款不存在");
        }
        
        // 更新已收金额
        BigDecimal newReceivedAmount = receivable.getReceivedAmount().add(amount);
        receivable.setReceivedAmount(newReceivedAmount);
        receivable.setUnreceivedAmount(receivable.getTotalAmount().subtract(newReceivedAmount));
        receivable.setLastReceiptDate(receiveDate);
        
        // 更新状态
        if (newReceivedAmount.compareTo(receivable.getTotalAmount()) >= 0) {
            receivable.setStatusEnum(ReceivableStatusEnum.COMPLETED);
        } else if (newReceivedAmount.compareTo(BigDecimal.ZERO) > 0) {
            receivable.setStatusEnum(ReceivableStatusEnum.PARTIAL);
        }
        
        receivable.setRemark(remark);
        receivable.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        receivableMapper.updateById(receivable);
        
        return id;
    }

    @Override
    public List<ReceivableDetailVO> listReceivables(Long contractId, Long clientId, 
                                                   ReceivableStatusEnum status, Integer overdueDays) {
        log.info("查询应收账款列表: contractId={}, clientId={}, status={}, overdueDays={}", 
                contractId, clientId, status, overdueDays);
        LambdaQueryWrapper<Receivable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(contractId != null, Receivable::getContractId, contractId)
                .eq(clientId != null, Receivable::getClientId, clientId)
                .eq(status != null, Receivable::getStatusCode, status.getCode())
                .ge(overdueDays != null, Receivable::getOverdueDays, overdueDays)
                .orderByDesc(Receivable::getCreateTime);
        return receivableMapper.selectList(wrapper).stream()
                .map(this::convertToDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public IPage<ReceivableDetailVO> pageReceivables(Page<Receivable> page, Long contractId, Long clientId, 
                                                    ReceivableStatusEnum status, Integer overdueDays) {
        log.info("分页查询应收账款: page={}, contractId={}, clientId={}, status={}, overdueDays={}", 
                page, contractId, clientId, status, overdueDays);
        LambdaQueryWrapper<Receivable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(contractId != null, Receivable::getContractId, contractId)
                .eq(clientId != null, Receivable::getClientId, clientId)
                .eq(status != null, Receivable::getStatusCode, status.getCode())
                .ge(overdueDays != null, Receivable::getOverdueDays, overdueDays)
                .orderByDesc(Receivable::getCreateTime);
        IPage<Receivable> receivablePage = receivableMapper.selectPage(page, wrapper);
        return receivablePage.convert(this::convertToDetailVO);
    }

    @Override
    public List<ReceivableDetailVO> listReceivablesByContract(Long contractId) {
        log.info("按合同查询应收账款: contractId={}", contractId);
        LambdaQueryWrapper<Receivable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Receivable::getContractId, contractId)
                .orderByDesc(Receivable::getCreateTime);
        return receivableMapper.selectList(wrapper).stream()
                .map(this::convertToDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReceivableDetailVO> listReceivablesByClient(Long clientId) {
        log.info("按客户查询应收账款: clientId={}", clientId);
        LambdaQueryWrapper<Receivable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Receivable::getClientId, clientId)
                .orderByDesc(Receivable::getCreateTime);
        return receivableMapper.selectList(wrapper).stream()
                .map(this::convertToDetailVO)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal sumReceivableAmount(Long contractId, Long clientId, ReceivableStatusEnum status) {
        log.info("统计应收账款总额: contractId={}, clientId={}, status={}", contractId, clientId, status);
        LambdaQueryWrapper<Receivable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(contractId != null, Receivable::getContractId, contractId)
                .eq(clientId != null, Receivable::getClientId, clientId)
                .eq(status != null, Receivable::getStatusCode, status.getCode());
        List<Receivable> receivables = receivableMapper.selectList(wrapper);
        return receivables.stream()
                .map(Receivable::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal sumReceivedAmount(Long contractId, Long clientId) {
        log.info("统计已收款总额: contractId={}, clientId={}", contractId, clientId);
        return sumReceivableAmount(contractId, clientId, ReceivableStatusEnum.COMPLETED);
    }

    @Override
    public BigDecimal sumUnreceivedAmount(Long contractId, Long clientId) {
        log.info("统计未收款总额: contractId={}, clientId={}", contractId, clientId);
        return sumReceivableAmount(contractId, clientId, ReceivableStatusEnum.PENDING);
    }

    @Override
    public Map<String, BigDecimal> agingAnalysis(Long clientId) {
        log.info("账龄分析: clientId={}", clientId);
        Map<String, BigDecimal> result = new HashMap<>();
        result.put("0-30天", BigDecimal.ZERO);
        result.put("31-60天", BigDecimal.ZERO);
        result.put("61-90天", BigDecimal.ZERO);
        result.put("90天以上", BigDecimal.ZERO);
        
        LambdaQueryWrapper<Receivable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(clientId != null, Receivable::getClientId, clientId)
                .eq(Receivable::getStatusCode, ReceivableStatusEnum.PENDING.getCode());
        List<Receivable> receivables = receivableMapper.selectList(wrapper);
        
        LocalDateTime now = LocalDateTime.now();
        for (Receivable receivable : receivables) {
            long days = ChronoUnit.DAYS.between(receivable.getExpectedReceiptDate(), now);
            if (days <= 30) {
                result.put("0-30天", result.get("0-30天").add(receivable.getUnreceivedAmount()));
            } else if (days <= 60) {
                result.put("31-60天", result.get("31-60天").add(receivable.getUnreceivedAmount()));
            } else if (days <= 90) {
                result.put("61-90天", result.get("61-90天").add(receivable.getUnreceivedAmount()));
            } else {
                result.put("90天以上", result.get("90天以上").add(receivable.getUnreceivedAmount()));
            }
        }
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateOverdueStatus() {
        log.info("更新应收账款逾期状态");
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<Receivable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Receivable::getStatusCode, ReceivableStatusEnum.PENDING.getCode())
                .lt(Receivable::getExpectedReceiptDate, now);
        List<Receivable> receivables = receivableMapper.selectList(wrapper);
        
        int count = 0;
        for (Receivable receivable : receivables) {
            long days = ChronoUnit.DAYS.between(receivable.getExpectedReceiptDate(), now);
            receivable.setOverdueDays((int) days);
            receivable.setStatusEnum(ReceivableStatusEnum.OVERDUE);
            receivable.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
            if (receivableMapper.updateById(receivable) > 0) {
                count++;
            }
        }
        
        return count;
    }

    private ReceivableDetailVO convertToDetailVO(Receivable receivable) {
        if (receivable == null) {
            return null;
        }
        
        ReceivableDetailVO vo = new ReceivableDetailVO();
        vo.setId(receivable.getId());
        vo.setContractId(receivable.getContractId());
        vo.setClientId(receivable.getClientId());
        vo.setTotalAmount(receivable.getTotalAmount());
        vo.setReceivedAmount(receivable.getReceivedAmount());
        vo.setUnreceivedAmount(receivable.getUnreceivedAmount());
        vo.setExpectedReceiptDate(receivable.getExpectedReceiptDate());
        vo.setLastReceiptDate(receivable.getLastReceiptDate());
        vo.setStatus(receivable.getStatusEnum().getCode());
        vo.setOverdueDays(receivable.getOverdueDays());
        vo.setRemark(receivable.getRemark());
        vo.setCreateTime(receivable.getCreateTime());
        vo.setUpdateTime(receivable.getUpdateTime());
        vo.setCreateBy(receivable.getCreateBy());
        vo.setUpdateBy(receivable.getUpdateBy());
        
        return vo;
    }
}