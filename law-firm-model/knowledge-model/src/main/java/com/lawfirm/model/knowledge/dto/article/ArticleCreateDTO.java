package com.lawfirm.model.knowledge.dto.article;

import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.knowledge.enums.ArticleTypeEnum;
import com.lawfirm.model.knowledge.enums.ContentTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章创建DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ArticleCreateDTO extends BaseDTO {

    /**
     * 文章标题
     */
    @NotBlank(message = "文章标题不能为空")
    @Size(max = 100, message = "文章标题不能超过100个字符")
    private String title;

    /**
     * 文章摘要
     */
    @Size(max = 500, message = "文章摘要不能超过500个字符")
    private String summary;

    /**
     * 文章内容
     */
    @NotBlank(message = "文章内容不能为空")
    private String content;

    /**
     * 内容类型
     */
    @NotNull(message = "内容类型不能为空")
    private ContentTypeEnum contentType;

    /**
     * 文章类型
     */
    @NotNull(message = "文章类型不能为空")
    private ArticleTypeEnum articleType;

    /**
     * 所属分类ID
     */
    @NotNull(message = "所属分类不能为空")
    private Long categoryId;

    /**
     * 标签列表
     */
    private List<String> tags = new ArrayList<>();

    /**
     * 封面图片
     */
    private String coverImage;

    /**
     * 是否置顶
     */
    private Boolean top = false;

    /**
     * 是否推荐
     */
    private Boolean recommended = false;

    /**
     * 是否原创
     */
    private Boolean original = true;

    /**
     * 原文链接
     */
    private String sourceUrl;

    /**
     * 访问权限（0：公开，1：登录可见，2：指定角色可见）
     */
    private Integer accessLevel = 0;

    /**
     * 可访问角色列表（逗号分隔）
     */
    private String accessRoles;

    /**
     * 是否允许评论
     */
    private Boolean allowComment = true;

    /**
     * SEO标题
     */
    private String seoTitle;

    /**
     * SEO关键词
     */
    private String seoKeywords;

    /**
     * SEO描述
     */
    private String seoDescription;

    /**
     * 附件列表
     */
    private List<AttachmentDTO> attachments = new ArrayList<>();

    /**
     * 附件DTO
     */
    @Data
    @Accessors(chain = true)
    public static class AttachmentDTO {
        /**
         * 附件名称
         */
        @NotBlank(message = "附件名称不能为空")
        private String name;

        /**
         * 文件路径
         */
        @NotBlank(message = "文件路径不能为空")
        private String path;

        /**
         * 文件大小
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
         * 描述
         */
        private String description;

        /**
         * 是否可下载
         */
        private Boolean downloadable = true;

        /**
         * 访问权限
         */
        private Integer accessLevel = 0;

        /**
         * 可访问角色列表
         */
        private String accessRoles;

        /**
         * MD5校验值
         */
        private String md5;
    }
} 