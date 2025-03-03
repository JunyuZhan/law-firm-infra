package com.lawfirm.model.knowledge.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 附件视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class AttachmentVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 文章标题
     */
    private String articleTitle;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件大小（字节）
     */
    private Long size;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件扩展名
     */
    private String extension;

    /**
     * 存储ID
     */
    private Long storageId;

    /**
     * 下载次数
     */
    private Long downloadCount;

    /**
     * 排序号
     */
    private Integer sortNumber;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否可下载
     */
    private Boolean downloadable;

    /**
     * 访问权限（0：公开，1：登录可见，2：指定角色可见）
     */
    private Integer accessLevel;

    /**
     * 可访问角色列表
     */
    private String accessRoles;

    /**
     * 上传人ID
     */
    private Long uploaderId;

    /**
     * 上传人名称
     */
    private String uploaderName;

    /**
     * 上传时间
     */
    private Long uploadTime;

    /**
     * 下载URL
     */
    private String downloadUrl;

    /**
     * 预览URL
     */
    private String previewUrl;
} 