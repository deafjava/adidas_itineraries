FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENV SERVER_PORT 8082
ENV API_URL http://192.168.3.12:8081/route/
ENV API_USER admin
ENV API_PW adidas!
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]