package com.lawfirm.model.client.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.client.entity.base.Client;
import com.lawfirm.model.client.constant.ClientSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 客户Mapper接口
 */
@Mapper
public interface ClientMapper extends BaseMapper<Client> {
    
    /**
     * 根据客户编号查询客户
     *
     * @param clientNo 客户编号
     * @return 客户信息
     */
    @Select(ClientSqlConstants.Client.SELECT_BY_CLIENT_NO)
    Client selectByClientNo(@Param("clientNo") String clientNo);
    
    /**
     * 根据客户名称模糊查询
     *
     * @param name 客户名称
     * @return 客户列表
     */
    @Select(ClientSqlConstants.Client.SELECT_BY_NAME)
    List<Client> selectByName(@Param("name") String name);
} 