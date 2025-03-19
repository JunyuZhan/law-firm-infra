package com.lawfirm.model.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.finance.entity.Receivable;
import com.lawfirm.model.finance.enums.ReceivableStatusEnum;
import com.lawfirm.model.finance.vo.receivable.ReceivableDetailVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 应收账款服务接口
 */
public interface ReceivableService {

    /**
     * 创建应收账款
     *
     * @param receivable 应收账款信息
     * @return 应收账款ID
     */
    Long createReceivable(Receivable receivable);

    /**
     * 更新应收账款
     *
     * @param receivable 应收账款信息
     * @return 是否更新成功
     */
    Boolean updateReceivable(Receivable receivable);

    /**
     * 删除应收账款
     *
     * @param id 应收账款ID
     * @return 是否删除成功
     */
    Boolean deleteReceivable(Long id);

    /**
     * 获取应收账款详情
     *
     * @param id 应收账款ID
     * @return 应收账款信息
     */
    ReceivableDetailVO getReceivable(Long id);

    /**
     * 更新应收账款状态
     *
     * @param id 应收账款ID
     * @param status 状态
     * @param remark 备注
     * @return 是否更新成功
     */
    Boolean updateReceivableStatus(Long id, ReceivableStatusEnum status, String remark);

    /**
     * 记录收款
     *
     * @param id 应收账款ID
     * @param amount 收款金额
     * @param accountId 收款账户ID
     * @param receiveDate 收款日期
     * @param remark 备注
     * @return 收款记录ID
     */
    Long recordReceipt(Long id, BigDecimal amount, Long accountId, 
                     LocalDateTime receiveDate, String remark);

    /**
     * 查询应收账款列表
     *
     * @param contractId 合同ID，可为null
     * @param clientId 客户ID，可为null
     * @param status 状态，可为null
     * @param overdueDays 逾期天数，大于该天数，可为null
     * @return 应收账款列表
     */
    List<ReceivableDetailVO> listReceivables(Long contractId, Long clientId, 
                                    ReceivableStatusEnum status, Integer overdueDays);

    /**
     * 分页查询应收账款
     *
     * @param page 分页参数
     * @param contractId 合同ID，可为null
     * @param clientId 客户ID，可为null
     * @param status 状态，可为null
     * @param overdueDays 逾期天数，大于该天数，可为null
     * @return 分页应收账款信息
     */
    IPage<ReceivableDetailVO> pageReceivables(Page<Receivable> page, Long contractId, Long clientId, 
                                    ReceivableStatusEnum status, Integer overdueDays);

    /**
     * 按合同查询应收账款
     *
     * @param contractId 合同ID
     * @return 应收账款列表
     */
    List<ReceivableDetailVO> listReceivablesByContract(Long contractId);

    /**
     * 按客户查询应收账款
     *
     * @param clientId 客户ID
     * @return 应收账款列表
     */
    List<ReceivableDetailVO> listReceivablesByClient(Long clientId);

    /**
     * 统计应收账款总额
     *
     * @param contractId 合同ID，可为null
     * @param clientId 客户ID，可为null
     * @param status 状态，可为null
     * @return 应收账款总额
     */
    BigDecimal sumReceivableAmount(Long contractId, Long clientId, ReceivableStatusEnum status);

    /**
     * 统计已收款总额
     *
     * @param contractId 合同ID，可为null
     * @param clientId 客户ID，可为null
     * @return 已收款总额
     */
    BigDecimal sumReceivedAmount(Long contractId, Long clientId);

    /**
     * 统计未收款总额
     *
     * @param contractId 合同ID，可为null
     * @param clientId 客户ID，可为null
     * @return 未收款总额
     */
    BigDecimal sumUnreceivedAmount(Long contractId, Long clientId);

    /**
     * 账龄分析
     *
     * @param clientId 客户ID，可为null
     * @return 账龄分析数据，key为账龄区间，value为金额
     */
    Map<String, BigDecimal> agingAnalysis(Long clientId);

    /**
     * 更新应收账款逾期状态
     * 系统每日自动调用此方法，检查应收账款是否逾期
     *
     * @return 更新的应收账款数量
     */
    Integer updateOverdueStatus();
} 