package com.lawfirm.core.workflow.config;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * 工作流配置属性类
 * 用于业务层注入工作流配置
 */
@Data
@Validated
public class WorkflowProperties {

    /**
     * 是否启用工作流模块
     */
    private boolean enabled = true;

    /**
     * 工作流配置
     */
    private Config config = new Config();

    /**
     * 工作流配置类
     */
    @Data
    public static class Config {
        /**
         * 数据源配置
         */
        private Datasource datasource = new Datasource();

        /**
         * 数据源配置类
         */
        @Data
        public static class Datasource {
            /**
             * 数据库模式
             */
            private String schema = "law_firm_workflow";
        }
    }
} 