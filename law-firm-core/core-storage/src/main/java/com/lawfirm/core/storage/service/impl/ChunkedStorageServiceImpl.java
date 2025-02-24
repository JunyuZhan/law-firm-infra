package com.lawfirm.core.storage.service.impl;

import com.lawfirm.model.base.storage.entity.ChunkMetadataEntity;
import com.lawfirm.model.base.storage.entity.FileMetadataEntity;
import com.lawfirm.model.base.storage.repository.ChunkMetadataRepository;
import com.lawfirm.model.base.storage.service.BusinessChunkedStorageService;
import com.lawfirm.core.storage.strategy.StorageStrategy;
import com.lawfirm.model.base.storage.model.FileMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 业务分片存储服务实现类
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class ChunkedStorageServiceImpl implements BusinessChunkedStorageService {

    @Autowired
    private StorageStrategy storageStrategy;

    @Autowired
    private ChunkMetadataRepository chunkMetadataRepository;

    @Override
    @Transactional
    public boolean uploadChunk(ChunkMetadataEntity metadata, byte[] chunkData) {
        try {
            // 上传分片
            String chunkPath = String.format("chunks/%s/%d", metadata.getIdentifier(), metadata.getChunkNumber());
            try (InputStream inputStream = new ByteArrayInputStream(chunkData)) {
                FileMetadata fileMetadata = storageStrategy.uploadFile(
                    metadata.getFilename(),
                    inputStream,
                    chunkData.length,
                    metadata.getContentType()
                );
                metadata.setPath(fileMetadata.getPath());
            }
            
            metadata.setCreateTime(LocalDateTime.now());
            metadata.setUpdateTime(LocalDateTime.now());
            chunkMetadataRepository.save(metadata);

            // 检查是否所有分片都已上传
            if (isUploadComplete(metadata.getIdentifier(), metadata.getTotalChunks())) {
                mergeChunks(metadata.getIdentifier(), metadata.getFilename(), metadata.getContentType());
            }
            return true;
        } catch (Exception e) {
            log.error("上传分片失败", e);
            return false;
        }
    }

    @Override
    public boolean checkChunk(String identifier, Integer chunkNumber) {
        return chunkMetadataRepository.findByIdentifierAndChunkNumber(identifier, chunkNumber) != null;
    }

    @Override
    @Transactional
    public FileMetadataEntity mergeChunks(String identifier, String fileName, String contentType) {
        try {
            List<ChunkMetadataEntity> chunks = chunkMetadataRepository.findByIdentifierOrderByChunkNumber(identifier);
            if (chunks.isEmpty()) {
                throw new RuntimeException("未找到分片文件");
            }

            // 合并所有分片
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            for (ChunkMetadataEntity chunk : chunks) {
                byte[] chunkData = storageStrategy.downloadFile(chunk.getPath());
                outputStream.write(chunkData);
            }

            // 上传合并后的文件
            byte[] mergedData = outputStream.toByteArray();
            FileMetadata fileMetadata;
            try (InputStream inputStream = new ByteArrayInputStream(mergedData)) {
                fileMetadata = storageStrategy.uploadFile(fileName, inputStream, mergedData.length, contentType);
            }

            // 删除分片
            cleanChunks(identifier);

            // 创建文件元数据实体
            FileMetadataEntity entity = new FileMetadataEntity();
            entity.setFilename(fileMetadata.getFilename());
            entity.setOriginalFilename(fileMetadata.getOriginalFilename());
            entity.setSize(fileMetadata.getSize());
            entity.setContentType(fileMetadata.getContentType());
            entity.setPath(fileMetadata.getPath());
            entity.setCreateTime(LocalDateTime.now());
            entity.setUpdateTime(LocalDateTime.now());
            return entity;
        } catch (IOException e) {
            log.error("合并分片失败", e);
            throw new RuntimeException("合并分片失败", e);
        }
    }

    @Override
    @Transactional
    public void cleanChunks(String identifier) {
        try {
            List<ChunkMetadataEntity> chunks = chunkMetadataRepository.findByIdentifierOrderByChunkNumber(identifier);
            for (ChunkMetadataEntity chunk : chunks) {
                storageStrategy.deleteFile(chunk.getPath());
                chunkMetadataRepository.delete(chunk);
            }
        } catch (Exception e) {
            log.error("清理分片失败", e);
            throw new RuntimeException("清理分片失败", e);
        }
    }

    private boolean isUploadComplete(String identifier, int totalChunks) {
        long count = chunkMetadataRepository.countByIdentifier(identifier);
        return count == totalChunks;
    }
} 