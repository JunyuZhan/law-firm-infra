package com.lawfirm.model.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.client.entity.common.ClientContact;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 联系人Mapper接口
 */
@Mapper
public interface ContactMapper extends BaseMapper<ClientContact> {
    /**
     * 获取客户的所有联系人
     *
     * @param clientId 客户ID
     * @return 联系人列表
     */
    List<ClientContact> selectByClientId(@Param("clientId") Long clientId);
    
    /**
     * 获取客户的默认联系人
     *
     * @param clientId 客户ID
     * @return 默认联系人
     */
    ClientContact selectDefaultByClientId(@Param("clientId") Long clientId);
} 