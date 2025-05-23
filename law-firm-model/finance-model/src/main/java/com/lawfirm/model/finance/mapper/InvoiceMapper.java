package com.lawfirm.model.finance.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.finance.entity.Invoice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 发票Mapper接口
 */
@Mapper
public interface InvoiceMapper extends BaseMapper<Invoice> {
} 