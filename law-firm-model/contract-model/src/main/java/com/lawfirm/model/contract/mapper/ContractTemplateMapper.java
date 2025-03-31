package com.lawfirm.model.contract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.contract.entity.ContractTemplate;
import com.lawfirm.model.contract.constant.ContractSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 合同模板Mapper接口
 */
@Mapper
public interface ContractTemplateMapper extends BaseMapper<ContractTemplate> {
    
    /**
     * 根据模板编码查询模板
     *
     * @param templateCode 模板编码
     * @return 模板信息
     */
    @Select(ContractSqlConstants.Template.SELECT_BY_TEMPLATE_CODE)
    ContractTemplate selectByTemplateCode(@Param("templateCode") String templateCode);
    
    /**
     * 查询分类下的模板列表
     *
     * @param categoryId 分类ID
     * @return 模板列表
     */
    @Select(ContractSqlConstants.Template.SELECT_BY_CATEGORY_ID)
    List<ContractTemplate> selectByCategoryId(@Param("categoryId") Long categoryId);
} 