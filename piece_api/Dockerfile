FROM amazoncorretto:18-alpine3.13-jdk
EXPOSE 8052
ARG JAR_FILE=./out/artifacts/piece_api_jar/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]