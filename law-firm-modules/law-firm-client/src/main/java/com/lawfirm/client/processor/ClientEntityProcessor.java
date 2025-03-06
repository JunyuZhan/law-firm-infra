package com.lawfirm.client.processor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lawfirm.client.exception.ClientException;
import com.lawfirm.client.mapper.ClientMapper;
import com.lawfirm.common.data.processor.EntityProcessor;
import com.lawfirm.model.client.entity.Client;
import com.lawfirm.model.client.dto.ClientDTO;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 客户实体处理器
 */
@Component
public class ClientEntityProcessor implements EntityProcessor<Client> {
    
    @Autowired
    private ClientMapper clientMapper;

    @Override
    public void beforeCreate(Client entity) {
        // 检查客户编号是否已存在
        if (checkClientNumberExists(entity.getClientNumber())) {
            throw new ClientException("客户编号已存在");
        }
        // 检查证件号码是否已存在
        if (entity.getIdNumber() != null && checkIdNumberExists(entity.getIdNumber())) {
            throw new ClientException("证件号码已存在");
        }
    }

    @Override
    public void beforeUpdate(Client entity) {
        Client existingClient = clientMapper.selectById(entity.getId());
        if (existingClient == null) {
            throw new ClientException("客户不存在");
        }
        
        // 如果客户编号发生变化，检查新的客户编号是否已存在
        if (!existingClient.getClientNumber().equals(entity.getClientNumber()) 
                && checkClientNumberExists(entity.getClientNumber())) {
            throw new ClientException("客户编号已存在");
        }
        
        // 如果证件号码发生变化，检查新的证件号码是否已存在
        if (entity.getIdNumber() != null 
                && !entity.getIdNumber().equals(existingClient.getIdNumber())
                && checkIdNumberExists(entity.getIdNumber())) {
            throw new ClientException("证件号码已存在");
        }
    }

    private boolean checkClientNumberExists(String clientNumber) {
        return clientMapper.selectCount(new QueryWrapper<Client>()
            .eq("client_number", clientNumber)) > 0;
    }

    private boolean checkIdNumberExists(String idNumber) {
        return clientMapper.selectCount(new QueryWrapper<Client>()
            .eq("id_number", idNumber)) > 0;
    }
} 