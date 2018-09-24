FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENV SERVER_PORT 8082
ENV API_USER admin
ENV API_PW adidas!
ENV URL_SPRING_CLOUD http://192.168.3.15:8088/eureka
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]