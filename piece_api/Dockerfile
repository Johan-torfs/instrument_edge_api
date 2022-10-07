FROM amazoncorretto:18-alpine3.13-jdk
EXPOSE 8052
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]