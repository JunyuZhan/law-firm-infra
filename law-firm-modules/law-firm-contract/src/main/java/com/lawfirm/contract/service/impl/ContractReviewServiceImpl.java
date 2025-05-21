package com.lawfirm.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.contract.dto.ContractReviewDTO;
import com.lawfirm.model.contract.dto.ContractReviewQueryDTO;
import com.lawfirm.model.contract.entity.ContractReview;
import com.lawfirm.model.contract.mapper.ContractReviewBaseMapper;
import com.lawfirm.model.contract.service.ContractReviewService;
import com.lawfirm.model.contract.vo.ContractReviewDetailVO;
import com.lawfirm.model.contract.vo.ContractReviewVO;
import com.lawfirm.contract.util.ContractReviewConverter;
import com.lawfirm.contract.constant.ContractConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import com.lawfirm.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.search.service.SearchService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 合同审核服务实现类
 */
@Slf4j
@Service("contractReviewService")
public class ContractReviewServiceImpl extends ServiceImpl<ContractReviewBaseMapper, ContractReview> implements ContractReviewService {

    /**
     * 注入core层审计服务，便于后续记录审核操作日志
     */
    @Autowired(required = false)
    @Qualifier("clientAuditService")
    private AuditService auditService;

    /**
     * 注入core层消息发送服务，便于后续审核相关通知等
     */
    @Autowired(required = false)
    @Qualifier("clientMessageSender")
    private MessageSender messageSender;

    /**
     * 注入core层文件存储服务，便于后续审核相关附件上传等
     */
    @Autowired(required = false)
    @Qualifier("clientFileService")
    private FileService fileService;

    /**
     * 注入core层存储桶服务
     */
    @Autowired(required = false)
    @Qualifier("clientBucketService")
    private BucketService bucketService;

    /**
     * 注入core层搜索服务
     */
    @Autowired(required = false)
    @Qualifier("clientSearchService")
    private SearchService searchService;

    @Override
    public boolean exists(QueryWrapper<ContractReview> queryWrapper) {
        return count(queryWrapper) > 0;
    }

    @Override
    public boolean save(ContractReview entity) {
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(List<ContractReview> entities) {
        return super.saveBatch(entities);
    }

    @Override
    public boolean update(ContractReview entity) {
        return updateById(entity);
    }

    @Override
    public boolean updateBatch(List<ContractReview> entities) {
        return updateBatchById(entities);
    }

    @Override
    public boolean remove(Long id) {
        return removeById(id);
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return removeByIds(ids);
    }

    @Override
    public ContractReview getById(Long id) {
        return super.getById(id);
    }

    @Override
    public List<ContractReview> list(QueryWrapper<ContractReview> wrapper) {
        return super.list(wrapper);
    }

    @Override
    public Page<ContractReview> page(Page<ContractReview> page, QueryWrapper<ContractReview> wrapper) {
        return super.page(page, wrapper);
    }

    @Override
    public long count(QueryWrapper<ContractReview> wrapper) {
        return super.count(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long submitReview(ContractReviewDTO reviewDTO) {
        log.info("提交合同审批: {}", reviewDTO);
        
        // 创建审核记录
        ContractReview review = new ContractReview();
        review.setContractId(reviewDTO.getContractId());
        review.setReviewer(reviewDTO.getReviewer());
        review.setReviewStatus(String.valueOf(ContractConstant.ReviewStatus.PENDING)); // 待审核状态
        review.setReviewComments(reviewDTO.getReviewComments());
        
        // 保存审核记录
        boolean saved = save(review);
        if (!saved) {
            log.error("保存合同审核记录失败: {}", reviewDTO);
            return null;
        }
        
        log.info("合同审核提交成功，ID: {}", review.getId());
        return review.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean approveReview(Long id, String comment) {
        log.info("审核通过: id={}, comment={}", id, comment);
        
        // 获取审核记录
        ContractReview review = getById(id);
        if (review == null) {
            log.error("审核记录不存在: {}", id);
            return false;
        }
        
        // 检查审核状态
        if (!String.valueOf(ContractConstant.ReviewStatus.PENDING).equals(review.getReviewStatus())) {
            log.error("审核状态不正确，无法通过: {}, 当前状态: {}", id, review.getReviewStatus());
            return false;
        }
        
        // 更新审核状态
        review.setReviewStatus(String.valueOf(ContractConstant.ReviewStatus.APPROVED)); // 已审核通过
        review.setReviewComments(comment);
        review.setReviewer(getCurrentUsername());
        
        // 保存审核结果
        boolean updated = updateById(review);
        if (!updated) {
            log.error("更新审核状态失败: {}", id);
            return false;
        }
        
        log.info("合同审核通过成功，ID: {}", id);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rejectReview(Long id, String comment) {
        log.info("审核拒绝: id={}, comment={}", id, comment);
        
        // 获取审核记录
        ContractReview review = getById(id);
        if (review == null) {
            log.error("审核记录不存在: {}", id);
            return false;
        }
        
        // 检查审核状态
        if (!String.valueOf(ContractConstant.ReviewStatus.PENDING).equals(review.getReviewStatus())) {
            log.error("审核状态不正确，无法拒绝: {}, 当前状态: {}", id, review.getReviewStatus());
            return false;
        }
        
        // 更新审核状态
        review.setReviewStatus(String.valueOf(ContractConstant.ReviewStatus.REJECTED)); // 已拒绝
        review.setReviewComments(comment);
        review.setReviewer(getCurrentUsername());
        
        // 保存审核结果
        boolean updated = updateById(review);
        if (!updated) {
            log.error("更新审核状态失败: {}", id);
            return false;
        }
        
        log.info("合同审核拒绝成功，ID: {}", id);
        return true;
    }

    @Override
    public ContractReviewDetailVO getReviewDetail(Long id) {
        log.info("获取审核详情: id={}", id);
        
        // 获取审核记录
        ContractReview review = getById(id);
        if (review == null) {
            log.error("审核记录不存在: {}", id);
            return null;
        }
        
        // 转换为详情VO
        ContractReviewDetailVO detailVO = new ContractReviewDetailVO();
        detailVO.setId(review.getId());
        detailVO.setContractId(review.getContractId());
        
        // 设置状态
        Integer status = null;
        if (String.valueOf(ContractConstant.ReviewStatus.PENDING).equals(review.getReviewStatus())) {
            status = ContractConstant.ReviewStatus.PENDING; // 待审核
        } else if (String.valueOf(ContractConstant.ReviewStatus.APPROVED).equals(review.getReviewStatus())) {
            status = ContractConstant.ReviewStatus.APPROVED; // 已通过
        } else if (String.valueOf(ContractConstant.ReviewStatus.REJECTED).equals(review.getReviewStatus())) {
            status = ContractConstant.ReviewStatus.REJECTED; // 已拒绝
        } else if (String.valueOf(ContractConstant.ReviewStatus.WITHDRAWN).equals(review.getReviewStatus())) {
            status = ContractConstant.ReviewStatus.WITHDRAWN; // 已撤回
        }
        detailVO.setStatus(status);
        
        // 添加审核记录
        ContractReviewDetailVO.ReviewRecord record = new ContractReviewDetailVO.ReviewRecord();
        record.setId(review.getId());
        record.setReviewerName(review.getReviewer());
        record.setStatus(status);
        record.setComment(review.getReviewComments());
        detailVO.setReviewRecords(java.util.Collections.singletonList(record));
        
        log.info("获取审核详情成功，ID: {}", id);
        return detailVO;
    }

    @Override
    public List<ContractReviewVO> listContractReviews(Long contractId) {
        log.info("查询合同审核历史: contractId={}", contractId);
        
        // 构建查询条件
        QueryWrapper<ContractReview> wrapper = new QueryWrapper<>();
        wrapper.eq("contract_id", contractId)
               .orderByDesc("create_time");
        
        // 查询审核记录
        List<ContractReview> reviews = list(wrapper);
        
        // 使用转换器将实体列表转换为VO列表
        return ContractReviewConverter.toVOList(reviews);
    }

    @Override
    public IPage<ContractReviewVO> pageReviews(Page<ContractReviewVO> page, ContractReviewQueryDTO queryDTO) {
        log.info("分页查询审核列表: page={}, queryDTO={}", page, queryDTO);
        QueryWrapper<ContractReview> wrapper = new QueryWrapper<>();
        if (queryDTO.getContractId() != null) {
            wrapper.eq("contract_id", queryDTO.getContractId());
        }
        if (queryDTO.getReviewerId() != null) {
            wrapper.eq("reviewer", queryDTO.getReviewerId());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq("review_status", queryDTO.getStatus());
        }
        wrapper.orderByDesc("create_time");
        Page<ContractReview> reviewPage = baseMapper.selectPage(new Page<>(page.getCurrent(), page.getSize()), wrapper);
        List<ContractReviewVO> voList = ContractReviewConverter.toVOList(reviewPage.getRecords());
        Page<ContractReviewVO> voPage = new Page<>(reviewPage.getCurrent(), reviewPage.getSize(), reviewPage.getTotal());
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean revokeReview(Long id, String reason) {
        log.info("撤销审核: id={}, reason={}", id, reason);
        ContractReview review = getById(id);
        if (review == null) {
            log.error("审核记录不存在: {}", id);
            return false;
        }
        review.setReviewStatus(String.valueOf(ContractConstant.ReviewStatus.WITHDRAWN));
        review.setReviewComments(reason);
        review.setReviewer(getCurrentUsername());
        boolean updated = updateById(review);
        if (!updated) {
            log.error("撤销审核失败: {}", id);
            return false;
        }
        log.info("合同审核撤销成功，ID: {}", id);
        return true;
    }

    @Override
    public boolean urgeReview(Long id) {
        log.info("催办审核: id={}", id);
        ContractReview review = getById(id);
        if (review == null) {
            log.error("审核记录不存在: {}", id);
            return false;
        }
        log.info("已向审核人({})发送催办通知", review.getReviewer());
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transferReview(Long id, Long newReviewerId, String reason) {
        log.info("转办审核: id={}, newReviewerId={}, reason={}", id, newReviewerId, reason);
        ContractReview review = getById(id);
        if (review == null) {
            log.error("审核记录不存在: {}", id);
            return false;
        }
        review.setReviewer(String.valueOf(newReviewerId));
        review.setReviewComments(reason);
        boolean updated = updateById(review);
        if (!updated) {
            log.error("转办审核失败: {}", id);
            return false;
        }
        log.info("合同审核转办成功，ID: {}", id);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reviewContract(ContractReviewDTO reviewDTO) {
        log.info("审核合同: {}", reviewDTO);
        Long reviewId = submitReview(reviewDTO);
        return reviewId != null;
    }

    @Override
    public List<ContractReview> listContractReviews() {
        log.info("查询合同审核列表");
        QueryWrapper<ContractReview> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        return list(wrapper);
    }

    @Override
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    
    @Override
    public Long getCurrentUserId() {
        return SecurityUtils.getUserId();
    }
    
    @Override
    public Long getCurrentTenantId() {
        // 如果系统支持多租户，则从SecurityContext中获取租户ID
        // 如果系统不支持多租户，则返回默认租户ID
        return 1L; // 默认返回租户ID为1
    }
} 