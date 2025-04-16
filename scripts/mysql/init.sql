-- 设置全局变量
SET GLOBAL time_zone = '+8:00';
SET GLOBAL character_set_server = utf8mb4;
SET GLOBAL collation_server = utf8mb4_unicode_ci;

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS `law_firm` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE `law_firm`;

-- 创建用户并授权
CREATE USER IF NOT EXISTS 'root'@'%' IDENTIFIED BY 'lawfirm_default';
GRANT ALL PRIVILEGES ON `law_firm`.* TO 'root'@'%';
FLUSH PRIVILEGES;

-- 设置数据库字符集
ALTER DATABASE law_firm CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建Flyway历史表（如果不存在）
CREATE TABLE IF NOT EXISTS `flyway_schema_history` (
    `installed_rank` INT NOT NULL AUTO_INCREMENT,
    `version` VARCHAR(50) DEFAULT NULL,
    `description` VARCHAR(200) NOT NULL,
    `type` VARCHAR(20) NOT NULL,
    `script` VARCHAR(1000) NOT NULL,
    `checksum` INT DEFAULT NULL,
    `installed_by` VARCHAR(100) NOT NULL,
    `installed_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `execution_time` INT NOT NULL,
    `success` TINYINT(1) NOT NULL,
    PRIMARY KEY (`installed_rank`),
    KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci; 