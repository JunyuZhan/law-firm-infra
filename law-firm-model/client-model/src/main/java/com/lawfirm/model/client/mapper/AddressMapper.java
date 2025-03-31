package com.lawfirm.model.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.client.entity.common.ClientAddress;
import com.lawfirm.model.client.constant.ClientSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 客户地址Mapper接口
 */
@Mapper
public interface AddressMapper extends BaseMapper<ClientAddress> {
    
    /**
     * 获取客户的所有地址
     *
     * @param clientId 客户ID
     * @return 地址列表
     */
    @Select(ClientSqlConstants.Address.SELECT_BY_CLIENT_ID)
    List<ClientAddress> selectByClientId(@Param("clientId") Long clientId);
    
    /**
     * 获取客户的默认地址
     *
     * @param clientId 客户ID
     * @param addressType 地址类型(可选)
     * @return 默认地址
     */
    @Select(ClientSqlConstants.Address.SELECT_DEFAULT_BY_CLIENT_ID)
    ClientAddress selectDefaultAddress(@Param("clientId") Long clientId, @Param("addressType") Integer addressType);
    
    /**
     * 更新客户地址为默认/非默认
     *
     * @param id 地址ID
     * @param isDefault 是否默认(0-否,1-是)
     * @return 影响行数
     */
    @Update("UPDATE client_address SET is_default = #{isDefault} WHERE id = #{id}")
    int updateDefaultStatus(@Param("id") Long id, @Param("isDefault") Integer isDefault);
} 