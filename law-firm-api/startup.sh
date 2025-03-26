#!/bin/bash

# 设置Java字符编码
export JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8"

# 启动应用
java $JAVA_OPTS -jar law-firm-api.jar 