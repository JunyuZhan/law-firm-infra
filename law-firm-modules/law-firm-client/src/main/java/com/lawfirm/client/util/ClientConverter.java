package com.lawfirm.client.util;

import com.lawfirm.model.client.entity.base.Client;
import com.lawfirm.model.client.dto.client.ClientCreateDTO;
import com.lawfirm.model.client.dto.client.ClientUpdateDTO;
import com.lawfirm.model.client.vo.ClientVO;
import com.lawfirm.model.client.dto.ClientDTO;
import com.lawfirm.model.client.enums.ClientTypeEnum;
import com.lawfirm.model.client.enums.ClientLevelEnum;
import com.lawfirm.model.client.enums.ClientSourceEnum;

/**
 * 客户对象转换工具类
 */
public class ClientConverter {

    /**
     * 将创建DTO转换为实体
     * @param dto 创建DTO
     * @return 客户实体
     */
    public static Client toEntity(ClientCreateDTO dto) {
        if (dto == null) return null;
        Client entity = new Client();
        entity.setClientName(dto.getClientName());
        entity.setClientType(dto.getClientType());
        entity.setClientLevel(dto.getClientLevel());
        entity.setClientSource(dto.getClientSource());
        entity.setIdType(dto.getIdType());
        entity.setIdNumber(dto.getIdNumber());
        entity.setPhone(dto.getContactPhone());
        entity.setEmail(dto.getEmail());
        entity.setLegalRepresentative(dto.getLegalRepresentative());
        entity.setUnifiedSocialCreditCode(dto.getUnifiedSocialCreditCode());
        return entity;
    }
    
    /**
     * 将更新DTO应用到实体
     * @param entity 实体
     * @param dto 更新DTO
     */
    public static void updateEntity(Client entity, ClientUpdateDTO dto) {
        if (entity == null || dto == null) return;
        if (dto.getClientName() != null) entity.setClientName(dto.getClientName());
        if (dto.getClientType() != null) entity.setClientType(dto.getClientType());
        if (dto.getClientLevel() != null) entity.setClientLevel(dto.getClientLevel());
        if (dto.getClientSource() != null) entity.setClientSource(dto.getClientSource());
        if (dto.getIdType() != null) entity.setIdType(dto.getIdType());
        if (dto.getIdNumber() != null) entity.setIdNumber(dto.getIdNumber());
        if (dto.getContactPhone() != null) entity.setPhone(dto.getContactPhone());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        if (dto.getCreditLevel() != null) entity.setCreditLevel(dto.getCreditLevel());
        if (dto.getLegalRepresentative() != null) entity.setLegalRepresentative(dto.getLegalRepresentative());
        if (dto.getUnifiedSocialCreditCode() != null) entity.setUnifiedSocialCreditCode(dto.getUnifiedSocialCreditCode());
    }
    
    /**
     * 将实体转换为视图对象
     * @param entity 实体
     * @return 视图对象
     */
    public static ClientVO toVO(Client entity) {
        if (entity == null) return null;
        ClientVO vo = new ClientVO();
        vo.setClientNo(entity.getClientNo());
        vo.setClientName(entity.getClientName());
        vo.setClientType(entity.getClientType());
        vo.setClientLevel(entity.getClientLevel());
        vo.setClientSource(entity.getClientSource());
        vo.setIdType(entity.getIdType());
        vo.setIdNumber(entity.getIdNumber());
        vo.setContactPhone(entity.getPhone());
        vo.setEmail(entity.getEmail());
        vo.setCreditLevel(entity.getCreditLevel());
        vo.setLegalRepresentative(entity.getLegalRepresentative());
        vo.setUnifiedSocialCreditCode(entity.getUnifiedSocialCreditCode());
        // 枚举名称映射
        ClientTypeEnum typeEnum = ClientTypeEnum.getByValue(entity.getClientType());
        vo.setClientTypeName(typeEnum != null ? typeEnum.getDesc() : null);
        ClientLevelEnum levelEnum = ClientLevelEnum.getByValue(entity.getClientLevel());
        vo.setClientLevelName(levelEnum != null ? levelEnum.getDesc() : null);
        ClientSourceEnum sourceEnum = ClientSourceEnum.getByValue(entity.getClientSource());
        vo.setClientSourceName(sourceEnum != null ? sourceEnum.getDesc() : null);
        // 其他字段可根据需要补充
        return vo;
    }

    /**
     * 将实体转换为DTO对象
     * @param entity 实体
     * @return DTO对象
     */
    public static ClientDTO toDTO(Client entity) {
        if (entity == null) {
            return null;
        }
        
        ClientDTO dto = new ClientDTO();
        dto.setId(entity.getId());
        dto.setClientNo(entity.getClientNo());
        dto.setClientName(entity.getClientName());
        dto.setClientType(entity.getClientType());
        dto.setClientLevel(entity.getClientLevel());
        dto.setClientSource(entity.getClientSource());
        dto.setIndustry(entity.getIndustry());
        dto.setScale(entity.getScale());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setManagerId(entity.getManagerId());
        dto.setStatus(entity.getStatus());
        dto.setIdType(entity.getIdType());
        dto.setIdNumber(entity.getIdNumber());
        dto.setCreditLevel(entity.getCreditLevel());
        dto.setLegalRepresentative(entity.getLegalRepresentative());
        dto.setUnifiedSocialCreditCode(entity.getUnifiedSocialCreditCode());
        dto.setCaseCount(entity.getCaseCount());
        dto.setActiveCaseCount(entity.getActiveCaseCount());
        
        return dto;
    }
}
