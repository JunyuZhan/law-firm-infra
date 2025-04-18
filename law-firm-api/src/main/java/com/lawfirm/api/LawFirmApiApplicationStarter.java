package com.lawfirm.api;

import com.lawfirm.api.config.CustomBeanNameGenerator;
import com.lawfirm.common.util.AppStarterUtil;
import com.lawfirm.common.util.DatabaseConnectionChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

/**
 * 律师事务所API应用启动器
 * <p>
 * 作为系统的主入口点，提供简化的启动方式和环境检测功能。
 * 支持多种环境配置和启动参数。
 */
@Slf4j
public class LawFirmApiApplicationStarter {

    /**
     * 主启动方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        log.info("======= 律师事务所管理系统正在启动 =======");
        
        // 1. 创建Spring应用实例
        SpringApplication app = new SpringApplication(LawFirmApiApplication.class);
        app.setBeanNameGenerator(new CustomBeanNameGenerator());
        
        // 2. 运行应用并获取环境
        Environment env = app.run(args).getEnvironment();
        
        // 3. 检查数据库连接
        checkDatabaseConnection(env);
        
        log.info("======= 律师事务所管理系统启动完成 =======");
        
        // 4. 输出访问信息
        AppStarterUtil.start(LawFirmApiApplication.class, args);
    }
    
    /**
     * 检查数据库连接
     *
     * @param env Spring环境
     */
    private static void checkDatabaseConnection(Environment env) {
        log.info("正在检查数据库连接...");
        
        // 从环境中获取数据库连接参数
        DatabaseConnectionChecker.ConnectionParams params = 
                DatabaseConnectionChecker.ConnectionParams.fromEnvironment(env);
        
        // 检查连接
        DatabaseConnectionChecker.CheckResult result = 
                DatabaseConnectionChecker.checkConnection(params);
        
        if (result.isSuccess()) {
            log.info("数据库连接检查: 成功");
        } else {
            log.error("数据库连接检查: 失败");
            log.error("错误信息: {}", result.getMessage());
            
            // 输出诊断信息
            String diagnosis = DatabaseConnectionChecker.diagnose(result);
            log.error("诊断建议:\n{}", diagnosis);
            
            // 数据库连接失败但不中断启动
            log.warn("尽管数据库连接检查失败，系统仍将继续启动，但可能无法正常运行");
        }
    }
} 