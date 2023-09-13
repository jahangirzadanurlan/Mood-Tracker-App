FROM adoptopenjdk/openjdk11
EXPOSE 8082
ARG JAR_FILE=build/libs/sentiment-analysis-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} sentiment.jar
ENTRYPOINT ["java", "-jar","/sentiment.jar"]