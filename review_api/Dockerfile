FROM amazoncorretto:18-alpine3.13-jdk
EXPOSE 8053
ENV FORWARD_REVIEW_HOST "mongodb.review"
ENV FORWARD_REVIEW_PORT 27018
ENV REVIEW_DB_ADMIN "admin"
ENV REVIEW_DB_NAME "reviews"
ENV REVIEW_USERNAME "citizix"
ENV REVIEW_PASSWORD "S3cret"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]