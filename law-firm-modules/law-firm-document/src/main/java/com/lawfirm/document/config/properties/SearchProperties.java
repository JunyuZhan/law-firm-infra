package com.lawfirm.document.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 搜索配置属性
 */
@Data
@Component("documentSearchProperties")
@ConfigurationProperties(prefix = "lawfirm.document.search")
public class SearchProperties {
    
    /**
     * 搜索引擎类型：elasticsearch/solr
     * 默认使用database数据库搜索，不再使用付费的ElasticSearch
     */
    private String type = "database";
    
    /**
     * 是否启用搜索引擎
     */
    private boolean enabled = true;
    
    /**
     * 索引前缀
     */
    private String indexPrefix = "lawfirm_";
    
    /**
     * 搜索结果数量限制
     */
    private int maxResults = 100;
    
    /**
     * 高亮设置
     */
    private Highlight highlight = new Highlight();
    
    @Data
    public static class Highlight {
        /**
         * 是否启用高亮
         */
        private boolean enabled = true;
        
        /**
         * 高亮前缀
         */
        private String preTag = "<em>";
        
        /**
         * 高亮后缀
         */
        private String postTag = "</em>";
    }
}
