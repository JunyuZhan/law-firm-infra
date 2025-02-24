package com.lawfirm.model.knowledge.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.knowledge.entity.Attachment;
import com.lawfirm.model.knowledge.vo.AttachmentVO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 附件服务接口
 */
public interface AttachmentService extends BaseService<Attachment> {

    /**
     * 上传附件
     */
    AttachmentVO upload(Long articleId, MultipartFile file, String description);

    /**
     * 批量上传附件
     */
    List<AttachmentVO> batchUpload(Long articleId, List<MultipartFile> files);

    /**
     * 更新附件信息
     */
    AttachmentVO update(Long id, String name, String description);

    /**
     * 分页查询附件
     */
    Page<AttachmentVO> query(Long articleId, int page, int size);

    /**
     * 获取附件详情
     */
    AttachmentVO getDetail(Long id);

    /**
     * 下载附件
     */
    byte[] download(Long id);

    /**
     * 预览附件
     */
    String preview(Long id);

    /**
     * 获取下载URL
     */
    String getDownloadUrl(Long id);

    /**
     * 获取预览URL
     */
    String getPreviewUrl(Long id);

    /**
     * 更新附件排序
     */
    void updateSort(Long id, Integer sortNumber);

    /**
     * 批量更新附件排序
     */
    void batchUpdateSort(List<Long> ids, List<Integer> sortNumbers);

    /**
     * 更新下载次数
     */
    void increaseDownloadCount(Long id);

    /**
     * 检查文件是否合法
     */
    boolean checkFileValid(MultipartFile file);

    /**
     * 获取文章的附件列表
     */
    List<AttachmentVO> getArticleAttachments(Long articleId);

    /**
     * 统计文章附件数量
     */
    Long countByArticle(Long articleId);

    /**
     * 统计存储空间使用量
     */
    Long countStorageSize(Long articleId);

    /**
     * 清理无效附件
     */
    void cleanInvalidAttachments();
} 