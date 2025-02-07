package com.lawfirm.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.model.system.dto.SysUpgradeDTO;
import com.lawfirm.model.system.dto.UpgradePackageDTO;
import com.lawfirm.model.system.entity.SysUpgrade;
import com.lawfirm.model.system.vo.SysUpgradeVO;
import com.lawfirm.model.system.vo.UpgradeLogVO;
import com.lawfirm.model.system.vo.UpgradePackageVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 系统升级Service接口
 */
public interface SysUpgradeService extends BaseService<SysUpgrade, SysUpgradeVO> {

    /**
     * 上传升级包
     */
    SysUpgradeVO upload(UpgradePackageDTO packageDTO);

    /**
     * 分页查询升级记录
     */
    PageResult<SysUpgradeVO> page(Integer pageNum, Integer pageSize);

    /**
     * 执行升级
     */
    void execute(Long id);

    /**
     * 分页查询升级日志
     */
    PageResult<SysUpgradeVO> log(Integer pageNum, Integer pageSize);

    /**
     * 上传升级包
     *
     * @param file 升级包文件
     * @param dto  升级包信息
     * @return 升级包VO
     */
    UpgradePackageVO uploadPackage(MultipartFile file, UpgradePackageDTO dto);
    
    /**
     * 获取升级包列表
     *
     * @param page 分页参数
     * @return 升级包VO分页
     */
    Page<UpgradePackageVO> getPackages(Page<UpgradePackageVO> page);
    
    /**
     * 执行升级
     *
     * @param packageId 升级包ID
     */
    void executeUpgrade(Long packageId);
    
    /**
     * 回滚升级
     *
     * @param packageId 升级包ID
     */
    void rollbackUpgrade(Long packageId);
    
    /**
     * 获取升级日志
     *
     * @param packageId 升级包ID
     * @return 升级日志VO列表
     */
    List<UpgradeLogVO> getLogs(Long packageId);

    /**
     * DTO转换为VO
     */
    SysUpgradeVO toUpgradeVO(SysUpgradeDTO dto);

    /**
     * DTO列表转换为VO列表
     */
    List<SysUpgradeVO> toUpgradeVOList(List<SysUpgradeDTO> dtoList);
} 