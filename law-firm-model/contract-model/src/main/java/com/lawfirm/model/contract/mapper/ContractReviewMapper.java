package com.lawfirm.model.contract.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lawfirm.model.contract.entity.ContractApproval;
import com.lawfirm.model.contract.vo.ContractReviewDetailVO;

/**
 * 合同审核数据转换接口
 */
@Mapper
public interface ContractReviewMapper {
    
    ContractReviewMapper INSTANCE = Mappers.getMapper(ContractReviewMapper.class);
    
    /**
     * 获取节点名称
     */
    default String getNodeName(Integer node) {
        if (node == null) {
            return null;
        }
        switch (node) {
            case 1:
                return "部门负责人";
            case 2:
                return "分所负责人";
            case 3:
                return "法务审核";
            case 4:
                return "财务审核";
            default:
                return "未知节点";
        }
    }
    
    /**
     * 合同审批实体转换为审核详情VO的审核记录
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "node", target = "node")
    @Mapping(source = "node", target = "nodeName", expression = "java(getNodeName(approval.getNode()))")
    @Mapping(source = "approverName", target = "reviewerName")
    @Mapping(source = "approverId", target = "reviewerId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "comment", target = "comment")
    @Mapping(source = "approvalTime", target = "reviewTime")
    ContractReviewDetailVO.ReviewRecord toReviewRecord(ContractApproval approval);
} 