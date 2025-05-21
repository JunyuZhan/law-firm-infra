FROM mailserver/docker-mailserver:latest

# 环境变量配置
ENV ENABLE_SPAMASSASSIN=1
ENV ENABLE_CLAMAV=1
ENV ENABLE_FAIL2BAN=1
ENV ENABLE_POSTGREY=1
ENV ONE_DIR=1
ENV DMS_DEBUG=0
ENV ENABLE_LDAP=0
ENV SSL_TYPE=self-signed
ENV TZ=Asia/Shanghai
ENV POSTMASTER_ADDRESS=postmaster@lawfirm.local
ENV PERMIT_DOCKER=network
ENV ENABLE_POSTFIX_VIRTUAL_TRANSPORT=1
ENV POSTFIX_DAGENT=lmtp:dovecot:24
ENV POSTFIX_MAILBOX_SIZE_LIMIT=0
ENV POSTFIX_MESSAGE_SIZE_LIMIT=25600000

# 注意：不再直接复制文件，而是在启动时从挂载目录执行setup.sh
# 挂载目录为：/tmp/mail-setup

# 创建启动脚本
RUN echo '#!/bin/bash\n\
if [ -f /tmp/mail-setup/setup.sh ]; then\n\
  echo "执行邮件服务初始化脚本..."\n\
  chmod +x /tmp/mail-setup/setup.sh\n\
  /tmp/mail-setup/setup.sh\n\
else\n\
  echo "警告：邮件初始化脚本不存在"\n\
fi\n\
exit 0' > /tmp/init-mail.sh && chmod +x /tmp/init-mail.sh

# 执行初始化脚本
RUN /tmp/init-mail.sh

# 暴露SMTP、IMAP和管理端口
EXPOSE 25 587 465 143 993 110 995 4190

# 健康检查
HEALTHCHECK --interval=30s --timeout=30s --start-period=60s --retries=3 \
  CMD nc -z localhost 25 || exit 1 