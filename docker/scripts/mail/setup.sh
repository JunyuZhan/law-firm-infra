#!/bin/bash

# 创建邮箱账户
# 语法：setup email add <邮箱地址> [密码]

# 系统管理员邮箱
setup email add admin@lawfirm.local admin_pass

# 通知邮箱
setup email add notification@lawfirm.local notify_pass

# 律师邮箱
setup email add zhangsan@lawfirm.local lawyer_pass
setup email add wangwu@lawfirm.local lawyer_pass

# 工作人员邮箱
setup email add lisi@lawfirm.local staff_pass

# 设置别名
# 语法：setup alias add <别名> <目标邮箱>
setup alias add info@lawfirm.local admin@lawfirm.local
setup alias add contact@lawfirm.local admin@lawfirm.local

# 设置区域
setup config dkim keysize 2048

# 配置反垃圾邮件设置
setup config setenv ENABLE_SPAMASSASSIN 1
setup config setenv SPAMASSASSIN_SPAM_TO_INBOX 1

# 配置杀毒设置
setup config setenv ENABLE_CLAMAV 1

# 配置SSL/TLS
setup config setenv SSL_TYPE self-signed

# 配置完成后退出
exit 0 