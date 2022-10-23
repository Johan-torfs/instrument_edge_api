FROM amazoncorretto:18-alpine3.13-jdk
EXPOSE 8050
ENV INSTRUMENT_SERVICE_BASEURL "localhost:8051"
ENV PIECE_SERVICE_BASEURL "localhost:8052"
ENV REVIEW_SERVICE_BASEURL "localhost:8053"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]