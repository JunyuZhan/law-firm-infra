package com.lawfirm.knowledge.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 知识管理模块配置属性
 */
@Data
@ConfigurationProperties(prefix = "knowledge")
public class KnowledgeProperties {

    /**
     * 存储配置
     */
    private Storage storage = new Storage();

    /**
     * 文档配置
     */
    private Document document = new Document();

    /**
     * 附件配置
     */
    private Attachment attachment = new Attachment();

    /**
     * 分类配置
     */
    private Category category = new Category();

    /**
     * 搜索配置
     */
    private Search search = new Search();

    /**
     * 权限配置
     */
    private Permission permission = new Permission();

    /**
     * 统计配置
     */
    private Statistic statistic = new Statistic();

    /**
     * 存储配置
     */
    @Data
    public static class Storage {
        /**
         * 存储基础路径
         */
        private String basePath = "/data/knowledge";

        /**
         * 临时存储路径
         */
        private String tempPath = "/data/knowledge/temp";
    }

    /**
     * 文档配置
     */
    @Data
    public static class Document {
        /**
         * 最大文件大小
         */
        private String maxSize = "10MB";

        /**
         * 允许的文件扩展名
         */
        private String allowedExtensions = ".doc,.docx,.pdf,.md,.txt";
    }

    /**
     * 附件配置
     */
    @Data
    public static class Attachment {
        /**
         * 最大文件大小
         */
        private String maxSize = "20MB";

        /**
         * 每个知识文档最大附件数量
         */
        private Integer maxCountPerKnowledge = 10;

        /**
         * 允许的文件扩展名
         */
        private String allowedExtensions = ".doc,.docx,.pdf,.jpg,.png,.zip";
    }

    /**
     * 分类配置
     */
    @Data
    public static class Category {
        /**
         * 最大分类深度
         */
        private Integer maxDepth = 3;
    }

    /**
     * 搜索配置
     */
    @Data
    public static class Search {
        /**
         * 是否启用高亮
         */
        private Boolean enableHighlight = true;

        /**
         * 最大结果数量
         */
        private Integer maxResultCount = 100;

        /**
         * 最小关键词长度
         */
        private Integer minKeywordLength = 2;
    }

    /**
     * 权限配置
     */
    @Data
    public static class Permission {
        /**
         * 公开分类ID列表
         */
        private List<Long> publicCategories;
    }

    /**
     * 统计配置
     */
    @Data
    public static class Statistic {
        /**
         * 统计任务执行时间表达式
         */
        private String cron = "0 0 1 * * ?";
    }
} 