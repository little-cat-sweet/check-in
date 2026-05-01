# 构建阶段：使用 Maven 构建项目
FROM maven:3.8-openjdk-11 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
# 打包，跳过测试
RUN mvn clean package -DskipTests

# 运行阶段：使用官方维护的 JRE 镜像运行
FROM eclipse-temurin:11-jre
WORKDIR /app
# 从构建阶段复制打好的 jar 包
COPY --from=builder /app/target/check-in.jar /app/check-in.jar

# 容器启动命令，指定使用 online 配置文件
ENTRYPOINT ["java", "-jar", "/app/check-in.jar", "--spring.profiles.active=online"]