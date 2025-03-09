package com.lawfirm.model.system.service;

import com.lawfirm.model.system.dto.upgrade.PatchCreateDTO;
import com.lawfirm.model.system.dto.upgrade.PatchQueryDTO;
import com.lawfirm.model.system.dto.upgrade.UpgradeCreateDTO;
import com.lawfirm.model.system.dto.upgrade.UpgradeQueryDTO;
import com.lawfirm.model.system.dto.upgrade.UpgradeUpdateDTO;
import com.lawfirm.model.system.vo.upgrade.PatchVO;
import com.lawfirm.model.system.vo.upgrade.UpgradeVO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * 系统升级服务接口
 */
public interface UpgradeService {

    /**
     * 查询升级列表
     *
     * @param queryDTO 查询参数
     * @return 升级列表
     */
    List<UpgradeVO> selectUpgradeList(UpgradeQueryDTO queryDTO);

    /**
     * 根据ID查询升级信息
     *
     * @param id 升级ID
     * @return 升级信息
     */
    UpgradeVO selectUpgradeById(Long id);

    /**
     * 新增升级
     *
     * @param createDTO 创建参数
     */
    void insertUpgrade(UpgradeCreateDTO createDTO);

    /**
     * 修改升级
     *
     * @param updateDTO 更新参数
     */
    void updateUpgrade(UpgradeUpdateDTO updateDTO);

    /**
     * 删除升级
     *
     * @param id 升级ID
     */
    void deleteUpgradeById(Long id);

    /**
     * 执行升级
     *
     * @param id 升级ID
     */
    void executeUpgrade(Long id);

    /**
     * 回滚升级
     *
     * @param id 升级ID
     */
    void rollbackUpgrade(Long id);

    /**
     * 查询补丁列表
     *
     * @param queryDTO 查询参数
     * @return 补丁列表
     */
    List<PatchVO> selectPatchList(PatchQueryDTO queryDTO);

    /**
     * 上传补丁文件
     *
     * @param upgradeId 升级ID
     * @param file 文件
     * @return 文件路径
     */
    String uploadPatchFile(Long upgradeId, MultipartFile file);

    /**
     * 新增补丁
     *
     * @param createDTO 创建参数
     */
    void insertPatch(PatchCreateDTO createDTO);

    /**
     * 删除补丁
     *
     * @param patchId 补丁ID
     */
    void deletePatchById(Long patchId);

    /**
     * 执行补丁
     *
     * @param patchId 补丁ID
     */
    void executePatch(Long patchId);

    /**
     * 回滚补丁
     *
     * @param patchId 补丁ID
     */
    void rollbackPatch(Long patchId);

    /**
     * 检查更新
     *
     * @return 是否有更新
     */
    boolean checkUpdate();
} 