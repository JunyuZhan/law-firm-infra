FROM osixia/openldap:1.5.0

# 环境变量配置
ENV LDAP_ORGANISATION="Law Firm LDAP"
ENV LDAP_DOMAIN="lawfirm.local"
ENV LDAP_BASE_DN="dc=lawfirm,dc=local"
ENV LDAP_ADMIN_PASSWORD="admin_password"
ENV LDAP_CONFIG_PASSWORD="config_password"
ENV LDAP_READONLY_USER="true"
ENV LDAP_READONLY_USER_USERNAME="readonly"
ENV LDAP_READONLY_USER_PASSWORD="readonly_password"
ENV LDAP_TLS="false"

# 设置工作目录
WORKDIR /container/service

# 注意：不再直接复制文件，而是使用volume挂载
# 启动时会从/container/service/slapd/assets/config/bootstrap/ldif/custom目录加载LDIF文件

# 暴露LDAP端口
EXPOSE 389 636

# 健康检查
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
  CMD ldapsearch -x -H ldap://localhost:389 -b $LDAP_BASE_DN -D "cn=admin,$LDAP_BASE_DN" -w $LDAP_ADMIN_PASSWORD || exit 1 