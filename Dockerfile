# 基础镜像 JDK11
FROM openjdk:11-jre-slim

# 工作目录
WORKDIR /app

# 复制打好的jar包（pom里finalName是check-in）
COPY target/check-in.jar /app/app.jar

# 时区
ENV TZ=Asia/Shanghai

# 启动命令 使用线上配置文件
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=online"]