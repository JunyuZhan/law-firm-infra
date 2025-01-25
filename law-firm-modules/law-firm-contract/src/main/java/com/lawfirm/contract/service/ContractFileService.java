package com.lawfirm.contract.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.contract.entity.ContractFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 合同文件服务接口
 */
public interface ContractFileService extends IService<ContractFile> {

    /**
     * 上传合同文件
     *
     * @param contractId 合同ID
     * @param file      文件
     * @return 文件ID
     */
    Long uploadFile(Long contractId, MultipartFile file);

    /**
     * 更新合同文件
     *
     * @param contractId 合同ID
     * @param file      文件
     * @return 文件ID
     */
    Long updateFile(Long contractId, MultipartFile file);

    /**
     * 删除合同文件
     *
     * @param id 文件ID
     */
    void deleteFile(Long id);

    /**
     * 获取合同文件列表
     *
     * @param contractId 合同ID
     * @return 文件列表
     */
    List<ContractFile> getContractFiles(Long contractId);

    /**
     * 获取合同最新文件
     *
     * @param contractId 合同ID
     * @return 文件信息
     */
    ContractFile getLatestFile(Long contractId);

    /**
     * 获取合同文件历史版本
     *
     * @param contractId 合同ID
     * @return 文件历史版本列表
     */
    List<ContractFile> getFileHistory(Long contractId);

    /**
     * 下载合同文件
     *
     * @param id 文件ID
     * @return 文件字节数组
     */
    byte[] downloadFile(Long id);
} 