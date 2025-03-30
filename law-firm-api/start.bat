@echo off
set EXCLUDE_OPTS=--spring.autoconfigure.exclude=com.lawfirm.core.workflow.config.FlowableConfig,org.flowable.spring.boot.FlowableAutoConfiguration,org.flowable.spring.boot.ProcessEngineServicesAutoConfiguration,org.flowable.spring.boot.app.AppEngineAutoConfiguration
set JVM_OPTS=-Dflowable.enabled=false -Dlawfirm.workflow.enabled=false -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Duser.language=zh -Duser.region=CN

echo 启动律师事务所管理系统API服务...
echo 已禁用工作流功能...

java %JVM_OPTS% -jar target/law-firm-api-1.0.0.jar %EXCLUDE_OPTS% 