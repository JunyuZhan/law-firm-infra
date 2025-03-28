package com.lawfirm.api.adaptor.system;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.system.dto.upgrade.PatchCreateDTO;
import com.lawfirm.model.system.dto.upgrade.PatchQueryDTO;
import com.lawfirm.model.system.dto.upgrade.UpgradeCreateDTO;
import com.lawfirm.model.system.dto.upgrade.UpgradeQueryDTO;
import com.lawfirm.model.system.dto.upgrade.UpgradeUpdateDTO;
import com.lawfirm.model.system.service.UpgradeService;
import com.lawfirm.model.system.vo.upgrade.PatchVO;
import com.lawfirm.model.system.vo.upgrade.UpgradeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 系统升级适配器
 * 负责系统升级相关的数据转换和服务调用
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UpgradeAdaptor extends BaseAdaptor {

    private final UpgradeService upgradeService;

    /**
     * 获取升级记录列表
     */
    public List<UpgradeVO> getUpgradeList(UpgradeQueryDTO queryDTO) {
        log.info("获取升级记录列表");
        return upgradeService.selectUpgradeList(queryDTO);
    }

    /**
     * 获取升级记录详情
     */
    public UpgradeVO getUpgradeDetail(Long id) {
        log.info("获取升级记录详情，ID：{}", id);
        return upgradeService.selectUpgradeById(id);
    }

    /**
     * 新增升级记录
     */
    public void addUpgrade(UpgradeCreateDTO createDTO) {
        log.info("新增升级记录：{}", createDTO.getVersion());
        upgradeService.insertUpgrade(createDTO);
    }

    /**
     * 修改升级记录
     */
    public void updateUpgrade(Long id, UpgradeUpdateDTO updateDTO) {
        log.info("修改升级记录，ID：{}", id);
        updateDTO.setId(id);
        upgradeService.updateUpgrade(updateDTO);
    }

    /**
     * 删除升级记录
     */
    public void deleteUpgrade(Long id) {
        log.info("删除升级记录，ID：{}", id);
        upgradeService.deleteUpgradeById(id);
    }

    /**
     * 执行系统升级
     */
    public void executeUpgrade(Long id) {
        log.info("执行系统升级，ID：{}", id);
        upgradeService.executeUpgrade(id);
    }

    /**
     * 回滚系统升级
     */
    public void rollbackUpgrade(Long id) {
        log.info("回滚系统升级，ID：{}", id);
        upgradeService.rollbackUpgrade(id);
    }

    /**
     * 获取补丁列表
     */
    public List<PatchVO> getPatchList(Long upgradeId, PatchQueryDTO queryDTO) {
        log.info("获取补丁列表，升级ID：{}", upgradeId);
        queryDTO.setUpgradeId(upgradeId);
        return upgradeService.selectPatchList(queryDTO);
    }

    /**
     * 上传补丁文件
     */
    public String uploadPatchFile(Long upgradeId, MultipartFile file) {
        log.info("上传补丁文件，升级ID：{}", upgradeId);
        return upgradeService.uploadPatchFile(upgradeId, file);
    }

    /**
     * 新增补丁
     */
    public void addPatch(Long upgradeId, PatchCreateDTO createDTO) {
        log.info("新增补丁，升级ID：{}", upgradeId);
        createDTO.setUpgradeId(upgradeId);
        upgradeService.insertPatch(createDTO);
    }
} 