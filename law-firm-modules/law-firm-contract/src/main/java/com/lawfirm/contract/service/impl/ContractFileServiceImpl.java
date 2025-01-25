package com.lawfirm.contract.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.contract.entity.Contract;
import com.lawfirm.contract.entity.ContractFile;
import com.lawfirm.contract.exception.ContractException;
import com.lawfirm.contract.mapper.ContractFileMapper;
import com.lawfirm.contract.service.ContractFileService;
import com.lawfirm.contract.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * 合同文件服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContractFileServiceImpl extends ServiceImpl<ContractFileMapper, ContractFile> implements ContractFileService {

    private final ContractService contractService;

    @Value("${contract.file.upload-path}")
    private String uploadPath;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long uploadFile(Long contractId, MultipartFile file) {
        // 校验合同是否存在
        Contract contract = contractService.getById(contractId);
        if (contract == null) {
            throw new ContractException("合同不存在");
        }

        try {
            // 生成文件存储路径
            String relativePath = generateFilePath(contractId, file.getOriginalFilename());
            Path targetPath = Paths.get(uploadPath, relativePath);
            Files.createDirectories(targetPath.getParent());

            // 保存文件
            file.transferTo(targetPath.toFile());

            // 创建文件记录
            ContractFile contractFile = new ContractFile();
            contractFile.setContractId(contractId);
            contractFile.setFileName(file.getOriginalFilename());
            contractFile.setFilePath(relativePath);
            contractFile.setFileSize(file.getSize());
            contractFile.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
            contractFile.setVersion(1);
            contractFile.setIsLatest(true);

            save(contractFile);
            return contractFile.getId();
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new ContractException("文件上传失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long updateFile(Long contractId, MultipartFile file) {
        // 校验合同是否存在
        Contract contract = contractService.getById(contractId);
        if (contract == null) {
            throw new ContractException("合同不存在");
        }

        // 获取最新版本
        ContractFile latestFile = getLatestFile(contractId);
        if (latestFile == null) {
            throw new ContractException("合同文件不存在");
        }

        try {
            // 生成文件存储路径
            String relativePath = generateFilePath(contractId, file.getOriginalFilename());
            Path targetPath = Paths.get(uploadPath, relativePath);
            Files.createDirectories(targetPath.getParent());

            // 保存文件
            file.transferTo(targetPath.toFile());

            // 更新旧版本状态
            latestFile.setIsLatest(false);
            updateById(latestFile);

            // 创建新版本记录
            ContractFile contractFile = new ContractFile();
            contractFile.setContractId(contractId);
            contractFile.setFileName(file.getOriginalFilename());
            contractFile.setFilePath(relativePath);
            contractFile.setFileSize(file.getSize());
            contractFile.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
            contractFile.setVersion(latestFile.getVersion() + 1);
            contractFile.setIsLatest(true);

            save(contractFile);
            return contractFile.getId();
        } catch (IOException e) {
            log.error("文件更新失败", e);
            throw new ContractException("文件更新失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(Long id) {
        // 校验文件是否存在
        ContractFile contractFile = getById(id);
        if (contractFile == null) {
            throw new ContractException("文件不存在");
        }

        // 删除物理文件
        try {
            Path filePath = Paths.get(uploadPath, contractFile.getFilePath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("文件删除失败", e);
            throw new ContractException("文件删除失败");
        }

        // 删除文件记录
        removeById(id);
    }

    @Override
    public List<ContractFile> getContractFiles(Long contractId) {
        LambdaQueryWrapper<ContractFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContractFile::getContractId, contractId)
                .orderByDesc(ContractFile::getVersion);
        return list(wrapper);
    }

    @Override
    public ContractFile getLatestFile(Long contractId) {
        LambdaQueryWrapper<ContractFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContractFile::getContractId, contractId)
                .eq(ContractFile::getIsLatest, true);
        return getOne(wrapper);
    }

    @Override
    public List<ContractFile> getFileHistory(Long contractId) {
        LambdaQueryWrapper<ContractFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ContractFile::getContractId, contractId)
                .orderByDesc(ContractFile::getVersion);
        return list(wrapper);
    }

    @Override
    public byte[] downloadFile(Long id) {
        // 校验文件是否存在
        ContractFile contractFile = getById(id);
        if (contractFile == null) {
            throw new ContractException("文件不存在");
        }

        try {
            Path filePath = Paths.get(uploadPath, contractFile.getFilePath());
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.error("文件下载失败", e);
            throw new ContractException("文件下载失败");
        }
    }

    /**
     * 生成文件存储路径
     *
     * @param contractId 合同ID
     * @param fileName   文件名
     * @return 相对路径
     */
    private String generateFilePath(Long contractId, String fileName) {
        LocalDateTime now = LocalDateTime.now();
        String datePath = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String extension = FilenameUtils.getExtension(fileName);
        return String.format("contract/%s/%d/%s.%s", datePath, contractId, uuid, extension);
    }
} 