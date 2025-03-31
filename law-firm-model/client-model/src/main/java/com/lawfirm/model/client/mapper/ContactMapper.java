package com.lawfirm.model.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.client.entity.common.ClientContact;
import com.lawfirm.model.client.constant.ClientSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    @Select(ClientSqlConstants.Contact.SELECT_BY_CLIENT_ID)
    List<ClientContact> selectByClientId(@Param("clientId") Long clientId);
    
    /**
     * 获取客户的默认联系人
     *
     * @param clientId 客户ID
     * @return 默认联系人
     */
    @Select(ClientSqlConstants.Contact.SELECT_DEFAULT_BY_CLIENT_ID)
    ClientContact selectDefaultByClientId(@Param("clientId") Long clientId);
} 