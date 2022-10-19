FROM amazoncorretto:18-alpine3.13-jdk
EXPOSE 8051
ENV MYSQL_DB_PORT 3306
ENV MYSQL_DB_USERNAME "root"
ENV MYSQL_DB_PASSWORD "S3cret"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]