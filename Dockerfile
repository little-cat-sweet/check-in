FROM openjdk:11-jre-slim
LABEL maintainer="851891179@qq.com"
WORKDIR /usr/local/java
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo Asia/Shanghai > /etc/timezone
ENV TZ=Asia/Shanghai \
    JAR_FILE=check-in.jar \
    JAVA_OPTS="-Xmx512m" \
    SPRING_PROFILES_ACTIVE=online \
    SPRING_CONFIG_LOCATION=/usr/local/java/application.yml,/usr/local/java/application-online.yml
EXPOSE 8080
COPY application.yml application-online.yml /usr/local/java/
COPY ${JAR_FILE} /usr/local/java/${JAR_FILE}
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS \
 -Djava.security.egd=file:/dev/./urandom \
 -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE \
 -Dspring.config.location=file:${SPRING_CONFIG_LOCATION} \
 -jar /usr/local/java/${JAR_FILE}"]