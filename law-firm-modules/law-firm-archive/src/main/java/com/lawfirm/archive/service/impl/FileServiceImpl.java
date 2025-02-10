package com.lawfirm.archive.service.impl;

import com.lawfirm.archive.service.FileService;
import com.lawfirm.model.base.storage.model.FileMetadata;
import com.lawfirm.model.base.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * 档案文件服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final StorageService storageService;

    private static final String BUSINESS_TYPE = "archive";

    @Override
    public FileMetadata uploadFile(MultipartFile file, String archiveId) {
        return storageService.upload(file, BUSINESS_TYPE, archiveId);
    }

    @Override
    public InputStream downloadFile(String fileId) {
        return storageService.download(fileId);
    }

    @Override
    public void deleteFile(String fileId) {
        storageService.delete(fileId);
    }

    @Override
    public List<FileMetadata> listFiles(String archiveId) {
        return storageService.listByBusiness(BUSINESS_TYPE, archiveId);
    }
} 