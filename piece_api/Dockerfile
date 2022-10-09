FROM amazoncorretto:18-alpine3.13-jdk
EXPOSE 8052
ENV FORWARD_PIECE_HOST "mongodb.piece"
ENV FORWARD_PIECE_PORT 27017
ENV PIECE_DB_ADMIN "admin"
ENV PIECE_DB_NAME "pieces"
ENV PIECE_USERNAME "citizix"
ENV PIECE_PASSWORD "S3cret"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]