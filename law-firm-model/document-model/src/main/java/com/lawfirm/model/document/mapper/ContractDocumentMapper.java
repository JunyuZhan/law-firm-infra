package com.lawfirm.model.document.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.document.entity.business.ContractDocument;
import com.lawfirm.model.document.constant.DocumentSqlConstants;
import com.lawfirm.model.document.entity.base.BaseDocument;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 合同文档数据访问层
 */
@Mapper
public interface ContractDocumentMapper extends BaseMapper<ContractDocument> {
    // 继承自BaseMapper的基础方法已经满足大部分需求
    // 如需自定义方法，可在此添加
    
    /**
     * 根据合同ID查询文档
     *
     * @param contractId 合同ID
     * @return 文档列表
     */
    @Select(DocumentSqlConstants.Contract.SELECT_BY_CONTRACT_ID)
    List<BaseDocument> selectByContractId(@Param("contractId") Long contractId);
} 