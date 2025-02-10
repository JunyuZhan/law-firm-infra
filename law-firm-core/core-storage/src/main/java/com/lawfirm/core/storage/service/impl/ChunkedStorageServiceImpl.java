package com.lawfirm.core.storage.service.impl;

import com.lawfirm.model.base.storage.entity.ChunkMetadataEntity;
import com.lawfirm.model.base.storage.repository.ChunkMetadataRepository;
import com.lawfirm.model.base.storage.service.BusinessChunkedStorageService;
import com.lawfirm.core.storage.strategy.StorageStrategy;
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
    public void uploadChunk(MultipartFile chunk, String identifier, int chunkNumber, int totalChunks) {
        try {
            // 保存分片元数据
            ChunkMetadataEntity metadata = new ChunkMetadataEntity();
            metadata.setIdentifier(identifier);
            metadata.setChunkNumber(chunkNumber);
            metadata.setTotalChunks(totalChunks);
            metadata.setSize(chunk.getSize());
            metadata.setFilename(chunk.getOriginalFilename());
            metadata.setContentType(chunk.getContentType());
            metadata.setCreateTime(LocalDateTime.now());
            metadata.setUpdateTime(LocalDateTime.now());

            // 上传分片
            String chunkPath = String.format("chunks/%s/%d", identifier, chunkNumber);
            metadata.setPath(storageStrategy.uploadFile(chunk).getFilePath());

            chunkMetadataRepository.save(metadata);

            // 检查是否所有分片都已上传
            if (isUploadComplete(identifier, totalChunks)) {
                mergeChunks(identifier, totalChunks);
            }
        } catch (Exception e) {
            log.error("上传分片失败", e);
            throw new RuntimeException("上传分片失败", e);
        }
    }

    @Override
    public boolean isUploadComplete(String identifier, int totalChunks) {
        long count = chunkMetadataRepository.countByIdentifier(identifier);
        return count == totalChunks;
    }

    @Override
    @Transactional
    public void mergeChunks(String identifier, int totalChunks) {
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
            String filename = chunks.get(0).getFilename();
            String contentType = chunks.get(0).getContentType();
            byte[] mergedData = outputStream.toByteArray();
            try (InputStream inputStream = new ByteArrayInputStream(mergedData)) {
                storageStrategy.uploadFile(filename, inputStream, mergedData.length, contentType);
            }

            // 删除分片
            for (ChunkMetadataEntity chunk : chunks) {
                storageStrategy.deleteFile(chunk.getPath());
                chunkMetadataRepository.delete(chunk);
            }
        } catch (IOException e) {
            log.error("合并分片失败", e);
            throw new RuntimeException("合并分片失败", e);
        }
    }
} 