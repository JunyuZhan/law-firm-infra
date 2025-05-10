package com.lawfirm.model.archive.converter;

import com.lawfirm.model.archive.dto.ArchiveSyncRecordDTO;
import com.lawfirm.model.archive.entity.ArchiveSyncRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ArchiveSyncRecord与ArchiveSyncRecordDTO之间的转换器
 */
@Component("archiveSyncRecordConverter")
public class ArchiveSyncRecordConverter {

    /**
     * 将ArchiveSyncRecord转换为ArchiveSyncRecordDTO
     *
     * @param entity ArchiveSyncRecord
     * @return ArchiveSyncRecordDTO
     */
    public ArchiveSyncRecordDTO toDTO(ArchiveSyncRecord entity) {
        if (entity == null) {
            return null;
        }
        ArchiveSyncRecordDTO dto = new ArchiveSyncRecordDTO();
        BeanUtils.copyProperties(entity, dto);
        
        return dto;
    }

    /**
     * 将ArchiveSyncRecord列表转换为ArchiveSyncRecordDTO列表
     *
     * @param entityList ArchiveSyncRecord列表
     * @return ArchiveSyncRecordDTO列表
     */
    public List<ArchiveSyncRecordDTO> toDTOList(List<ArchiveSyncRecord> entityList) {
        if (entityList == null) {
            return null;
        }
        return entityList.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * 将ArchiveSyncRecordDTO转换为ArchiveSyncRecord
     *
     * @param dto ArchiveSyncRecordDTO
     * @return ArchiveSyncRecord
     */
    public ArchiveSyncRecord toEntity(ArchiveSyncRecordDTO dto) {
        if (dto == null) {
            return null;
        }
        ArchiveSyncRecord entity = new ArchiveSyncRecord();
        BeanUtils.copyProperties(dto, entity);
        
        return entity;
    }
} 