package com.lawfirm.model.archive.converter;

import com.lawfirm.model.archive.dto.SyncConfigDTO;
import com.lawfirm.model.archive.entity.SyncConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * SyncConfig与SyncConfigDTO之间的转换器
 */
@Component("syncConfigConverter")
public class SyncConfigConverter {

    /**
     * 将SyncConfigDTO转换为SyncConfig（用于新建）
     *
     * @param dto SyncConfigDTO
     * @return SyncConfig
     */
    public SyncConfig toEntity(SyncConfigDTO dto) {
        if (dto == null) {
            return null;
        }
        SyncConfig entity = new SyncConfig();
        // 使用BeanUtils进行属性拷贝
        BeanUtils.copyProperties(dto, entity);
        // 这里不设置ID，因为ID应由数据库生成或在持久化前设置
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        
        // 处理Boolean和Integer之间的转换
        if (dto.getEnabled() != null) {
            entity.setEnabled(dto.getEnabled());
        }
        
        return entity;
    }

    /**
     * 使用现有SyncConfig更新属性（用于更新）
     *
     * @param dto 源DTO
     * @param entity 目标实体
     * @return 更新后的实体
     */
    public SyncConfig updateEntity(SyncConfigDTO dto, SyncConfig entity) {
        if (dto == null || entity == null) {
            return entity;
        }
        
        // 使用BeanUtils进行属性拷贝，忽略ID和创建时间
        BeanUtils.copyProperties(dto, entity, "id", "createTime");
        entity.setUpdateTime(LocalDateTime.now());
        
        // 处理Boolean和Integer之间的转换
        if (dto.getEnabled() != null) {
            entity.setEnabled(dto.getEnabled());
        }
        
        return entity;
    }

    /**
     * 将SyncConfig转换为SyncConfigDTO
     *
     * @param entity SyncConfig
     * @return SyncConfigDTO
     */
    public SyncConfigDTO toDTO(SyncConfig entity) {
        if (entity == null) {
            return null;
        }
        SyncConfigDTO dto = new SyncConfigDTO();
        BeanUtils.copyProperties(entity, dto);
        
        return dto;
    }

    /**
     * 将SyncConfig列表转换为SyncConfigDTO列表
     *
     * @param entityList SyncConfig列表
     * @return SyncConfigDTO列表
     */
    public List<SyncConfigDTO> toDTOList(List<SyncConfig> entityList) {
        if (entityList == null) {
            return null;
        }
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
} 