package com.lawfirm.document.manager.editor;

import com.lawfirm.model.document.dto.document.DocumentEditDTO;
import com.lawfirm.model.document.vo.DocumentVO;

import java.io.InputStream;
import java.util.List;

/**
 * 文档编辑管理器
 * 负责处理文档的编辑、锁定、协同等功能
 */
public interface DocumentEditorManager {

    /**
     * 获取文档编辑URL
     *
     * @param documentId 文档ID
     * @return 编辑URL
     */
    String getEditUrl(Long documentId);

    /**
     * 锁定文档
     *
     * @param documentId 文档ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean lockDocument(Long documentId, Long userId);

    /**
     * 解锁文档
     *
     * @param documentId 文档ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean unlockDocument(Long documentId, Long userId);

    /**
     * 检查文档锁定状态
     *
     * @param documentId 文档ID
     * @return 锁定用户ID，如果未锁定则返回null
     */
    Long checkLockStatus(Long documentId);

    /**
     * 保存文档编辑内容
     *
     * @param documentId 文档ID
     * @param content 文档内容
     * @param userId 用户ID
     */
    void saveContent(Long documentId, InputStream content, Long userId);

    /**
     * 自动保存文档
     *
     * @param documentId 文档ID
     * @param content 文档内容
     * @param userId 用户ID
     */
    void autoSave(Long documentId, InputStream content, Long userId);

    /**
     * 获取文档编辑历史
     *
     * @param documentId 文档ID
     * @return 编辑历史列表
     */
    List<DocumentVO> getEditHistory(Long documentId);

    /**
     * 获取当前编辑者列表
     *
     * @param documentId 文档ID
     * @return 用户ID列表
     */
    List<Long> getCurrentEditors(Long documentId);

    /**
     * 加入协同编辑
     *
     * @param documentId 文档ID
     * @param userId 用户ID
     */
    void joinCollaboration(Long documentId, Long userId);

    /**
     * 离开协同编辑
     *
     * @param documentId 文档ID
     * @param userId 用户ID
     */
    void leaveCollaboration(Long documentId, Long userId);

    /**
     * 广播编辑操作
     *
     * @param documentId 文档ID
     * @param editDTO 编辑操作DTO
     */
    void broadcastEdit(Long documentId, DocumentEditDTO editDTO);
} 