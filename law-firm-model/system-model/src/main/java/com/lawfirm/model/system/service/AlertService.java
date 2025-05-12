package com.lawfirm.model.system.service;

/**
 * 系统告警服务接口
 */
public interface AlertService {

    /**
     * 发送告警信息
     *
     * @param type    告警类型
     * @param level   告警级别（INFO/WARNING/ERROR）
     * @param message 告警信息
     * @return 告警ID
     */
    String sendAlert(String type, String level, String message);

    /**
     * 发送数据库告警
     *
     * @param dbName  数据库名称
     * @param level   告警级别
     * @param message 告警信息
     * @return 告警ID
     */
    String sendDbAlert(String dbName, String level, String message);

    /**
     * 关闭告警
     *
     * @param alertId 告警ID
     * @return 是否成功
     */
    boolean closeAlert(String alertId);
} 