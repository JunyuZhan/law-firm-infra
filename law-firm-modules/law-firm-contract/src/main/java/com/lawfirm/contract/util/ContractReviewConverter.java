package com.lawfirm.contract.util;

import com.lawfirm.model.contract.entity.ContractReview;
import com.lawfirm.model.contract.vo.ContractReviewVO;
import com.lawfirm.contract.constant.ContractConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 合同审核转换器
 */
public class ContractReviewConverter {

    /**
     * 将实体转换为VO
     */
    public static ContractReviewVO toVO(ContractReview entity) {
        if (entity == null) {
            return null;
        }
        
        ContractReviewVO vo = new ContractReviewVO();
        BeanUtils.copyProperties(entity, vo);
        
        // 设置状态名称
        vo.setStatusName(getReviewStatusName(entity.getReviewStatus()));
        
        // 设置结果名称
        vo.setResultName(getReviewResultName(entity.getReviewComments()));
        
        return vo;
    }

    /**
     * 将实体列表转换为VO列表
     */
    public static List<ContractReviewVO> toVOList(List<ContractReview> entityList) {
        if (entityList == null) {
            return null;
        }
        return entityList.stream()
                .map(ContractReviewConverter::toVO)
                .collect(Collectors.toList());
    }

    /**
     * 获取审核状态名称
     */
    private static String getReviewStatusName(String status) {
        if (!StringUtils.hasText(status)) {
            return "";
        }
        try {
            int statusValue = Integer.parseInt(status);
            switch (statusValue) {
                case ContractConstant.ReviewStatus.PENDING:
                    return "待审核";
                case ContractConstant.ReviewStatus.APPROVED:
                    return "已通过";
                case ContractConstant.ReviewStatus.REJECTED:
                    return "已拒绝";
                case ContractConstant.ReviewStatus.WITHDRAWN:
                    return "已撤回";
                default:
                    return "未知状态";
            }
        } catch (NumberFormatException e) {
            return "状态格式错误";
        }
    }

    /**
     * 获取审核结果名称
     */
    private static String getReviewResultName(String comments) {
        if (!StringUtils.hasText(comments)) {
            return "未审核";
        }
        // 根据审核意见判断结果
        if (comments.contains("通过") || comments.contains("同意")) {
            return "通过";
        } else if (comments.contains("驳回") || comments.contains("拒绝")) {
            return "驳回";
        } else if (comments.contains("修改") || comments.contains("完善")) {
            return "退回修改";
        }
        return "未审核";
    }
} 